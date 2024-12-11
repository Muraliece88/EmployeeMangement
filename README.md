# Employee Management API

## Frameworks Used

Java 17, Spring Boot 3, Maven 3.8.
For removing bolier plate code --- Lombok, --Mapstruct., Records
API Documentation - OpenApi 3.0.3.
Container- Docker
Testing - Junit jupiter, Mockito, MockMvc
No checkstyles files used

## Considerations and assumptions

1. API first approach was chosen
2. No plugins used to generate any class or methods
3. Due to lack of time the developer could configure spring cloud config server to externalize the configuration properties
4. For delete operation in stored procedure, default employee id is considered to be 1
5. Users are configured to be inMemory for simplicity otherwise OAuth2 servers like Keyclock can be configured. As it requires some manuall actions developer
   did not implement for timebeing


## Code setup and run and test(if docker is installed)
1. Clone the project from gitHub
2. Navigate to project root folder and execute
   docker compose build   and then docker compose up
3. This stage will execute all APIs as a seperate image in a container



## Code setup and run and test(if docker is NOT installed)
1. Clone the project from gitHub
2. Navigate to project root folder and execute mvn clean install on the project root directory
3. Navigate to <cloned path>\ABNEmployeeMgmt\crud-service\target, execute java -jar <module>-<version>.jar
5. Navigate to <cloned path>\ABNEmployeeMgmt\banking\target, execute java -jar <module>-<version>.jar
6. UserName and password to access the respective API are defined in the in mememory as security is enabled

## API End points to test()

   **Employee Service:**
      Create employee:
            Endpoint: POST http://localhost:9090/employees/
            Header: ADMIN
            Authentication: Basic , UserName: user, Password: password
            Body:  {
            "first_name": "John",
            "surname": "Joe"  
            }
      Get employee:
            Endpoint: GET http://localhost:9090/employees/1
            Header: USER
            Authentication: Basic , UserName: user, Password: password
      Update employee:
            Endpoint: PUT http://localhost:9090/employees/1
            Header: USER
            Authentication: Basic , UserName: user, Password: password
            Body:  {
            "first_name": "John",
            "surname": "William"  
            }
       Delete employee:
            Endpoint: DELETE http://localhost:9090/employees/1
            Header: ADMIN
            Authentication: Basic , UserName: user, Password: password
            
            
  **Crud Service:**
       Create employee:
            Endpoint: POST http://localhost:9091//api/employees
            Authentication: Basic , UserName: admin, Password: admin
            Body:  {
            "name": "John Joe",
            "role_id": 1  
            } 
       Get employee:
            Endpoint: GET http://localhost:9090/employees/1
            Authentication: Basic , UserName: admin, Password: admin
       Update employee:
            Endpoint: PUT http://localhost:9090/employees/1
            Authentication: Basic , UserName: admin, Password: admin
            Body:  {
            "name": "John Joe",
            "role_id": 1  
            }
       Delete employee:
            Endpoint: DELETE http://localhost:9090/employees/1
            Authentication: Basic , UserName: admin, Password: admin


## Packaging structure

Considering microservice architecture functional packaging is considered so that it can be deployed as seperate artifacts

## Production ready considerations:

Spring boot application runs with active profiles hence properties per environment is placed and the prod profile is configured
1. Spring security is enabled with different credentials for prod with roles for differnt endpoint. Roles can also be configured in tables and can be made run time rather than hardcoding
2. Actuator endpoints are made available to capture health and metrics
3. Passwords are hardcoded in the code or property file as there is no vault integration done and in real time it can be fetched from secure vaults
4. Consider to use config service to externalize the properties so that actuator referesh can be used to update properties