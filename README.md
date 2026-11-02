# 📱 Swag Labs Mobile Automation Framework

An enterprise-grade, cross-platform mobile test automation framework built from scratch using **Java**, **Appium v2**, **Maven**, and **TestNG**, implementing the **Page Object Model (POM)** design pattern and fully integrated into a **Jenkins CI/CD** pipeline.

---

## 🚀 Key Features & Architecture
* **Design Pattern:** Modular **Page Object Model (POM)** architecture cleanly separating test scripts, business actions, and element locators to maximize code reusability and maintainability.
* **Mobile Test Automation:** Powered by **Appium v2** and the `UiAutomator2` driver for robust execution against Android applications (`com.swaglabsmobileapp`).
* **CI/CD Pipeline Integration:** Automated execution via **Jenkins** on Windows 11, managing environment-aware profile switching (`-Denv=ci`), process lifecycle cleanup (`taskkill`), and background Appium server daemon initialization.
* **Robust Synchronization & Resiliency:** Engineered with custom explicit wait utilities (`WaitUtils`) and dynamic touch action scrolling wrappers to eliminate race conditions, async rendering delays, and emulator flakiness.

---

## 🧰 Technology Stack
* **Language:** Java (JDK 17)
* **Automation Engine:** Appium v2.18.0
* **Test Framework:** TestNG & Maven (Surefire Plugin)
* **CI/CD & Process Management:** Jenkins, Node.js / npm, Windows Batch Scripting
* **Execution Environment:** Android Emulator (API 37) / Windows 11

---

## 📂 Project Directory Structure
```text
SwagLabsAppFramework/
├── src/
│   ├── main/java/com/automation/framework/
│   │   └── pages/             # Page Object classes (Authentication, Cart, Menu, etc.)
│   └── test/java/com/automation/framework/
│       ├── Utils/             # WaitUtils, dynamic scroll wrappers, and helper classes
│       └── BaseTestSwagLabsApp.java # Core setup, capabilities, and lifecycle hooks
├── testng.xml                 # Test Suite suite-level configuration
└── pom.xml                    # Maven dependencies, build profiles, and plugins
```

⚙️ Execution Instructions
1. Local Execution
Ensure your Appium server is active locally on port 4723 and your emulator is running, then execute:

```bash
mvn clean test
```
2. CI/CD Execution (Jenkins)
Trigger the automated pipeline utilizing environment parameters:

```bash
mvn clean test -Denv=ci
```
