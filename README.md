# 🏥 MediTrack - Healthcare Management System

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Build](https://img.shields.io/badge/Build-Maven-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Tests](https://img.shields.io/badge/Tests-36%2F36%20Passing-brightgreen.svg)](src/main/java/com/airtribe/meditrack/test/)

A comprehensive healthcare management application built with **Advanced Java 8 features** including lambda expressions, Stream API, and functional programming patterns. MediTrack provides complete functionality for managing doctors, patients, appointments, and billing operations with automated data initialization and Excel reporting.

---

## 🌟 Features

### Core Functionality
- 🩺 **Doctor Management**: Add, search, and manage doctors with specializations and availability
- 👤 **Patient Management**: Register patients, assign doctors, track medical records
- 📅 **Appointment Scheduling**: Book, cancel, and view appointments with conflict detection
- 💰 **Billing System**: Generate bills, process payments, track revenue
- 📊 **Excel Reports**: Export bill summaries to professionally formatted Excel files

### Advanced Features
- ⚡ **Lambda Expressions**: Throughout the codebase for concise, functional code
- 🔄 **Stream API**: For filtering, mapping, grouping, and statistical operations
- 🎯 **Functional Programming**: Predicates, Functions, Consumers, Suppliers
- 📈 **Analytics**: Statistics for doctors, patients, appointments, and bills
- 🧪 **Comprehensive Testing**: 36 automated tests with 100% pass rate
- 💾 **Default Data**: Pre-loaded sample data for instant demonstration

---

## 📋 Table of Contents

- [Quick Start](#-quick-start)
- [Default Data](#-default-data)
- [Project Structure](#-project-structure)
- [System Requirements](#-system-requirements)
- [Installation](#-installation)
- [Running the Application](#-running-the-application)
- [Usage Examples](#-usage-examples)
- [Testing](#-testing)
- [Java 8 Features](#-java-8-features)
- [Documentation](#-documentation)
- [Architecture](#-architecture)

---

## 🚀 Quick Start

### Easiest Way (PowerShell)
```powershell
.\run-meditrack.ps1
```

### Alternative (Batch)
```cmd
run-demo.bat
```

### Manual Execution
```bash
# Compile
mvn clean compile

# Build classpath
mvn dependency:build-classpath -Dmdep.outputFile=cp.txt

# Run (Windows)
$cp = Get-Content cp.txt
java -cp "target/classes;$cp" com.airtribe.meditrack.Main

# Run (Linux/Mac)
java -cp "target/classes:$(cat cp.txt)" com.airtribe.meditrack.Main
```

---

## 📦 Default Data

MediTrack automatically loads sample data on startup:

| Category | Count | Details |
|----------|-------|---------|
| 👨‍⚕️ **Doctors** | 5 | Cardiology, Neurology, Pediatrics, Orthopedics, Dermatology |
| 👤 **Patients** | 5 | Pre-registered with medical records and assigned doctors |
| 📅 **Appointments** | 3 | Scheduled for upcoming days |
| 💵 **Bills** | 2 | $1,500 (paid) + $2,000 (pending) |

**Sample Doctors:**
- Dr. Sarah Johnson (Cardiology) - 15 years experience
- Dr. Michael Chen (Neurology) - 12 years experience
- Dr. Emily Rodriguez (Pediatrics) - 8 years experience
- Dr. James Wilson (Orthopedics) - 20 years experience
- Dr. Priya Sharma (Dermatology) - 10 years experience

See [Default Data Guide](docs/Default_Data_Guide.md) for complete details.

---

## 📁 Project Structure

```
meditrack-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/airtribe/meditrack/
│   │   │       ├── Main.java                    # Entry point with CLI support
│   │   │       ├── constants/
│   │   │       │   ├── AppointmentStatus.java
│   │   │       │   ├── BillStatus.java
│   │   │       │   ├── Constants.java
│   │   │       │   └── Specialization.java
│   │   │       ├── entity/
│   │   │       │   ├── Appointment.java         # Appointment entity
│   │   │       │   ├── Bill.java                # Bill entity (implements Payable)
│   │   │       │   ├── BillSummary.java         # Summary DTO
│   │   │       │   ├── Doctor.java              # Doctor entity (implements Searchable)
│   │   │       │   ├── Patient.java             # Patient entity
│   │   │       │   └── Person.java              # Base person entity
│   │   │       ├── exception/
│   │   │       │   ├── AppointmentNotFoundException.java
│   │   │       │   └── InvalidDataException.java
│   │   │       ├── interfaces/
│   │   │       │   ├── Payable.java             # Payment interface
│   │   │       │   └── Searchable.java          # Search interface
│   │   │       ├── service/
│   │   │       │   ├── AppointmentService.java  # Appointment business logic
│   │   │       │   ├── BillService.java         # Billing business logic
│   │   │       │   ├── DoctorService.java       # Doctor business logic
│   │   │       │   └── PatientService.java      # Patient business logic
│   │   │       ├── test/
│   │   │       │   └── TestRunner.java          # 36 automated tests
│   │   │       └── utils/
│   │   │           ├── DateUtil.java            # Date utilities
│   │   │           ├── FunctionalUtils.java     # 50+ functional utilities
│   │   │           ├── StreamUtils.java         # Advanced stream operations
│   │   │           └── Validator.java           # Data validation
│   │   └── resources/
│   └── test/
├── docs/
│   ├── CLI_Usage_Guide.md                       # Command-line reference
│   ├── Default_Data_Guide.md                    # Default data documentation
│   ├── Java8_Features_Summary.md                # Java 8 features used
│   ├── JVM_Report.md                            # JVM architecture report
│   └── Setup_Instructions.md                    # Detailed setup guide
├── pom.xml                                      # Maven dependencies
├── run-meditrack.ps1                            # PowerShell launcher
├── run-demo.bat                                 # Batch launcher
├── QUICKSTART.md                                # Quick start guide
└── README.md                                    # This file
```

---

## 💻 System Requirements

- **Java**: JDK 21 or higher
- **Maven**: 3.6+ (for building)
- **Memory**: Minimum 512 MB RAM
- **OS**: Windows, Linux, macOS, or any OS with JVM support

---

## 🔧 Installation

### 1. Clone the Repository
```bash
git clone <repository-url>
cd meditrack-app
```

### 2. Verify Java Installation
```bash
java -version
# Should show Java 21 or higher
```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run Tests (Optional)
```bash
mvn test
# Or use TestRunner
java -cp "target/classes" com.airtribe.meditrack.test.TestRunner --test=all
```

---

## 🎮 Running the Application

### Interactive Mode (Default)
```bash
java -cp "target/classes;$(cat cp.txt)" com.airtribe.meditrack.Main
```

**Menu Options:**
```
1. 🩺 Add Doctor
2. 👤 Add Patient
3. 📅 Book Appointment
4. ❌ Cancel Appointment
5. 💵 Generate Bill
6. 💰 Pay Bill
7. 📋 List Appointments
8. 📊 Download Bill Summary (Excel)
9. 👨‍⚕️ List All Doctors
10. 👥 List All Patients
0. 🚪 Exit
```

### Command-Line Mode
```bash
# Show help
java com.airtribe.meditrack.Main --help

# Show version
java com.airtribe.meditrack.Main --version

# Run demo
java com.airtribe.meditrack.Main --demo
```

---

## 📖 Usage Examples

### Example 1: Add a New Doctor
```
Choose option: 1
Doctor ID: 6
Name: Dr. John Smith
Experience (years): 15
Specialization: CARDIOLOGY
Available Days: MONDAY,WEDNESDAY,FRIDAY
From Time (HH:MM): 09:00
To Time (HH:MM): 17:00
```

### Example 2: Book an Appointment
```
Choose option: 3
Patient ID: 1
Appointment Date/Time (yyyy-MM-dd HH:mm): 2025-12-20 14:30
```

### Example 3: Generate Bill
```
Choose option: 5
Appointment ID: 1
Bill Amount: 1500.00
```

### Example 4: Download Excel Report
```
Choose option: 8
📊 Bill summary exported successfully to: Bill_Summary_20251214_163045.xlsx
```

---

## 🧪 Testing

### Run All Tests
```bash
# Using TestRunner
java -cp "target/classes" com.airtribe.meditrack.test.TestRunner --test=all

# Verbose mode
java -cp "target/classes" com.airtribe.meditrack.test.TestRunner --test=all --verbose
```

### Run Specific Test Suites
```bash
# Doctor tests only
java -cp "target/classes" com.airtribe.meditrack.test.TestRunner --test=doctors

# Patient tests only
java -cp "target/classes" com.airtribe.meditrack.test.TestRunner --test=patients

# Appointment tests only
java -cp "target/classes" com.airtribe.meditrack.test.TestRunner --test=appointments

# Bill tests only
java -cp "target/classes" com.airtribe.meditrack.test.TestRunner --test=bills
```

### Test Coverage
- **Total Tests**: 36
- **Success Rate**: 100%
- **Test Suites**: 4 (Doctors, Patients, Appointments, Bills)
- **Each Suite**: 8-10 comprehensive tests

---

## ⚡ Java 8 Features

### Lambda Expressions
```java
// Method reference
doctors.forEach(System.out::println);

// Lambda expression
appointments.stream()
    .filter(apt -> apt.getStatus() == AppointmentStatus.SCHEDULED)
    .forEach(apt -> System.out.println(apt));
```

### Stream API
```java
// Filtering and collecting
List<Doctor> cardiologists = doctorService.getAllDoctors().stream()
    .filter(doc -> doc.getSpecialization() == Specialization.CARDIOLOGY)
    .collect(Collectors.toList());

// Grouping
Map<AppointmentStatus, Long> counts = appointments.stream()
    .collect(Collectors.groupingBy(
        Appointment::getStatus,
        Collectors.counting()
    ));

// Statistics
DoubleSummaryStatistics stats = bills.stream()
    .collect(Collectors.summarizingDouble(Bill::getAmount));
```

### Optional
```java
Optional<Doctor> doctor = doctorService.findById(1L);
doctor.ifPresent(d -> System.out.println("Found: " + d.getName()));
```

### Functional Interfaces
```java
// Predicate
Predicate<Doctor> isExperienced = doc -> doc.getExperience() > 10;

// Function
Function<Patient, String> getName = Patient::getName;

// Consumer
Consumer<Bill> printBill = bill -> System.out.println(bill);
```

### Advanced Features
- **Method References**: `Doctor::getName`, `Integer::sum`
- **Stream Operations**: `filter()`, `map()`, `reduce()`, `groupingBy()`
- **Collectors**: `toList()`, `toSet()`, `groupingBy()`, `summarizingDouble()`
- **FunctionalUtils**: Memoization, currying, predicate composition
- **StreamUtils**: Zip, sliding window, cartesian product

See [Java8_Features_Summary.md](docs/Java8_Features_Summary.md) for complete details.

---

## 📚 Documentation

| Document | Description |
|----------|-------------|
| [QUICKSTART.md](QUICKSTART.md) | Quick start guide for new users |
| [Setup_Instructions.md](docs/Setup_Instructions.md) | Detailed setup instructions |
| [Default_Data_Guide.md](docs/Default_Data_Guide.md) | Default data documentation |
| [CLI_Usage_Guide.md](docs/CLI_Usage_Guide.md) | Command-line reference |
| [Java8_Features_Summary.md](docs/Java8_Features_Summary.md) | Java 8 features showcase |
| [JVM_Report.md](docs/JVM_Report.md) | JVM architecture deep dive |

---

## 🏗️ Architecture

### Design Patterns
- **Service Layer Pattern**: Business logic separation
- **DTO Pattern**: BillSummary for data transfer
- **Repository Pattern**: In-memory data storage
- **Strategy Pattern**: Payment processing
- **Factory Pattern**: Entity creation

### Layer Architecture
```
┌─────────────────────────────────────┐
│     Presentation Layer (Main.java)  │ ← User interaction
├─────────────────────────────────────┤
│     Service Layer                   │ ← Business logic
│  - DoctorService                    │
│  - PatientService                   │
│  - AppointmentService               │
│  - BillService                      │
├─────────────────────────────────────┤
│     Entity Layer                    │ ← Domain models
│  - Doctor, Patient                  │
│  - Appointment, Bill                │
├─────────────────────────────────────┤
│     Utility Layer                   │ ← Cross-cutting concerns
│  - Validator, DateUtil              │
│  - FunctionalUtils, StreamUtils     │
└─────────────────────────────────────┘
```

### Key Technologies
- **Java 21**: Modern Java features
- **Maven**: Dependency management
- **Apache POI**: Excel file generation
- **Stream API**: Functional data processing
- **Lambda Expressions**: Concise code

---

## 🔒 Data Validation

All inputs are validated using functional predicates:

```java
// Validation examples
- Doctor experience: Must be positive
- Patient age: 0-150 years
- Phone numbers: 10 digits
- Appointment dates: Must be future dates
- Bill amounts: Must be positive
- Email format: Valid email pattern
```

---

## 📊 Analytics & Reports

### Built-in Statistics
- **Doctor Statistics**: Average experience, count by specialization
- **Patient Statistics**: Total patients, active count, age demographics
- **Appointment Statistics**: Count by status, upcoming appointments
- **Bill Statistics**: Total revenue, paid/pending breakdown, average bill amount

### Excel Reports
Generated reports include:
- Bill summary with totals
- Patient-wise billing
- Doctor-wise appointments
- Professional formatting with colors and borders

---

## 🐛 Error Handling

Custom exceptions for better error management:
- `InvalidDataException`: Data validation failures
- `AppointmentNotFoundException`: Appointment lookup failures
- Comprehensive error messages with suggestions

---

## 🚀 Performance

### JVM Optimizations
- **JIT Compilation**: Hot code paths compiled to native code
- **Stream Parallelization**: Available for large datasets
- **Memory Efficiency**: In-memory storage with GC optimization
- **Lazy Evaluation**: Streams evaluated only when needed

### Benchmarks
- **Startup Time**: < 2 seconds with default data
- **Data Loading**: 5 doctors, 5 patients, 3 appointments in < 100ms
- **Excel Generation**: 1000 bills in < 1 second
- **Search Operations**: O(1) for ID-based, O(n) for filtered

---

## 🤝 Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests to ensure everything passes
5. Submit a pull request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👥 Authors

**MediTrack Development Team**
- Healthcare Management System
- Advanced Java 8 Implementation
- Comprehensive Testing Framework

---

## 🙏 Acknowledgments

- Oracle Java Documentation
- Apache POI Project
- Java 8 in Action (Book)
- Stream API Best Practices
- AirTribe Community

---

## 📞 Support

For issues, questions, or suggestions:
- Create an issue in the repository
- Consult the [documentation](docs/)
- Review the [Quick Start Guide](QUICKSTART.md)

---

## 🎯 Roadmap

### Future Enhancements
- [ ] Database integration (JDBC/Hibernate)
- [ ] REST API endpoints (Spring Boot)
- [ ] Web UI (React/Angular)
- [ ] Authentication & Authorization
- [ ] Multi-language support
- [ ] Advanced reporting (PDF, Charts)
- [ ] Email notifications
- [ ] Backup & restore functionality

---

## 📈 Version History

### v1.0.0 (Current)
- ✅ Complete CRUD operations
- ✅ Advanced Java 8 features
- ✅ 36 comprehensive tests (100% pass rate)
- ✅ Default data initialization
- ✅ Excel report generation
- ✅ CLI support (--help, --version, --demo)
- ✅ Comprehensive documentation

---

## 🌐 Platform Compatibility

**Write Once, Run Anywhere (WORA)**

MediTrack runs on any platform with JVM:
- ✅ Windows 10/11
- ✅ Linux (Ubuntu, CentOS, etc.)
- ✅ macOS (Intel & Apple Silicon)
- ✅ Docker containers
- ✅ Cloud platforms (AWS, Azure, GCP)

---

**Made with ❤️ using Advanced Java 8 Features**