package com.apigateway.service;

import com.apigateway.exceptions.IncorrectPasswordException;
import com.apigateway.exceptions.UserExists;
import com.apigateway.exceptions.UserNotFound;
import com.apigateway.model.User;
import com.apigateway.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;

import javax.ws.rs.core.Response;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String login(User user){
        User attemptUser = userRepo.findByUserName(user.getUserName());
        if(attemptUser != null)
        {
            if(!encoder.matches(user.getPassword(), attemptUser.getPassword())){
                throw new IncorrectPasswordException();
            }else{
                String jwtToken = obtainJwtToken(user);
                return jwtToken;
            }
        }else{
            throw new UserNotFound(user.getUserName());
        }
    }

    public String register(User user){
        if(userRepo.findByUserName(user.getUserName()) == null){
            User newUser = new User();
            newUser.setUserName(user.getUserName());
            newUser.setPassword(encoder.encode(user.getPassword()));
            createUserInKeycloak(newUser);

            userRepo.save(newUser);

            return "You have been registered successfully!";
        }else{
            throw new UserExists(user.getUserName());
        }
    }

    public String obtainJwtToken(User user){
        String url = "http://keycloak:8080/auth/realms/teamfinder-realm/protocol/openid-connect/token";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", "spring-cloud-client");
        map.add("username", user.getUserName());
        map.add("password", user.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        // Extract the access_token from the response
        String jwtToken = new JSONObject(response.getBody()).getString("access_token");
        return jwtToken;
    }

    private void createUserInKeycloak(User user) {
        String serverUrl = "http://keycloak:8080/auth";
        String realm = "teamfinder-realm";
        String clientId = "spring-cloud-client";
        String clientSecret = "Zumxxlmnc0SNmHq8Md1TY9Z6XlPQSAgl";
        String adminUsername = "admin";
        String adminPassword = "admin";

        // Step 1: Obtain an Access Token
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .username(adminUsername)
                .password(adminPassword)
                .clientSecret(clientSecret)
                .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();


        // Use the access token obtained from the response
        String accessToken = tokenResponse.getToken();

        // Step 2: Use the Access Token in API Requests
        Keycloak adminClient = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorization(accessToken) // Set the access token as the authorization header
                .build();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUserName());
        userRepresentation.setEnabled(true);

        // Creating a new Keycloak user
        Response result = adminClient.realm(realm).users().create(userRepresentation);

        if (result.getStatus() == 201) {
            String location = result.getLocation().toString();
            String userId = location.substring(location.lastIndexOf("/") + 1);

            // Set password
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(user.getPassword());

            adminClient.realm(realm).users().get(userId).resetPassword(passwordCred);
        }
    }

}
