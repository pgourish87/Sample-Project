# Spring-Boot-App - Spring Boot with SQL Database

A comprehensive Spring Boot 3.x CRUD application demonstrating best practices for building REST APIs with Java 17, Maven, and SQL databases (MySQL/PostgreSQL).

## Project Structure

```
spring-boot-app/
├── src/
│   ├── main/
│   │   ├── java/com/example/crud/
│   │   │   ├── controller/          # REST API endpoints
│   │   │   │   └── UserController.java
│   │   │   ├── service/             # Business logic
│   │   │   │   └── UserService.java
│   │   │   ├── repository/          # Data access layer
│   │   │   │   └── UserRepository.java
│   │   │   ├── entity/              # JPA entities
│   │   │   │   └── User.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   └── UserDTO.java
│   │   │   ├── exception/           # Custom exceptions & handlers
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── DuplicateResourceException.java
│   │   │   │   └── ErrorResponse.java
│   │   │   ├── config/              # Configuration classes
│   │   │   │   └── OpenApiConfig.java
│   │   │   └── CrudApplicationMain.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-dev.properties
│   └── test/
│       └── java/com/example/crud/
│           └── UserServiceTest.java
├── pom.xml
├── README.md
└── .gitignore
```

## Features

- ✅ **Complete CRUD Operations**: Create, Read, Update, Delete users
- ✅ **REST API**: Well-designed REST endpoints with proper HTTP methods
- ✅ **Database**: Support for MySQL and PostgreSQL
- ✅ **Validation**: Input validation using Jakarta Bean Validation
- ✅ **Exception Handling**: Global exception handler with meaningful error responses
- ✅ **Pagination**: Built-in pagination support
- ✅ **Logging**: Comprehensive logging with SLF4J
- ✅ **Documentation**: Swagger/OpenAPI API documentation
- ✅ **Transaction Management**: Spring Transaction support
- ✅ **Testing**: Unit tests with JUnit and Mockito
- ✅ **Java 17**: Modern Java features support

## Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- MySQL 8.0+ or PostgreSQL 12+
- Git

## Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/spring-boot-app.git
cd spring-boot-app
```

2. **Create database**

For MySQL:
```sql
CREATE DATABASE crud_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

For PostgreSQL:
```sql
CREATE DATABASE crud_db;
```

3. **Configure database connection**

Edit `src/main/resources/application.properties`:

For MySQL (default):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crud_db
spring.datasource.username=root
spring.datasource.password=root
```

For PostgreSQL:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/crud_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL10Dialect
```

4. **Build the project**
```bash
mvn clean install
```

5. **Run the application**
```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

## API Endpoints

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Create a new user |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/email/{email}` | Get user by email |
| GET | `/api/users/page?page=0&size=10` | Get users with pagination |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Example Requests

**Create User (POST)**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phone": "9876543210",
    "address": "123 Main St"
  }'
```

**Get User by ID (GET)**
```bash
curl http://localhost:8080/api/users/1
```

**Update User (PUT)**
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane@example.com",
    "phone": "9876543210",
    "address": "456 Main St"
  }'
```

**Delete User (DELETE)**
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

## API Documentation

Swagger UI is available at: `http://localhost:8080/swagger-ui.html`

API Docs JSON: `http://localhost:8080/api/v3/api-docs`

## Project Dependencies

### Core Dependencies
- **spring-boot-starter-web**: Web MVC framework
- **spring-boot-starter-data-jpa**: JPA/Hibernate ORM
- **spring-boot-starter-validation**: Bean validation
- **mysql-connector-j**: MySQL JDBC driver
- **postgresql**: PostgreSQL JDBC driver

### Development Tools
- **lombok**: Reduce boilerplate code
- **spring-boot-devtools**: Live reload during development
- **springdoc-openapi-starter-webmvc-ui**: Swagger/OpenAPI UI

### Testing
- **spring-boot-starter-test**: Testing with JUnit and Mockito

## Running Tests

Execute unit tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=UserServiceTest
```

Generate test coverage report:
```bash
mvn test jacoco:report
```

## Configuration Profiles

The application supports different profiles:

- **Default**: Uses MySQL with `ddl-auto=update`
- **dev**: Development profile with enhanced logging (activate with `-Dspring.profiles.active=dev`)

## Database Schema

The application automatically creates the `users` table with the following structure:

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Error Handling

The application provides structured error responses:

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with ID: 1",
  "path": "/api/users/1"
}
```

## Development Guidelines

### Code Structure
- **Controller**: Handles HTTP requests/responses
- **Service**: Contains business logic
- **Repository**: Data access operations
- **Entity**: Database models (JPA)
- **DTO**: Data transfer objects for API
- **Exception**: Custom exceptions and global error handling

### Best Practices
- Use DTOs for API responses (not entities)
- Implement proper validation in DTOs
- Use service layer for business logic
- Handle exceptions gracefully
- Write unit tests for services
- Use logging appropriately

## Build and Run

### Build JAR
```bash
mvn clean package
java -jar target/spring-boot-app-1.0.0.jar
```

### Run with Maven
```bash
mvn spring-boot:run
```

### Run Tests
```bash
mvn clean test
```

### Generate Documentation
```bash
mvn site
```

## Performance Optimization

### Connection Pool Configuration
The application uses HikariCP for database connection pooling:
- Maximum pool size: 10
- Minimum idle connections: 5
- Connection timeout: 20 seconds
- Idle timeout: 5 minutes

### Pagination
For large datasets, use pagination to improve performance:
```bash
curl "http://localhost:8080/api/users/page?page=0&size=20"
```

## Troubleshooting

### Database Connection Issues
1. Verify database is running
2. Check credentials in `application.properties`
3. Ensure database exists
4. Check firewall rules

### Port Already in Use
Change port in `application.properties`:
```properties
server.port=8081
```

### Build Issues
Clean Maven cache:
```bash
mvn clean install -U
```

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Support

For issues or questions, please open an issue on GitHub or contact the development team.

## Roadmap

- [ ] Role-based access control (RBAC)
- [ ] JWT authentication
- [ ] Advanced search and filtering
- [ ] Audit logging
- [ ] Caching with Redis
- [ ] Docker containerization
- [ ] Kubernetes deployment

---

**Last Updated**: December 2024
**Version**: 1.0.0
