Here’s an improved version of your `README.md` with some enhancements for clarity, consistency, and flow:

---

# Reactive Stream Broadcasting with Kafka

This project is a Java-based application developed using the **Spring Boot** framework and **Maven** for dependency management. It demonstrates a reactive streaming architecture where messages are consumed from the Wikimedia stream, published to a Kafka topic, and then consumed by a Kafka consumer application. The entire process utilizes reactive programming principles via **Spring WebFlux** and **Spring Kafka**.

## Table of Contents

- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Configuration](#configuration)
    - [Kafka Topic Configuration](#kafka-topic-configuration)
    - [WebClient Configuration](#webclient-configuration)
- [Usage](#usage)
    - [Starting the Application](#starting-the-application)
    - [Consuming and Publishing Messages](#consuming-and-publishing-messages)
- [Running the Application](#running-the-application)
- [License](#license)

## Project Structure

The project follows a standard Spring Boot directory layout. Here’s a summary of the key components:

```
- src
  - main
    - java
      - com.murad.producer
        - config: Kafka and WebClient configuration files.
        - producer: Kafka producer logic.
        - rest: REST controller to trigger stream consumption and publishing.
        - stream: Service for consuming Wikimedia stream and publishing messages to Kafka.
      - com.murad.consumer
        - config: Kafka topic configuration files.
        - consumer: Kafka consumer logic for consuming messages from Kafka topics.
    - resources
      - application.properties: Application configurations.
- pom.xml: Maven configuration for dependency management.
```

### Key Modules

- **com.murad.producer.config**: Contains configuration for Kafka topics and WebClient.
- **com.murad.producer.producer**: Handles Kafka producer logic to publish messages to Kafka topics.
- **com.murad.producer.rest**: Defines the REST controller to initiate the process of consuming and publishing messages.
- **com.murad.producer.stream**: Responsible for consuming data from the Wikimedia stream and publishing it to Kafka.
- **com.murad.consumer.config**: Contains Kafka topic configuration.
- **com.murad.consumer.consumer**: Kafka consumer that listens to the Kafka topic and processes incoming messages.

## Dependencies

This project uses the following dependencies:

- **Spring Boot**: Framework for building the application.
- **Spring WebFlux**: To support reactive programming for asynchronous operations.
- **Spring Kafka**: To integrate Kafka for publishing and consuming messages.
- **Lombok**: To reduce boilerplate code (e.g., getter/setter methods and constructors).

## Configuration

### Kafka Topic Configuration

Kafka topics are defined in the `WikiMediaKafkaTopicConfig.java` file. This configuration defines a Kafka topic for consuming and publishing Wikimedia stream data.

```java
@Bean
public NewTopic wikiMediaTopic() {
    return TopicBuilder
            .name("wikimedia-stream")
            .partitions(2)
            .replicas(1)
            .build();
}
```

### WebClient Configuration

The `WebClient` is used to interact with the Wikimedia stream API. The configuration is set up in `WikiMediaStreamConsumer.java`.

```java
public WikiMediaStreamConsumer(WebClient.Builder webClientBuilder, WikimediaProducer wikimediaProducer) {
    this.webClient = webClientBuilder
            .baseUrl("https://stream.wikimedia.org/v2")
            .build();
    this.wikimediaProducer = wikimediaProducer;
}
```

## Usage

### Starting the Application

To start the application, run the `main` method in `ProducerApplication.java`:

```java
@SpringBootApplication
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
```

This will start the Spring Boot application, initializing both the Kafka producer and consumer services.

### Consuming and Publishing Messages

The `WikimediaController` provides an endpoint to start consuming data from the Wikimedia stream and publishing it to Kafka:

```java
@RestController
@RequestMapping("/api/v1/wikimedia")
@RequiredArgsConstructor
public class WikimediaController {
    private final WikiMediaStreamConsumer streamConsumer;

    @GetMapping
    public void startPublishing() {
        streamConsumer.consumeAndPublish();
    }
}
```

When you access this endpoint, the system will begin consuming data from the Wikimedia stream and publish it to the Kafka topic.

## Running the Application

### Prerequisites

1. **Kafka**: Ensure Kafka is installed and running. You can quickly set up Kafka using Docker with the following command:

   ```sh
   docker-compose up -d
   ```

2. **Build the Project**:
   To build the project and install dependencies using Maven, run:

   ```sh
   mvn clean install
   ```

3. **Run the Application**:
   Start the Spring Boot application with:

   ```sh
   mvn spring-boot:run
   ```

4. **Access the Application**:
   Trigger the process of consuming data from the Wikimedia stream and publishing it to Kafka by running the following `curl` command:

   ```sh
   curl http://localhost:8080/api/v1/wikimedia
   ```

   This will start consuming the stream and publishing messages to the Kafka topic.

## License

This project is licensed under the **MIT License**. See the `LICENSE` file for more details.

---
