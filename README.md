# CSIT6910D_IP

## Project Structure Description
For your convenience in understanding and running the project, I will introduce to you the general functions of each module (folder) mentioned above:
1. To increase code readability, almost every .java file in the project has its purpose indicated and a large number of comments have been added.
2. docker: This folder contains a Docker Script that includes Kafka related docker images.
3. lbs-admin: This folder is the controller layer of the project, which contains the program's runtime entry point, as well as Kafka consumers, data flow scripts, consumer configuration files, and more.
4. lbs-service: This folder is the service layer of the project, which defines the interfaces and implementations of the service layer. It mainly handles business logic related to location services, such as processing location data, generating map information, etc.
5. lbs-common: This folder contains classes common to the entire project, such as API response encapsulation, exception handling, status code enumeration, etc.
6. lbs-shared: This folder contains configuration and entity classes for project sharing, such as MyBatis-Plus configuration, Redis configuration, entities, and VO classes.
7. kafka_Lbs.sql: The database script file for the project.
8. pom.xml: Project related dependencies.


## Issues to note when running this project:
1. Please ensure that the Kafka related environment is configured correctly first.
2. Please run the SQL file first to generate the database.
3. After starting the project, it is necessary to run the data flow script "DataStream. class" to simulate the generation of user location data.

