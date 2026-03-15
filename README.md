# ☕ Java Refresher Course

> **Package:** `com.futureframeservices.javaRefresherCourse`  
> **Author:** Srivalli Gade | **GitHub:** [@srivalligade04](https://github.com/srivalligade04)  
> **Version:** 1.0.0 | **Java:** 17+

A structured, hands-on Java refresher course designed to take you from rusty → **interview-ready in 4 hours**. Covers 7 core modules with practical code examples, a cheat sheet, and 100+ interview Q&A.

---

## 📚 Table of Contents

- [About](#about)
- [Modules](#modules)
- [4-Hour Study Plan](#️-4-hour-study-plan)
- [Top 10 Interview Topics](#-top-10-interview-topics)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running the Code](#running-the-code)
- [IDE Setup](#ide-setup)
  - [IntelliJ IDEA](#-intellij-idea)
  - [VS Code](#-vs-code)
- [Wiki](#wiki)
- [Contributing](#contributing)
- [License](#license)

---

## About

This repository is a comprehensive Java refresher course covering fundamental to intermediate Java concepts. Whether you're returning to Java after a break or preparing for interviews, this course gives you practical code examples, wiki study guides, a cheat sheet, and 100+ interview questions — all in one place.

---

## Modules

The course is organized into **7 modules** that mirror both the `src/` code and the wiki docs:

| # | Module | Topics |
|---|--------|--------|
| 01 | [Java Basics](https://github.com/srivalligade04/java-refresher-course/wiki/Module-01-Java-Basics) | Primitives, Strings, Arrays, StringBuilder |
| 02 | [OOP](https://github.com/srivalligade04/java-refresher-course/wiki/Module-02-OOP) | Encapsulation, Inheritance, Polymorphism, Interfaces |
| 03 | [Collections](https://github.com/srivalligade04/java-refresher-course/wiki/Module-03-Collections) | List, Set, Map, Queue, Sorting |
| 04 | [Exceptions](https://github.com/srivalligade04/java-refresher-course/wiki/Module-04-Exceptions) | try-catch, Custom Exceptions, I/O |
| 05 | [Multithreading](https://github.com/srivalligade04/java-refresher-course/wiki/Module-05-Multithreading) | Threads, Executors, Atomic, synchronized |
| 06 | [Java 8 Features](https://github.com/srivalligade04/java-refresher-course/wiki/Module-06-Java8-Features) | Lambdas, Streams, Optional, Collectors |
| 07 | [Design Patterns](https://github.com/srivalligade04/java-refresher-course/wiki/Module-07-Design-Patterns) | Singleton, Factory, Builder, Observer, Strategy |

**Bonus Resources:**
- 📋 [Interview Q&A Master](https://github.com/srivalligade04/java-refresher-course/wiki/Interview-Q&A-Master) — 100+ Q&A across all topics
- ⚡ [Cheat Sheet](https://github.com/srivalligade04/java-refresher-course/wiki/Cheat-Sheet) — Quick reference card
- 🚀 [How To Run](https://github.com/srivalligade04/java-refresher-course/wiki/How-To-Run) — Detailed setup guide

---

## ⏱️ 4-Hour Study Plan

| Time | Module | Focus |
|------|--------|-------|
| 0:00 – 0:30 | Module 01 | Primitives, Strings, Arrays |
| 0:30 – 1:15 | Module 02 | OOP — the most interview-heavy topic |
| 1:15 – 2:00 | Module 03 | Collections — HashMap internals! |
| 2:00 – 2:20 | Module 04 | Exceptions — checked vs unchecked |
| 2:20 – 2:50 | Module 05 | Threads — race conditions, ExecutorService |
| 2:50 – 3:20 | Module 06 | Java 8 — Streams & Lambdas |
| 3:20 – 3:40 | Module 07 | Design Patterns — Singleton, Builder |
| 3:40 – 4:00 | 🏆 Review | [Interview Q&A Master](https://github.com/srivalligade04/java-refresher-course/wiki/Interview-Q&A-Master) |

---

## 🔑 Top 10 Interview Topics

1. `==` vs `.equals()` — and why it matters for HashMap
2. How does **HashMap work internally** (hashCode + equals + buckets)
3. **ArrayList vs LinkedList** — when to use which
4. **Checked vs Unchecked** exceptions — with examples
5. **synchronized vs volatile** — what each guarantees
6. **Overloading vs Overriding** — compile-time vs runtime
7. **Abstract class vs Interface** — IS-A vs CAN-DO
8. **`map()` vs `flatMap()`** in Streams
9. **Singleton pattern** — thread-safe implementation
10. **`@Transactional`** — how Spring handles it

---

## Project Structure

```
java-refresher-course/
├── src/
│   └── main/
│       └── java/
│           └── com/futureframeservices/javaRefresherCourse/
│               ├── module01_basics/       # Primitives, Strings, Arrays
│               ├── module02_oop/          # Encapsulation, Inheritance, Polymorphism
│               ├── module03_collections/  # List, Set, Map, Queue
│               ├── module04_exceptions/   # try-catch, Custom Exceptions
│               ├── module05_threads/      # Threads, Executors, Atomic
│               ├── module06_java8/        # Lambdas, Streams, Optional
│               └── module07_patterns/     # Singleton, Factory, Builder
├── wiki/                                  # Study guides per module
├── pom.xml                                # Maven project configuration
├── run.sh                                 # Shell script to build and run all modules
└── README.md
```

---

## Prerequisites

Before running this project, make sure you have the following installed:

- **Java JDK 17+** — [Download here](https://adoptium.net/)
- **Maven 3.6+** — [Download here](https://maven.apache.org/download.cgi)
- A Java IDE such as [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [VS Code](https://code.visualstudio.com/)

Verify your installations:

```bash
java -version
mvn -version
```

---

## Getting Started

1. **Clone the repository:**

```bash
git clone https://github.com/srivalligade04/java-refresher-course.git
cd java-refresher-course
```

2. **Build the project using Maven:**

```bash
mvn clean install
```

---

## Running the Code

**Run all 7 modules at once:**
```bash
mvn compile exec:java
```

**Using the run script:**
```bash
chmod +x run.sh
./run.sh
```

**Run a specific class:**
```bash
mvn exec:java -Dexec.mainClass="com.futureframeservices.javaRefresherCourse.module01_basics.ClassName"
```

---

## IDE Setup

### 🟠 IntelliJ IDEA

#### Import the Project
1. Open IntelliJ → Click **"Open"** (or `File > Open`)
2. Navigate to the cloned `java-refresher-course` folder and select it
3. IntelliJ will **auto-detect** the `pom.xml` — click **"Open as Project"**
4. Wait for Maven to **sync and download dependencies** (progress bar at the bottom)

#### Run the Code
- **Single file:** Open any `.java` file → click the ▶️ **green play button** next to the `main()` method
- **Via Maven:** Open the **Maven panel** (right sidebar) → `Plugins > exec > exec:java`
- **Via Terminal** (inside IntelliJ): `View > Tool Windows > Terminal`, then:
  ```bash
  ./run.sh
  # or
  mvn exec:java -Dexec.mainClass="com.futureframeservices.javaRefresherCourse.module01_basics.ClassName"
  ```

#### Tips
- If Maven doesn't auto-sync: right-click `pom.xml` → **"Maven > Reload Project"**
- Set JDK: `File > Project Structure > SDKs` → make sure **Java 17+** is selected

---

### 🔵 VS Code

#### Install Required Extensions First
Open VS Code → Extensions (`Ctrl+Shift+X`) → install:
- **Extension Pack for Java** *(by Microsoft)* — includes Maven, debugger, language support
- **Maven for Java** *(included in the pack)*

#### Import the Project
1. `File > Open Folder` → select the `java-refresher-course` folder
2. VS Code will **auto-detect** the Maven project and show a notification — click **"Yes"** to import
3. Wait for the Java Language Server to initialize (bottom status bar)

#### Run the Code
- **Single file:** Open any `.java` file → click **"Run"** link that appears above `main()`, or right-click → **"Run Java"**
- **Via Maven sidebar:** Click the **Maven icon** in the left sidebar → expand your project → `Plugins > exec > exec:java`
- **Via Terminal** (`` Ctrl+` ``):
  ```bash
  ./run.sh
  # or
  mvn compile exec:java
  ```

#### Tips
- If Java isn't detected: `Ctrl+Shift+P` → type **"Java: Configure Java Runtime"** → set your JDK path
- To see all Java files: use the **Java Projects** panel in the Explorer sidebar

---

### ⚡ IDE Quick Comparison

| Feature | IntelliJ IDEA | VS Code |
|---|---|---|
| Maven auto-detect | ✅ Automatic | ✅ With extension |
| Run button | ✅ Built-in | ✅ With extension |
| Best for | Full Java development | Lightweight editing |
| Setup effort | Minimal | Requires extensions |

---

## Wiki

The wiki is the **complete study guide** for this course, with one page per module plus bonus resources:

| Page | Description |
|------|-------------|
| [Module 01 – Java Basics](https://github.com/srivalligade04/java-refresher-course/wiki/Module-01-Java-Basics) | Primitives, Strings, Arrays, StringBuilder |
| [Module 02 – OOP](https://github.com/srivalligade04/java-refresher-course/wiki/Module-02-OOP) | Encapsulation, Inheritance, Polymorphism, Interfaces |
| [Module 03 – Collections](https://github.com/srivalligade04/java-refresher-course/wiki/Module-03-Collections) | List, Set, Map, Queue, Sorting |
| [Module 04 – Exceptions](https://github.com/srivalligade04/java-refresher-course/wiki/Module-04-Exceptions) | try-catch, Custom Exceptions, I/O |
| [Module 05 – Multithreading](https://github.com/srivalligade04/java-refresher-course/wiki/Module-05-Multithreading) | Threads, Executors, Atomic, synchronized |
| [Module 06 – Java 8 Features](https://github.com/srivalligade04/java-refresher-course/wiki/Module-06-Java8-Features) | Lambdas, Streams, Optional, Collectors |
| [Module 07 – Design Patterns](https://github.com/srivalligade04/java-refresher-course/wiki/Module-07-Design-Patterns) | Singleton, Factory, Builder, Observer, Strategy |
| [Interview Q&A Master](https://github.com/srivalligade04/java-refresher-course/wiki/Interview-Q&A-Master) | 100+ Q&A across all topics |
| [Cheat Sheet](https://github.com/srivalligade04/java-refresher-course/wiki/Cheat-Sheet) | Quick reference card |

---

## Contributing

Contributions are welcome! If you'd like to add examples, fix bugs, or improve documentation:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-topic`)
3. Commit your changes (`git commit -m "Add: topic description"`)
4. Push to your branch (`git push origin feature/your-topic`)
5. Open a Pull Request

---

## License

This project is open source and available under the [MIT License](LICENSE).

---

> Made with ❤️ by [srivalligade04](https://github.com/srivalligade04)
