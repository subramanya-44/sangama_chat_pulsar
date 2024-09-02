---

# Chat Application with Apache Pulsar

## Overview

This project is a simple chat application using Apache Pulsar for messaging, with a Java Swing GUI. It consists of two separate Java applications that act as producers and consumers. Each application can send and receive messages through Pulsar.

## Requirements

- Docker
- Maven
- Java Development Kit (JDK) 22
- IntelliJ IDEA (or any IDE with Maven support)

## Setup Instructions

### 1. Run Apache Pulsar in Docker

1. **Pull the Pulsar Docker Image**:
   Open a terminal and run:
   ```bash
   docker pull apachepulsar/pulsar:latest
   ```

2. **Start Pulsar in Standalone Mode**:
   Run the following command to start Pulsar in standalone mode:
   ```bash
   docker run -it --rm -p 6650:6650 -p 8080:8080 apachepulsar/pulsar:latest bin/pulsar standalone
   ```
   - Port `6650` is used for Pulsar client connections.
   - Port `8080` is used for the Pulsar admin API.



### 2. Setup Maven Dependencies

1. **Create a Maven Project**:
   If you haven’t already, create a new Maven project in your IDE (e.g., IntelliJ IDEA).

2. **Add Dependencies**:
   Update your `pom.xml` file to include the necessary Pulsar dependencies. A sample `pom.xml` configuration is provided in the project repository.

   

### 3. Create and Configure Java Files

1. **Create the UI1(ENTITY2) File**:
   Save the file as `ChatUI.java`. This file contains the producer and consumer logic for the first entity.

   ![ChatUI](images/chatui.png) <!-- Add an image of the ChatUI code or UI if available -->

2. **Create the UI2(ENTITY2) File**:
   Save the file as `ChatUI2.java`. This file contains the producer and consumer logic for the second entity.

   ![ChatUI2](images/chatui2.png) <!-- Add an image of the ChatUI2 code or UI if available -->

### 4. Build and Run the Project

1. **Build the Project**:
   In your terminal, navigate to the project directory and run:
   ```bash
   mvn clean install
   ```
2. **Run entity1**:
   Launch the `ChatUI` application:
   ```bash
   mvn exec:java -Dexec.mainClass="org.example.ChatUI"
   ```
 
5. **Run entity2**:
   Launch the `ChatUI2` application:
   ```bash
   mvn exec:java -Dexec.mainClass="org.example.ChatUI2"
   ```
   ![Run Consumer](images/output.png) 

You should now have two chat applications running, each with its own GUI. Messages sent from one application should appear in the other.

## Troubleshooting

- **Pulsar Connection Issues**: Ensure Pulsar is running and accessible at the specified `SERVICE_URL`.
- **Dependency Issues**: Verify that Maven dependencies are correctly added to `pom.xml` and run `mvn clean install` to refresh the dependencies.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
