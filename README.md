# TeamFind Microservices Project

Welcome to the TeamFind Microservices Project! This Java-based project showcases the power of microservices architecture using Spring framework components, including Kafka, Zipkin, Prometheus, Eureka Discovery Server, and API Gateway. Additionally, Keycloak is integrated for secure user authentication, providing JWT tokens for API calls.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Microservices Architecture](#microservices-architecture)
- [Authentication with Keycloak](#authentication-with-keycloak)
- [Contributing](#contributing)
- [License](#license)

## Introduction

TeamFind is an innovative project that demonstrates the principles and benefits of microservices architecture. By utilizing various Spring components and advanced tools, we've created a scalable and efficient system that fosters collaboration, all while maintaining the highest level of security.

## Features

- **Microservices:** The application is built using microservices architecture, enabling independent development and deployment of various services.
- **Service Discovery:** Eureka Discovery Server facilitates service registration and discovery, ensuring seamless communication between microservices.
- **Message Broker:** Kafka acts as a high-throughput message broker, enhancing data streaming and communication.
- **Tracing and Monitoring:** Zipkin provides distributed tracing, while Prometheus offers monitoring and alerting capabilities.
- **API Gateway:** A centralized API Gateway ensures unified access to the microservices, enhancing security and simplifying client interactions.
- **Authentication:** Keycloak is integrated for robust user authentication, generating JWT tokens for secure API calls.

## Technologies Used

- Java with Spring Framework
- Kafka
- Zipkin
- Prometheus
- Eureka Discovery Server
- API Gateway
- Keycloak for Authentication

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/StoneTone/TeamFind.git
   ```

2. Navigate to the project directory:
   ```
   cd TeamFind
   ```

3. Install dependencies and build services:
   ```
   mvn clean install
   ```


## Microservices Architecture

The project employs a microservices architecture, dividing functionality into individual services for scalability and maintainability. Each service has its own repository and can be developed independently.

## Authentication with Keycloak

Keycloak is integrated to provide secure user authentication. Users can log in to receive JWT tokens, which they use to make authorized API calls.

## Contributing

Contributions are welcome! If you'd like to contribute, please open an issue or pull request in the repository. See [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

## License

This project is licensed under the [MIT License](LICENSE).

---

Thank you for exploring the TeamFind Microservices Project!
