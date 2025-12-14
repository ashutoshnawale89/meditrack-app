# MediTrack Command-Line Usage Guide

## Overview
MediTrack Healthcare Management System provides both **interactive menu mode** and **command-line interface** for flexible operation and automated testing.

---

## 📋 Table of Contents
1. [Interactive Mode](#interactive-mode)
2. [Command-Line Mode](#command-line-mode)
3. [Testing Suite](#testing-suite)
4. [Build and Run](#build-and-run)
5. [Examples](#examples)

---

## 🖥️ Interactive Mode

### Launch Interactive Menu
```bash
# Standard execution
java com.airtribe.meditrack.Main

# Or using Maven
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

### Menu Options
```
===== MediTrack Main Menu =====
1. 🩺 Add Doctor
2. 👤 Add Patient
3. 📅 Book Appointment
4. ❌ Cancel Appointment
5. 💰 Generate Bill
6. 💳 Pay Bill
7. 📋 List All Appointments
8. 📊 Download Bill Summary (Excel)
0. 🚪 Exit
```

---

## ⌨️ Command-Line Mode

### Display Help
```bash
java com.airtribe.meditrack.Main --help
# or
java com.airtribe.meditrack.Main -h
```

**Output:**
```
MediTrack Healthcare Management System - Help
============================================================
Usage: java com.airtribe.meditrack.Main [options]

Options:
  (no args)         Launch interactive menu mode
  --help, -h        Show this help message
  --version, -v     Display version information
  --demo            Run demonstration with sample data

Features:
  • Doctor management with specialization tracking
  • Patient registration and medical records
  • Appointment scheduling and management
  • Billing and payment processing
  • Excel report generation
```

### Show Version Information
```bash
java com.airtribe.meditrack.Main --version
# or
java com.airtribe.meditrack.Main -v
```

**Output:**
```
MediTrack Healthcare Management System
Version: 1.0.0
Java Version: 21.0.x
Build Date: 2025-12-14
© 2025 MediTrack Development Team
```

### Run Demo Mode
```bash
java com.airtribe.meditrack.Main --demo
```

**What it does:**
- Creates sample doctor (Dr. Demo Smith - Cardiology)
- Registers sample patient (Demo Patient)
- Books sample appointment
- Generates sample bill
- Loads system with test data for exploration

---

## 🧪 Testing Suite

### Run All Tests
```bash
# Default - runs all test suites
java com.airtribe.meditrack.test.TestRunner

# Explicit all tests
java com.airtribe.meditrack.test.TestRunner --test=all
```

### Run Specific Test Suites

#### Doctor Tests Only
```bash
java com.airtribe.meditrack.test.TestRunner --test=doctors
```

#### Patient Tests Only
```bash
java com.airtribe.meditrack.test.TestRunner --test=patients
```

#### Appointment Tests Only
```bash
java com.airtribe.meditrack.test.TestRunner --test=appointments
```

#### Billing Tests Only
```bash
java com.airtribe.meditrack.test.TestRunner --test=bills
```

### Verbose Mode
```bash
# Show detailed output for each test
java com.airtribe.meditrack.test.TestRunner --verbose
# or
java com.airtribe.meditrack.test.TestRunner -v

# Combine with specific suite
java com.airtribe.meditrack.test.TestRunner --test=doctors --verbose
```

### Test Help
```bash
java com.airtribe.meditrack.test.TestRunner --help
```

---

## 🔨 Build and Run

### Using Maven

#### Compile
```bash
mvn clean compile
```

#### Run Main Application
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

#### Run with Arguments
```bash
# Show help
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main" -Dexec.args="--help"

# Run demo
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main" -Dexec.args="--demo"
```

#### Run Tests
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner"

# With arguments
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner" -Dexec.args="--test=doctors --verbose"
```

#### Package as JAR
```bash
mvn clean package

# Run the JAR
java -jar target/meditrack-app-1.0-SNAPSHOT.jar
```

### Direct Java Execution

#### From Project Root
```bash
# Compile
javac -d target/classes -sourcepath src/main/java src/main/java/com/airtribe/meditrack/Main.java

# Run
java -cp target/classes com.airtribe.meditrack.Main
```

#### Run Tests
```bash
java -cp target/classes com.airtribe.meditrack.test.TestRunner --test=all
```

---

## 📝 Examples

### Example 1: Quick Start Demo
```bash
# Step 1: Run demo to load sample data
java com.airtribe.meditrack.Main --demo

# Step 2: Launch interactive mode to explore
java com.airtribe.meditrack.Main

# Step 3: Run tests to verify functionality
java com.airtribe.meditrack.test.TestRunner --verbose
```

### Example 2: Development Workflow
```bash
# 1. Compile project
mvn clean compile

# 2. Run comprehensive tests
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.test.TestRunner" \
  -Dexec.args="--test=all --verbose"

# 3. If tests pass, run application
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

### Example 3: Testing Individual Components
```bash
# Test doctor functionality in detail
java com.airtribe.meditrack.test.TestRunner --test=doctors --verbose

# Test billing with verbose output
java com.airtribe.meditrack.test.TestRunner --test=bills --verbose

# Quick patient tests
java com.airtribe.meditrack.test.TestRunner --test=patients
```

### Example 4: CI/CD Pipeline
```bash
#!/bin/bash
# build-and-test.sh

echo "Building MediTrack..."
mvn clean package

echo "Running test suite..."
java -cp target/classes com.airtribe.meditrack.test.TestRunner --test=all

if [ $? -eq 0 ]; then
    echo "All tests passed!"
    exit 0
else
    echo "Tests failed!"
    exit 1
fi
```

---

## 🎯 Use Cases

### For Developers
```bash
# During development - run specific tests
java com.airtribe.meditrack.test.TestRunner --test=doctors --verbose

# Quick functionality check
java com.airtribe.meditrack.Main --demo
```

### For QA/Testing
```bash
# Comprehensive test run
java com.airtribe.meditrack.test.TestRunner --test=all --verbose > test-results.log

# Verify specific feature
java com.airtribe.meditrack.test.TestRunner --test=bills
```

### For Demonstrations
```bash
# Load sample data
java com.airtribe.meditrack.Main --demo

# Then show interactive features
java com.airtribe.meditrack.Main
```

### For Production Deployment
```bash
# Package application
mvn clean package -DskipTests

# Run packaged JAR
java -jar target/meditrack-app-1.0-SNAPSHOT.jar
```

---

## 🔍 Test Output Example

```
============================================================
  MediTrack Manual Test Runner v1.0
  Comprehensive Testing Suite for Healthcare Management
============================================================
Running test suite: ALL
Verbose mode: ON

============================================================

🩺 Testing Doctor Functionality
------------------------------------------------------------
✅ PASS: Add Doctor
✅ PASS: Find Doctor by ID
✅ PASS: Find Doctor by Name
✅ PASS: Add Multiple Doctors
✅ PASS: Find Doctors by Specialization
✅ PASS: Find Doctors by Availability
✅ PASS: Calculate Average Experience
✅ PASS: Group Doctors by Specialization
✅ PASS: Find Top Experienced Doctors
✅ PASS: Update Doctor

🏥 Testing Patient Functionality
------------------------------------------------------------
✅ PASS: Add Patient
✅ PASS: Find Patient by ID
...

============================================================
  Test Execution Summary
============================================================
Total Tests:  40
Passed:       40 ✅
Failed:       0 ❌
Success Rate: 100.00%
============================================================

🎉 All tests passed! System is working correctly.
```

---

## 📚 Additional Resources

### JavaDoc Generation
```bash
# Generate JavaDocs
mvn javadoc:javadoc

# View in browser
open target/site/apidocs/index.html
```

### IDE Integration

#### IntelliJ IDEA
```
Right-click Main.java → Run 'Main.main()'
Right-click Main.java → Modify Run Configuration → Add Program arguments
```

#### Eclipse
```
Run → Run Configurations → Arguments tab → Program arguments
```

#### VS Code
Create `.vscode/launch.json`:
```json
{
    "configurations": [
        {
            "type": "java",
            "name": "MediTrack Main",
            "request": "launch",
            "mainClass": "com.airtribe.meditrack.Main",
            "args": ""
        },
        {
            "type": "java",
            "name": "MediTrack Demo",
            "request": "launch",
            "mainClass": "com.airtribe.meditrack.Main",
            "args": "--demo"
        },
        {
            "type": "java",
            "name": "Test Runner",
            "request": "launch",
            "mainClass": "com.airtribe.meditrack.test.TestRunner",
            "args": "--test=all --verbose"
        }
    ]
}
```

---

## ⚡ Quick Reference

| Command | Description |
|---------|-------------|
| `java Main` | Launch interactive mode |
| `java Main --help` | Show help |
| `java Main --version` | Show version |
| `java Main --demo` | Run demo |
| `java TestRunner` | Run all tests |
| `java TestRunner --test=doctors` | Test doctors only |
| `java TestRunner --verbose` | Verbose test output |
| `mvn clean compile` | Build project |
| `mvn exec:java` | Run via Maven |
| `mvn javadoc:javadoc` | Generate docs |

---

## 🐛 Troubleshooting

### Common Issues

**Issue:** `ClassNotFoundException`
```bash
# Solution: Ensure you're in the correct directory and classes are compiled
mvn clean compile
java -cp target/classes com.airtribe.meditrack.Main
```

**Issue:** Apache POI dependencies not found
```bash
# Solution: Download dependencies
mvn clean install
```

**Issue:** Java version incompatible
```bash
# Check Java version (requires Java 8+)
java -version

# Update pom.xml if needed
```

---

## 📞 Support

For issues or questions:
- Review JavaDoc documentation
- Run `--help` commands for usage
- Check test output for diagnostics
- Review source code comments

---

**Happy MediTrack-ing! 🏥**
