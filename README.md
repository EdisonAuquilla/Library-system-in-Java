# Library Management System

## 📖 Overview
Library Management System is a Java-based console application designed to manage books, users, loans, and reservations efficiently. The system allows users to browse books, make reservations, borrow and return books, while administrators can manage the book inventory.

## 🛠️ Technologies Used
- **Java** (Core functionality)
- **Maven** (Project management and dependencies)
- **OOP Principles** (Encapsulation, Inheritance, Polymorphism)
- **Collections Framework** (Lists, Sets, and Maps)

## 📂 Project Structure
```
LibraryManagementSystem/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── mycompany/
│   │   │   │   │   ├── sistemadebiblioteca/   # Main package containing classes
│── target/    # Compiled output
│── pom.xml    # Maven dependencies
│── README.md  # Project documentation
```

## 🔹 Features
### User Features
✅ View available books  
✅ Search books by title or genre  
✅ Reserve and cancel book reservations  
✅ Borrow and return books  
✅ View personal loan and reservation history  

### Admin Features
✅ Add and remove books  
✅ Increase or decrease book quantities  
✅ Manage user loans and reservations  
✅ Search books in the inventory  

## 🚀 Installation & Setup
### Prerequisites
- Java 8 or later
- Maven installed

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/LibraryManagementSystem.git
   ```
2. Navigate to the project directory:
   ```bash
   cd LibraryManagementSystem
   ```
3. Compile and run the project:
   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="com.mycompany.sistemadebiblioteca.SistemaDeBiblioteca"
   ```

## 📝 Contributing
If you wish to contribute, fork this repository and submit a pull request. Feel free to report issues or suggest improvements!

## 📜 License
This project is open-source and available under the MIT License.

