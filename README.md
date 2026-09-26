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
```
## 🗄️ Database Setup
1. Open MySQL Workbench or your MySQL command-line client.
2. Execute the following SQL script to initialize the database:
```text
CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

-- 1. Admin authentication table
CREATE TABLE IF NOT EXISTS admin_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL
);

-- 2. Student details table
CREATE TABLE IF NOT EXISTS students (
    roll_no VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course VARCHAR(50) NOT NULL,
    branch VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    contact VARCHAR(15)
);

-- 3. Results and marks table
CREATE TABLE IF NOT EXISTS results (
    result_id INT AUTO_INCREMENT PRIMARY KEY,
    roll_no VARCHAR(20) NOT NULL,
    semester INT NOT NULL,
    subject1 DECIMAL(5,2) DEFAULT 0.00,
    subject2 DECIMAL(5,2) DEFAULT 0.00,
    subject3 DECIMAL(5,2) DEFAULT 0.00,
    subject4 DECIMAL(5,2) DEFAULT 0.00,
    subject5 DECIMAL(5,2) DEFAULT 0.00,
    gpa DECIMAL(4,2),
    status VARCHAR(10),
    FOREIGN KEY (roll_no) REFERENCES students(roll_no) ON DELETE CASCADE,
    UNIQUE KEY uq_student_sem (roll_no, semester)
);

-- Insert default admin account (Username: admin | Password: admin123)
INSERT INTO admin_users (username, password_hash, full_name)
VALUES ('admin', SHA2('admin123', 256), 'System Administrator')
ON DUPLICATE KEY UPDATE id=id;
```
### 📊 Grading & Evaluation Matrix

| Marks Range | Grade Point | Performance Classification | Status |
| :--- | :---: | :--- | :---: |
| 90 – 100 | **10.0** | Outstanding | PASS |
| 80 – 89 | **9.0** | Excellent | PASS |
| 70 – 79 | **8.0** | Very Good | PASS |
| 60 – 69 | **7.0** | Good | PASS |
| 50 – 59 | **6.0** | Above Average | PASS |
| 40 – 49 | **5.0** | Pass Grade | PASS |
| Below 40 | **0.0** | Unsatisfactory | FAIL |
