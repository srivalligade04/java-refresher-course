# ☕ Java Refresher Course

A structured, hands-on refresher course for Java developers looking to revisit core concepts, sharpen their skills, and brush up on modern Java features.

---

## 📚 Table of Contents

- [About](#about)
- [Topics Covered](#topics-covered)
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

This repository serves as a comprehensive Java refresher course covering fundamental and intermediate Java programming concepts. Whether you're returning to Java after a break or want to solidify your understanding of core topics, this course provides practical examples and exercises to help you get back up to speed quickly.

---

## Topics Covered

- ✅ Java Basics (variables, data types, operators)
- ✅ Control Flow (if/else, switch, loops)
- ✅ Object-Oriented Programming (classes, objects, inheritance, polymorphism)
- ✅ Interfaces and Abstract Classes
- ✅ Exception Handling
- ✅ Collections Framework (List, Set, Map)
- ✅ Generics
- ✅ Java Streams and Lambda Expressions
- ✅ File I/O
- ✅ Multithreading and Concurrency

---

## Project Structure

```
java-refresher-course/
├── src/
│   └── main/
│       └── java/         # Java source files organized by topic
├── wiki/                 # Additional documentation and notes
├── pom.xml               # Maven project configuration
├── run.sh                # Shell script to build and run the project
└── README.md
```

---

## Prerequisites

Before running this project, make sure you have the following installed:

- **Java JDK 11+** — [Download here](https://adoptium.net/)
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

You can run individual examples using Maven or the provided shell script:

**Using Maven:**
```bash
mvn exec:java -Dexec.mainClass="com.example.ClassName"
```

**Using the run script:**
```bash
chmod +x run.sh
./run.sh
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
  mvn exec:java -Dexec.mainClass="com.example.YourClassName"
  ```

#### Tips
- If Maven doesn't auto-sync: right-click `pom.xml` → **"Maven > Reload Project"**
- Set JDK: `File > Project Structure > SDKs` → make sure Java 11+ is selected

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
  mvn clean install
  mvn exec:java -Dexec.mainClass="com.example.YourClassName"
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

Additional notes, explanations, and reference material can be found in the [`wiki/`](./wiki) folder of this repository. This includes topic summaries, cheat sheets, and supplementary reading.

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
