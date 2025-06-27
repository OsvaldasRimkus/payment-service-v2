# Better and improved Payment Service v2

To run with one command use this in project folder: `./mvnw spring-boot:run`

## Tech stack:

**Java** 17+

**Spring Boot** 3.x

**Spring Web**

**Spring Data JPA**

**H2 Database**

**Maven**

**One-command startup using wrapper scripts**

## Useful links:

**Application** starts on: http://localhost:8080

**Swagger** runs on: http://localhost:8080/swagger-ui/index.html#/

**H2 Database console** is on: http://localhost:8080/h2-console/

## Database login details
| Field        |                  Value |
|:-------------|-----------------------:|
| Driver Class |          org.h2.Driver |
| JDBC URL     | jdbc:h2:mem:paymentsdb |
| User Name    |                     sa |
| Password     |                        |

**query to check all payments**: `select * from payment;`