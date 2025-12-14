# MediTrack Default Data

## Overview

MediTrack automatically loads sample data when the application starts, making it easy to explore features without manual data entry.

## Default Data Loaded

### 👨‍⚕️ Doctors (5)

| ID | Name | Specialization | Experience | Available Days |
|----|------|----------------|------------|----------------|
| 1 | Dr. Sarah Johnson | Cardiology | 15 years | Mon, Wed, Fri |
| 2 | Dr. Michael Chen | Neurology | 12 years | Tue, Thu |
| 3 | Dr. Emily Rodriguez | Pediatrics | 8 years | Mon-Fri |
| 4 | Dr. James Wilson | Orthopedics | 20 years | Wed, Fri |
| 5 | Dr. Priya Sharma | Dermatology | 10 years | Mon, Thu, Fri |

### 👤 Patients (5)

| ID | Name | Age | Medical Record | Assigned Doctor |
|----|------|-----|----------------|-----------------|
| 1 | John Smith | 45 | MRN001 | Dr. Sarah Johnson |
| 2 | Maria Garcia | 32 | MRN002 | Dr. Michael Chen |
| 3 | David Brown | 28 | MRN003 | Dr. Emily Rodriguez |
| 4 | Lisa Anderson | 55 | MRN004 | Dr. James Wilson |
| 5 | Robert Taylor | 38 | MRN005 | Dr. Priya Sharma |

### 📅 Appointments (3)

| ID | Patient | Doctor | Scheduled Date/Time | Status |
|----|---------|--------|---------------------|--------|
| 1 | John Smith | Dr. Sarah Johnson | 2 days from now, 10:00 AM | Scheduled |
| 2 | Maria Garcia | Dr. Michael Chen | 3 days from now, 2:30 PM | Scheduled |
| 3 | David Brown | Dr. Emily Rodriguez | 5 days from now, 11:00 AM | Scheduled |

### 💰 Bills (2)

| ID | Patient | Amount | Status |
|----|---------|--------|--------|
| 1 | John Smith | $1,500.00 | ✅ Paid |
| 2 | Maria Garcia | $2,000.00 | ⏳ Pending |

## How It Works

The `initializeDefaultData()` method is automatically called when `Main.main()` starts:

```java
public static void main(String[] args) {
    // Load default sample data at startup
    initializeDefaultData();
    
    // ... rest of application code
}
```

## Running the Application

### Option 1: PowerShell Script (Recommended)
```powershell
.\run-meditrack.ps1
```

### Option 2: Batch Script
```cmd
run-demo.bat
```

### Option 3: Manual Maven Command
```bash
# Compile
mvn compile

# Build classpath
mvn dependency:build-classpath -Dmdep.outputFile=cp.txt

# Run (Windows)
java -cp "target/classes;$(Get-Content cp.txt)" com.airtribe.meditrack.Main

# Run (Linux/Mac)
java -cp "target/classes:$(cat cp.txt)" com.airtribe.meditrack.Main
```

## What You'll See

When you start the application, you'll see:

```
🔄 Initializing default data...

✅ Loaded 5 doctors
✅ Registered 5 patients
✅ Scheduled 3 appointments
✅ Generated 2 bills (1 paid, 1 pending)

✨ Default data initialized successfully!
   - 5 Doctors available
   - 5 Patients registered
   - 3 Appointments scheduled
   - 2 Bills generated

============================================================
  MediTrack Healthcare Management System v1.0
  Advanced Java 8 Medical Practice Solution
============================================================
```

## Benefits

✅ **Instant Exploration** - No need to manually add data before testing features  
✅ **Realistic Demo** - Pre-configured with diverse specializations and scenarios  
✅ **Testing Ready** - Already has appointments and bills for workflow testing  
✅ **User-Friendly** - New users can immediately see how the system works

## Adding Your Own Data

After the default data loads, you can:
- Add more doctors (Menu option 1)
- Register additional patients (Menu option 2)
- Book new appointments (Menu option 3)
- Generate more bills (Menu option 5)

Your new data will be added to the existing default data!

## Disabling Default Data

If you want to start with a clean system, comment out the initialization call in `Main.java`:

```java
public static void main(String[] args) {
    // initializeDefaultData(); // Comment this line
    
    if (args.length > 0) {
        handleCommandLineArgs(args);
        return;
    }
    // ...
}
```

## Command-Line Usage

Default data is loaded for all modes:

```bash
# Interactive mode (default data loaded)
java com.airtribe.meditrack.Main

# Help (default data loaded)
java com.airtribe.meditrack.Main --help

# Demo mode (default data + demo workflow)
java com.airtribe.meditrack.Main --demo

# Version info (default data loaded)
java com.airtribe.meditrack.Main --version
```

---

**Note:** Default data uses IDs 1-5 for doctors, patients, and appointments. When adding new entities, use IDs 6 and above to avoid conflicts.
