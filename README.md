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
