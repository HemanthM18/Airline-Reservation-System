# ✈️ Airline Reservation System – Java + MySQL + Maven

A complete Airline Reservation System built using **Java**, **MySQL**, and **Maven** following an MVC structure with DAO, service layers, transactions, and relational database design.

This project supports:

- Passenger Management  
- Flight Management  
- Searching Flights  
- Booking Flights (Transactional)
- Automatic Ticket Generation  
- Payment Recording  
- Seat Availability Tracking  

---

## 🚀 Features

### 👤 Passenger Management
- Add new passengers  
- List all passengers  

### ✈️ Flight Management
- Add flights  
- Search flights by origin → destination  
- Real-time seat availability  

### 🧾 Booking System (Transactional)
- Uses `SELECT ... FOR UPDATE` to lock rows  
- Generates booking  
- Issues ticket  
- Records payment  
- Updates seats booked  
- Commits only when all steps succeed  

### 💳 Payments
- Supports UPI / CARD  
- Auto-generates transaction IDs  

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Programming Language | Java 17 |
| Backend Framework | Core Java (OOP, DAO, Service) |
| Database | MySQL 8.0 |
| Build Tool | Maven |
| IDE | VS Code |
| Architecture | MVC + DAO + Service Layer |
| Transactions | JDBC + MySQL (commit/rollback) |

---

## 📂 Project Structure

airlinereservationsystem/
│
├── src/main/java/com/airline/
│ ├── App.java
│ ├── db/DBConnection.java
│ ├── dao/
│ │ ├── PassengerDAO.java
│ │ ├── FlightDAO.java
│ │ ├── BookingDAO.java
│ │ ├── TicketDAO.java
│ │ └── PaymentDAO.java
│ ├── models/
│ │ ├── Passenger.java
│ │ ├── Flight.java
│ │ ├── Booking.java
│ │ ├── Ticket.java
│ │ └── Payment.java
│ └── service/BookingService.java
│
├── pom.xml
└── README.md

## ⚙️ Running the Project in VS Code
- Install JDK 17
- Install MySQL 8
- Install VS Code extensions: "Extension Pack for Java", "Maven for Java"
- ✔ Clone or open the folder in VS Code
- ✔ Build the project: .\mvnw.cmd clean install
- ✔ Run the application: .\mvnw.cmd exec:java
- If PowerShell gives issues, use the exact working command you used: .\mvnw.cmd exec:java "-Dexec.mainClass=com.airline.App"

## Sample Test Flow
- Run the app
- Choose 1 → Add Passenger
- Choose 3 → Search Flights
- Choose 4 → Book Flight
- Check MySQL tables:
    - SELECT * FROM Passenger;
    - SELECT * FROM Flight;
    - SELECT * FROM Booking;
    - SELECT * FROM Ticket;
    - SELECT * FROM Payment;