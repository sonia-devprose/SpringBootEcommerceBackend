E-commerce Backend API
This project is a robust, scalable e-commerce backend API built with Spring Boot. It offers a RESTful interface for product, customer, and shopping cart management, following a clear layered architecture.

✨ Features
This API provides endpoints for:

Product Management: CRUD operations, search by name or price range.

Customer Management: CRUD operations, search by email or name.

Shopping Cart: Add, retrieve, update quantity, remove specific items, and clear cart for customers.

API Root: A welcome HTML page with endpoint listings.

In-Memory Database (H2): For lightweight development and testing.

Basic Security: Configured for basic authentication (endpoints generally permitted for demo ease).

🚀 Technologies Used
Spring Boot: Main framework.

Spring Data JPA: Data access.

Hibernate: ORM.

H2 Database: Embedded database.

Lombok: Boilerplate reduction.

Maven: Build tool.

Java 21: Programming language.

📦 Project Structure
Standard Spring Boot layered architecture:

src/main/java/com/ecommerce/demo/
├── config/             # Spring Security
├── controller/         # REST API endpoints
├── dto/                # Data Transfer Objects
├── entity/             # JPA Entities (Database mapping)
├── exception/          # Custom exceptions
├── repository/         # Spring Data JPA Data access
└── service/            # Business Logic
└── DemoApplication.java # Main entry point

🛠️ How to Run
Prerequisites
Java Development Kit (JDK) 21+

Apache Maven (configured in PATH)

Steps to Run
Clone repository:

git clone [your-repo-url]
cd demo

Clean & Install:

mvn clean install

Run Application:

mvn spring-boot:run

Accessing API & H2 Console
Once running:

API Root: http://localhost:8080/api (HTML welcome page)

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:ecommercedb

User Name: sa

Password: (BLANK)

Example API Endpoints (via Postman)
All endpoints use the /api prefix from server.servlet.context-path.

Products:

GET /api/products (All)

GET /api/products/{id} (By ID)

POST /api/products (Create ProductDto: {"name": "Smartphone XYZ", "price": 799.99, "description": "Latest model"})

PUT /api/products/{id} (Update ProductDto)

DELETE /api/products/{id}

Customers:

GET /api/customers (All)

GET /api/customers/{id} (By ID)

POST /api/customers (Create CustomerDto: {"name": "Jane Doe", "email": "jane.doe@example.com"})

PUT /api/customers/{id} (Update CustomerDto)

DELETE /api/customers/{id}

Cart:

GET /api/cart/{customerId} (Customer's cart)

POST /api/cart (Add CartItemRequest: {"productId": 1, "customerId": 1, "quantity": 1})

PUT /api/cart/items/{cartItemId}/quantity?quantity={newQuantity} (Update quantity)

DELETE /api/cart/items/{cartItemId} (Remove item)

DELETE /api/cart/{customerId} (Clear cart)
