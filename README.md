
# Back Management System (OOPD Project)

A desktop-based **Back Management System** focused on **withdrawal requests** and **client-side operations**, built using **Java SwingX** and managed with **Maven**. This application serves as an OOPD course project, demonstrating strong object-oriented principles in a real-world GUI application.

## 💼 Key Features

- 🧑‍💻 **Client Dashboard**: Interactive interface for clients to perform actions.
- 💸 **Withdrawal Requests**: Submit, view, and manage withdrawal transactions.
- 📈 **Status Tracking**: Track the status of each request in real-time.
- 🖼️ **SwingX UI**: Enhanced UI experience using SwingX components.
- 🧩 **MVC Architecture**: Clean separation of model, view, and controller logic.
- 🔄 **Design Patterns**: Uses Observer, Adapter, and Singleton patterns.

## 🛠️ Technologies Used

- ☕ Java 8+
- 🖼️ SwingX (`org.jdesktop.swingx`)
- 📦 Maven (Project build and dependency management)
- 🧪 JUnit (Testing framework)

## 🏗️ Project Structure

```
back-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── model/
│   │   │   │   ├── Client.java
│   │   │   │   ├── WithdrawalRequest.java
│   │   │   │   └── TransactionStatus.java
│   │   │   ├── view/
│   │   │   │   ├── ClientDashboard.java
│   │   │   │   └── WithdrawalPanel.java
│   │   │   ├── controller/
│   │   │   │   └── ClientController.java
│   │   │   └── App.java
│   │
│   └── test/
│       └── java/
│           └── model/
│               └── WithdrawalRequestTest.java
│
├── pom.xml
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- Java JDK 8 or above
- Maven 3.6+
- Any Java IDE (IntelliJ, Eclipse, NetBeans, etc.)

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/back-management-system.git
   cd back-management-system
   ```

2. Compile the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="App"
   ```

## 🧪 Running Tests

To execute unit tests:
```bash
mvn test
```

## 🔮 Future Enhancements

- Integration with a backend database (MySQL/SQLite).
- User authentication and authorization.
- Exporting withdrawal reports to PDF/Excel.
- Notification system for request updates.

## 👥 Authors

- 6531503201	Nattawat Nattachanasit
- 6631501041	Tanakrit Somboon
- 6431503089	Thanitsara Umpondol
- 6731503117	Vachira Loyweaha
- 6631503129	Sai Shang Hlang
- 6531503182	Shwe Sin Tun

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
