# 🛒 E-Commerce Backend (Ktor, Clean Architecture)

This is a backend system for an E-Commerce app built with **Ktor**, following **Clean Architecture** principles and using **SOLID design** principles.

## 📦 Features

- ✅ User Authentication (JWT)
- ✅ Product CRUD
- ✅ Shopping Cart Management
- ✅ Order Management
- ✅ Payment Processing
- ✅ Admin Dashboard (View Users & Products)
- ✅ Role-based Access (User/Admin)
- ✅ Status Page Error Handling

## 🏗️ Project Structure (Clean Architecture)

```
project-root/
├── application.conf
├── build.gradle.kts
├── Main.kt

├── config/                # App Configs (Routing, Security, DB, etc.)
├── di/                    # Koin DI Modules

├── domain/                # Business logic layer (UseCases, Repos, Entities)
├── data/                  # Data access layer (DTOs, Tables, Mappers, Impl)
├── presentation/          # Controllers & Routes
```

## 🔐 Authentication

- JWT-based login/signup
- Role-based route access for admin

## 🧠 Technologies Used

- Kotlin + Ktor
- Exposed ORM (`org.jetbrains.exposed.sql.Database`)
- Koin (Dependency Injection)
- kotlinx.serialization (JSON)
- Postman (API Testing)

## 🧪 Status Page Error Handling

Handles and responds with user-friendly messages for:
- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found
- 500 Internal Server Error

## 🧰 Usage

- Designed for modular development using Clean Architecture.
- Every feature (auth, cart, order, etc.) has its own domain, data, and presentation layers.
- Plug and play approach—new features can be added following existing structure.

## 🚫 Not Yet Included

- ❌ Media upload/handling (images, videos, PDFs)
- ❌ Cloud storage or file streaming
- ❌ HikariCP or external connection pooling

> These features can be added as per future use cases.

## ✅ How to Run

```bash
./gradlew run
```
Server will start at `http://localhost:8080`.

---

## 👨‍💻 Author

Made with 💻 by [Khubaib](khubaibbaloch)
