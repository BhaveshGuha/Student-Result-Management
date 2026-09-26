# Student Result Management System

A Java-based desktop application integrated with MySQL to manage student records, subject marks, automated GPA calculation, and examination performance reports.

---

## 📌 Features

- **Secure Authentication**: SHA-256 cryptographic password hashing with prepared statements to prevent SQL injection.
- **Student Profile Management**: Full CRUD operations (register, view, and delete student records with cascading cleanup).
- **Automated GPA Engine**: Dynamic calculation of Grade Points and cumulative GPA on a standard 10-point scale.
- **Pass/Fail Evaluation**: Automatic grading and academic status determination.
- **Tabbed Administrative Dashboard**: Clean interface built with Java Swing for simple navigation between registration, grading, and result generation.
- **Official Marksheet Generation**: View structured, printable academic transcripts filtered by roll number and semester.

---

## 🛠️ Tech Stack & Architecture

- **Language**: Java 17+
- **GUI Framework**: Java Swing / AWT
- **Database**: MySQL 8.0+
- **Database Driver**: MySQL Connector/J (`com.mysql:mysql-connector-j`)
- **Build Tool**: Apache Maven
- **Design Pattern**: Data Access Object (DAO) & Model-View-Controller (MVC) separation

---

## 📂 Project Structure

```text
src/
└── main/
    └── java/
        └── com/
            └── studentapp/
                ├── auth/
                │   └── PasswordUtil.java        # SHA-256 password hashing utility
                ├── dao/
                │   ├── AdminDAO.java            # Admin verification & database auth
                │   ├── StudentDAO.java          # Student CRUD queries
                │   └── ResultDAO.java           # Exam results & marks persistence
                ├── db/
                │   └── DBConnection.java        # JDBC connection pooling & handling
                ├── model/
                │   ├── Student.java             # Student POJO
                │   └── Result.java              # Result POJO
                ├── service/
                │   └── GPACalculator.java       # GPA & grading business logic
                └── ui/
                    ├── LoginFrame.java          # Authentication GUI (Entry Point)
                    └── DashboardFrame.java      # Main management interface
