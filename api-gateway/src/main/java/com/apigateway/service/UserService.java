package com.apigateway.service;

import com.apigateway.exceptions.IncorrectPasswordException;
import com.apigateway.exceptions.UserExists;
import com.apigateway.exceptions.UserNotFound;
import com.apigateway.model.User;
import com.apigateway.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.keycloak.OAuth2Constants;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import javax.ws.rs.core.Response;

@Service
@RequiredArgsConstructor
public class UserService {

    //region DI and Values
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Value("${client.id}")
    private String clientId;
    @Value("${realm}")
    private String realm;
    @Value("${admin.user}")
    private String adminUser;
    @Value("${admin.pass}")
    private String adminPass;
    @Value("${client.secret}")
    private String clientSecret;
    @Value("${token.url}")
    private String tokenUrl;
    @Value("${admin.token.url}")
    private String adminTokenUrl;

    //endregion

    public String login(User user) throws URISyntaxException, IOException {
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

    public String obtainJwtToken(User user) throws URISyntaxException, IOException {
        String encodedPass = userRepo.findEncodedPasswordByUser(user.getUserName());

        // Prepare the request body
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "password"));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("username", user.getUserName()));
        params.add(new BasicNameValuePair("password", encodedPass));
        params.add(new BasicNameValuePair("client_secret", clientSecret));

        // Create the POST request
        URI uri = new URIBuilder(tokenUrl).build();
        HttpPost httpPost = new HttpPost(uri);

        // Set the headers
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // Set the request body
        HttpEntity entity = new UrlEncodedFormEntity(params);
        httpPost.setEntity(entity);

        // Execute the request
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // Check the response status
            if (response.getStatusLine().getStatusCode() == 200) {
                // Parse the response and extract the access token
                HttpEntity responseEntity = response.getEntity();
                String responseBody = EntityUtils.toString(responseEntity);
                // Assuming the response body contains JSON like: {"access_token": "..."}
                return responseBody.split("\"access_token\":\"")[1].split("\"")[0];
            } else {
                // Handle error responses here
                throw new IOException("Failed to obtain access token. Status code: " + response.getStatusLine().getStatusCode());
            }
        }
    }

    private void createUserInKeycloak(User user) {

        // Step 1: Obtain an Access Token
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(adminTokenUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .username(adminUser)
                .password(adminPass)
                .clientSecret(clientSecret)
                .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();


        // Use the access token obtained from the response
        String accessToken = tokenResponse.getToken();

        // Step 2: Use the Access Token in API Requests
        Keycloak adminClient = KeycloakBuilder.builder()
                .serverUrl(adminTokenUrl)
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
