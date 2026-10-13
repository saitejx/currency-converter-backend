# Currency Converter Backend

A RESTful backend service for currency conversion and transaction history management built with **Spring Boot** and **Java 21**, backed by an embedded **H2 Database**.

---

##  Features

- **Real-Time Currency Conversion**: Convert values between supported fiat currencies (`INR`, `USD`, `EUR`, `GBP`, `JPY`, `AED`).
- **Transaction History (CRUD)**:
  - Automatically persist currency conversions.
  - Query conversion history records.
  - Retrieve details for a specific conversion by ID.
  - Update or edit saved records.
  - Delete past conversion records.
- **Embedded Persistent Storage**: Configured with file-based H2 database (`./data/currencydb`).
- **H2 Web Console**: Inspect database tables directly via the web browser.
- **Docker Ready**: Multi-stage build Dockerfile provided for production and cloud deployments (e.g., Render, Railway, AWS).

---

## 🛠️ Tech Stack & Dependencies

- **Java**: 21
- **Framework**: Spring Boot 4.x / Spring WebMVC
- **Persistence**: Spring Data JPA / Hibernate
- **Database**: H2 Database (File-based storage)
- **Validation**: Jakarta Validation / Hibernate Validator
- **Build Tool**: Maven (`mvnw` wrapper included)
- **Containerization**: Docker (Eclipse Temurin 21)

---

## 📁 Project Structure

```text
backend/
├── src/
│   ├── main/
│   │   ├── java/com/teja/currency_converter/
│   │   │   ├── controller/             # REST endpoints (ConversionController)
│   │   │   ├── model/                  # Entities & DTOs (Conversion, ConversionRequest)
│   │   │   ├── repository/             # Spring Data JPA Repository
│   │   │   ├── service/                # Business logic (ConversionService, CurrencyService)
│   │   │   └── CurrencyConverterApplication.java
│   │   └── resources/
│   │       └── application.properties  # App configurations & H2 settings
│   └── test/                           # Unit & integration tests
├── data/                               # Generated H2 database files
├── Dockerfile                          # Multi-stage Docker build file
├── pom.xml                             # Maven project dependencies
└── mvnw / mvnw.cmd                     # Maven wrapper scripts
```

---

## ⚙️ Prerequisites

- **Java Development Kit (JDK)**: Java 21 or later
- **Maven**: Version 3.8+ (optional, you can use `./mvnw` or `mvnw.cmd`)
- **Docker** *(optional)*: For containerized execution

---

## 🚀 Getting Started

### 1. Clone & Navigate to Backend
```bash
cd currency-converter/backend
```

### 2. Run the Application

#### Using Maven Wrapper (Windows PowerShell / CMD):
```powershell
.\mvnw.cmd spring-boot:run
```

#### Using Maven Wrapper (Linux / macOS):
```bash
./mvnw spring-boot:run
```

#### Using Installed Maven:
```bash
mvn spring-boot:run
```

The application will start on port **`8080`** by default (or the port defined by the `PORT` environment variable):  
`http://localhost:8080`

---

## 🗄️ H2 Database Console

You can access the embedded H2 console during development:

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:file:./data/currencydb`
- **User Name**: `sa`
- **Password**: *(leave blank)*

---

## 📡 API Endpoints Reference

Base URL: `/api/conversions` (CORS enabled for all origins)

| Method | Endpoint | Description | Request Body | Response Status |
|---|---|---|---|---|
| `POST` | `/api/conversions/convert` | Calculate conversion & save record | `ConversionRequest` JSON | `201 Created` |
| `POST` | `/api/conversions` | Create/save a conversion record directly | `Conversion` JSON | `201 Created` |
| `GET` | `/api/conversions` | Retrieve all conversion history | *None* | `200 OK` |
| `GET` | `/api/conversions/{id}` | Get conversion by ID | *None* | `200 OK` |
| `PUT` | `/api/conversions/{id}` | Update an existing record | `Conversion` JSON | `200 OK` |
| `DELETE` | `/api/conversions/{id}` | Delete a conversion by ID | *None* | `204 No Content` |

---

### Request & Response Examples

#### 1. Convert Currency
**Endpoint**: `POST /api/conversions/convert`

**Request Body**:
```json
{
  "amount": 100.00,
  "fromCurrency": "USD",
  "toCurrency": "EUR"
}
```

**Response** (`201 Created`):
```json
{
  "id": 1,
  "amount": 100.0000,
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "exchangeRate": 0.922222,
  "convertedAmount": 92.2222,
  "createdAt": "2026-10-13T22:45:00"
}
```

#### 2. Get All History
**Endpoint**: `GET /api/conversions`

**Response** (`200 OK`):
```json
[
  {
    "id": 1,
    "amount": 100.0000,
    "fromCurrency": "USD",
    "toCurrency": "EUR",
    "exchangeRate": 0.922222,
    "convertedAmount": 92.2222,
    "createdAt": "2026-10-13T22:45:00"
  }
]
```

#### 3. Update a Conversion Record
**Endpoint**: `PUT /api/conversions/1`

**Request Body**:
```json
{
  "amount": 150.00,
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "exchangeRate": 0.922222,
  "convertedAmount": 138.3333
}
```

**Response** (`200 OK`): Updated `Conversion` object.

#### 4. Delete Record
**Endpoint**: `DELETE /api/conversions/1`

**Response** (`204 No Content`)

---


