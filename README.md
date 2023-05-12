# Project Name: GraphQL Backend with Java Spring Boot and Netflix DGS

## Description
This project serves as a learning exercise to practice implementing a GraphQL backend using Java, Spring Boot, and Netflix DGS. The project is based on the course "Learn how to use Java Spring Boot 3 + Netflix DGS to build GraphQL backend: read, modify, and subscribe for data change" on Udemy, instructed by Timotius Pamungkas.

The primary goal of this project is to gain hands-on experience with GraphQL and understand its concepts and implementation using Java Spring Boot. By following the course, you will learn how to create GraphQL schemas, define resolvers, handle data queries, mutations, and subscriptions, and integrate with other libraries and tools such as Netflix DGS.

## Technologies Used
- Java
- Spring Boot
- Gradle
- GraphQL
- Netflix DGS

## Prerequisites
Before running this project, ensure that you have the following prerequisites installed on your machine:
- Java Development Kit (JDK)
- Gradle
- An IDE of your choice (e.g., IntelliJ IDEA, Eclipse)

## Setup Instructions
1. Clone the repository or download the project files from the GitHub repository.
2. Build the project using Gradle to resolve the dependencies.
3. Run `gradle clean bootRun -DskipTests` to run the project skipping the tests.
3. The GraphQL endpoint should now be accessible at `http://localhost:8080/graphql`. You can test the GraphQL queries, mutations, and subscriptions using tools like [GraphQL Playground](https://github.com/graphql/graphql-playground) or [Insomnia](https://insomnia.rest/graphql/).

## Project Structure
The project follows a standard Java Spring Boot structure. Here's an overview of the key directories and files:

- `src/main/java/com/fdherrera/graphqldemo/`: Contains the Java source code.
- `src/test/java/com/fdherrera/graphqldemo/`: Contains tests.

- `src/main/schema/`: Contains all of the schemas.

- `build.gradle`: The Gradle build file that defines project dependencies and configurations.

## Additional Resources
- [Java Spring Boot](https://spring.io/projects/spring-boot)
- [Gradle](https://gradle.org/)
- [GraphQL](https://graphql.org/)
- [Netflix DGS](https://netflix.github.io/dgs/)

## Acknowledgements
I would like to acknowledge the instructor Timotius Pamungkas for his excellent course on Udemy, "Learn how to use Java Spring Boot 3 + Netflix DGS to build GraphQL backend: read, modify, and subscribe for data change". The course provided valuable insights and guidance in building this GraphQL backend using Java Spring Boot and Netflix DGS.

## License
This project is licensed under the [MIT License](LICENSE).

Feel free to explore, modify, and use this project for educational and non-commercial purposes.

## Contact Information
If you have any questions or suggestions regarding this project, please feel free to reach out to me at [federico.herrera_dev@outlook.com](mailto:federico.herrera_dev@outlook.com).

