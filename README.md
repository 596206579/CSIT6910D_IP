# CSIT6910A_IP

## Project Structure Description
For your convenience in understanding and running the project, I will introduce to you the general functions of each module (folder) mentioned above:
1. docker: This folder contains a Docker Script that includes Kafka related docker images.
2. lbs-admin: This folder is the controller layer of the project, which contains the program's runtime entry point, as well as Kafka consumers, data flow scripts, consumer configuration files, and more.
3. lbs-service: This folder is the service layer of the project, which defines the interfaces and implementations of the service layer. It mainly handles business logic related to location services, such as processing location data, generating map information, etc.
4. lbs-common: This folder contains classes common to the entire project, such as API response encapsulation, exception handling, status code enumeration, etc.
5. lbs-shared: This folder contains configuration and entity classes for project sharing, such as MyBatis-Plus configuration, Redis configuration, entities, and VO classes.
6. kafka_Lbs.sql: The database script file for the project.
7. pom.xml: Project related dependencies.


## Issues to note when running this project:
1. Please ensure that the Kafka related environment is configured correctly first.
2. Please run the SQL file first to generate the database.
3. After starting the project, it is necessary to run the data flow script "DataStream. class" to simulate the generation of user location data.
![image](https://github.com/596206579/CSIT6910D_IP/assets/42928202/2160ebb9-9da4-4399-a3d9-8b81929ad358)

