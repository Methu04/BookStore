# 📚 Bookstore REST API

This is a Java-based RESTful API for a simple bookstore application, built using **JAX-RS (Jersey)**. which is a standard Java library for building RESTful web services and JSON is used for all request 
and response bodies for the communication between the client and the server. It ensures a lightweight and 
easy to read data format. For the testing of functionalities of the API endpoints and demonstration Postman 
tool was used. Postman allows to send HTTP requests to the API and analyze the responses. HashMaps and 
Lists are used for in memory data storage.  
It allows users to browse books, manage customers and authors, handle carts, and place orders.

---

## 🔧 Technologies Used

- Java
- JAX-RS (Jersey)
- Maven (for dependency management)
- Postman (for API testing)

---

## 🚀 Endpoints

### 📚 Book
  ⚪ POST /books
  
    { 
        "title": The Little Prince, 
        "authId": 5,
        "year": 1943,
        "price": 2550.0,
        "stockQuantity": 20, 
         "bookId": 5, 
        "isbn": "1234dfdfg" 
      }
  ⚪ GET /books  
  ⚪ GET /books/{id}  
  ⚪ PUT /books/{id} 
  
        { 
          "title": The Little Prince, 
          "authId": 5,
          "year": 1943,
          "price": 2550.0,
          "stockQuantity": 30, 
           "bookId": 5, 
          "isbn": "1234dfdfg" 
        }
  ⚪ DELETE /books/{id} 
### 👤 Author
  ⚪ POST /authors 
  
      { 
        "authId": 5, 
        "name": "Louisa May Alcott", 
        "biography": "Louisa May Alcott was an American novelist, short story writer, and poet best known for writing the novel Little Women and its sequels Good Wives, Little Men, and Jo's Boys." 
       }
       
  ⚪ GET /authors  
  ⚪ GET /authors/{id} 
  ⚪ PUT /authors/{id} 
  
      { 
        "authId": 6, 
        "name": "Antoine de SaintExupéry", 
        "biography": "French aviator and writer whose works are the unique testimony of a pilot and a warrior who looked at adventure and danger with a poet's eyes." 
        } 
  ⚪ DELETE /authors/{id}
  ⚪ GET /authors/{id}/books 
### 👤 Customer
  ⚪ POST /customers 
  
      { 
          "customerId": 5,  
          "name": "Down Bow", 
          "email": "down@gmail.com",
          "password": "Down123" 
        }
  ⚪ GET /customers  
  ⚪ GET /customers/{id}  
  ⚪ PUT /customers/{id} 
  
      {   
          "name": "Mollie volt", 
          "email": "mollie@gmail.com",
          "password": "mollie123" 
        }
  ⚪ DELETE /customers/{id} 
### 🛒 Cart
  ⚪ POST /customers/{customerId}/cart/items 
  
      {
          "title": "Moby-Dick", 
          "authId": 1, 
          "year": 1851, 
          "price": 2000.0, 
          "stockQuantity": 9, 
          "bookId": 1, 
          "isbn": "3453dfdfg" 
      }
      
      {
            "title": "Jane Eyre", 
            "authId": 3, 
            "year": 1847, 
            "price": 2500.0, 
            "stockQuantity": 5, 
            "bookId": 3, 
            "isbn": "3343dfdfg" 
        } 
  ⚪ GET /customers/{customerId}/cart   
  ⚪ PUT /customers/{customerId}/cart/items/{bookId}
  
      {
            "title": "Jane Eyre", 
            "authId": 3, 
            "year": 1847, 
            "price": 2500.0, 
            "stockQuantity": 10, 
            "bookId": 3, 
            "isbn": "3343dfdfg" 
        } 
  ⚪ DELETE /customers/{customerId}/cart/items/{bookId} 
### 📦 Order
  ⚪ POST /customers/{customerId}/orders 
  
       [ 
          { 
            "title": "Moby-Dick", 
            "authId": 1, 
            "year": 1851, 
            "price": 2000.0, 
            "stockQuantity": 9, 
            "bookId": 1, 
            "isbn": "3453dfdfg" 
          }, 
          { 
            "title": "Jane Eyre", 
            "authId": 3, 
            "year": 1847, 
            "price": 2500.0, 
            "stockQuantity": 10, 
            "bookId": 3, 
            "isbn": "3343dfdfg" 
          }
        ] 
  ⚪ GET /customers/{customerId}/orders   
  ⚪ GET /customers/{customerId}/orders/{orderId} 

---

## 🖥️ How to Run

1. Import the project into an IDE (e.g., IntelliJ or Eclipse)
2. Run the main application class
3. Use Postman or browser to test endpoints (e.g., `http://localhost:8080/api/books`)

---

## 📌 Notes

- Data is stored in memory, so it resets on each restart.
- Custom exceptions are used for error handling (e.g., BookNotFound, OutOfStock).

---


