import Keycloak from 'keycloak-js';

const keycloakConfig = {
  url: 'http://localhost:8080/auth',
  realm: 'teamfinder-realm',
  clientId: 'spring-cloud-client',
};

const keycloak = new Keycloak(keycloakConfig);

export default keycloak;