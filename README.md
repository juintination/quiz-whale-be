# Quiz Whale API Server
Implementation of API Server for Quiz Whale Service.

This project uses the [Udacity Nanoderee Style](https://udacity.github.io/git-styleguide/) as a git commit message rule.

## Related Projects
- React Frontend Application: [https://github.com/tracer12/quiz-whale-fe](https://github.com/tracer12/quiz-whale-fe)
  - The frontend web application that interacts with this backend API server.
- AI Server: [https://github.com/wint3rx3/quiz-whale-ai](https://github.com/wint3rx3/quiz-whale-ai)
  - The server responsible for creating quizzes.

## Key Features
- Acts as an intermediary between a React Web Application and a AI Server.
- Implements user authentication and authorization using Spring Security.
- Utilize JPA for efficient database management.

## Main Functions
- Quiz Conversion
  - Receives generated quizzes from the AI server.
- Result Transmission
  - Returns the quizzes to the React Web Application.
- User Management
  - Provides secure user authentication and authorization through Spring Security.
- Data Management
  - Performs efficient database operations using JPA.

## Technical Stack
- Language
  - JAVA 17
- Framework
  - Spring boot 3.4.3
- Dependency Management
  - Gradle
- Security
  - Spring Security
  - JWT
- Database
  - MariaDB
  - H2 (for testing)
- ORM
  - JPA
- Utilities
  - Lombok
  - Gson
  - JavaFaker
- Test
  - JUnit
 
## API Documentation
- The [API documentation](https://documenter.getpostman.com/view/32366655/2sAYdfoVgc) has been created using Postman.
- When the project is running, you can access the documentation through the base URL.

## Deployment
- This project has been deployed using [AWS Elastic Beanstalk](https://aws.amazon.com/ko/elasticbeanstalk/?trk=3d211853-d899-491e-bd5a-fb5f17de6f0f&sc_channel=ps&ef_id=CjwKCAjwg-24BhB_EiwA1ZOx8toyJDcUjiqv9TNAK3Gvkl29AqKEZgORomacVk1wcx8AsY3TYG3M3RoCcqkQAvD_BwE:G:s&s_kwcid=AL!4422!3!651510175878!e!!g!!elasticbeanstalk!19835789747!147297563979).
- Elastic Beanstalk automates the management of application infrastructure, allowing developers to focus on writing code.
- It provides features such as capacity provisioning, load balancing, auto-scaling, and application health monitoring.

## Prerequisites
Before running this project, you need to make the following configuration changes:
- Database Configuration
  - Modify the database-related settings in both application.properties and build.gradle files.
- AI Server URL
  - In the application.properties file, update the ai.server.url to point to your AI Server.

These configuration steps are crucial for the proper functioning of the application. Ensure that you have the correct database credentials and the appropriate URL for the ai server before attempting to run the project.

## How to Configure
- build.gradle
  - Database configuration:
  ```
  dependencies {
      implementation 'org.mariadb.jdbc:mariadb-java-client' // Replace with your_database_driver if necessary
  }
  ```
- application.properties
  - Database configuration:
  ```
  spring.datasource.driver-class-name=your_database_driver_class_name
  spring.datasource.url=jdbc:your_database_type://your_database_url:your_port/your_database_name
  spring.datasource.username=your_database_username
  spring.datasource.password=your_database_password
  ```
  - AI server URL:
  ```
  ai.server.url=http://your-ai-server-url
  ```
