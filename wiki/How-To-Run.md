# 🚀 How to Run

> Complete setup and run instructions for the Java Refresher Course.

---

## Prerequisites

| Tool | Version | Download |
|------|---------|----------|
| JDK | 17+ | [adoptium.net](https://adoptium.net/) |
| Maven | 3.6+ | [maven.apache.org](https://maven.apache.org/install.html) |
| Git | Any | [git-scm.com](https://git-scm.com/downloads) |
| IDE | Any | IntelliJ IDEA (recommended) |

---

## Quick Start

```bash
# 1. Clone the repo
git clone https://github.com/srivalligade04/java-refresher-course.git

# 2. Go into project
cd java-refresher-course/javaRefresherCourse

# 3. Compile and run ALL modules
mvn compile exec:java
```

That's it! You'll see output from all 7 modules in sequence.

---

## Run Individual Modules

To run a specific module, create a temporary main or use your IDE:

```bash
# Compile
mvn compile

# Run specific module manually
java -cp target/classes com.futureframeservices.javaRefresherCourse.Main
```

Or in IntelliJ:
1. Open `Module01_JavaBasics.java`
2. Click the green ▶️ next to the `run()` method (add a temporary `main`)
3. Or right-click → Run

---

## Project Structure

```
javaRefresherCourse/
│
├── pom.xml                              ← Maven build config
├── run.sh                               ← One-click run script
│
└── src/main/java/com/futureframeservices/javaRefresherCourse/
    │
    ├── Main.java                        ← Entry point — runs all modules
    │
    ├── basics/
    │   └── Module01_JavaBasics.java     ← Primitives, Strings, Arrays
    │
    ├── oop/
    │   └── Module02_OOP.java            ← Encapsulation, Inheritance, Polymorphism
    │
    ├── collections/
    │   └── Module03_Collections.java    ← List, Set, Map, Queue, Sorting
    │
    ├── exceptions/
    │   └── Module04_Exceptions.java     ← try-catch, Custom Exceptions, I/O
    │
    ├── multithreading/
    │   └── Module05_Multithreading.java ← Threads, Executors, AtomicInteger
    │
    ├── java8/
    │   └── Module06_Java8Features.java  ← Lambdas, Streams, Optional
    │
    └── patterns/
        └── Module07_DesignPatterns.java ← Singleton, Factory, Builder, Observer, Strategy
```

---

## Build Commands

```bash
# Compile only
mvn compile

# Compile and run
mvn compile exec:java

# Run specific class
mvn exec:java -Dexec.mainClass="com.futureframeservices.javaRefresherCourse.Main"

# Package as JAR
mvn package

# Run the JAR
java -jar target/javaRefresherCourse-1.0.0.jar

# Clean build artifacts
mvn clean

# Clean + compile + run
mvn clean compile exec:java
```

---

## Open in IntelliJ IDEA

1. **File → Open** → select the `javaRefresherCourse` folder
2. IntelliJ auto-detects Maven → click **Trust Project**
3. Wait for Maven to sync (bottom right progress bar)
4. Open `Main.java` → click ▶️ next to `main()` method
5. Output appears in the **Run** tab at the bottom

---

## Open in VS Code

```bash
cd javaRefresherCourse
code .
```

Install extensions:
- **Extension Pack for Java** (Microsoft)
- **Maven for Java** (Microsoft)

Then open `Main.java` → click **Run** above the `main()` method.

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| `mvn: command not found` | Install Maven: `brew install maven` (Mac) or [download](https://maven.apache.org/install.html) |
| `java: command not found` | Install JDK from [adoptium.net](https://adoptium.net/) |
| `UnsupportedClassVersionError` | Your JRE is older than Java 17. Update JDK. |
| IntelliJ shows red imports | File → Invalidate Caches → Restart |
| Maven build fails | Run `mvn clean` then retry |
| Port already in use | Not applicable — this is a plain Java project, no server |

---

## Push to GitHub

```bash
# First time
git init
git add .
git commit -m "🚀 Java Refresher Course — FutureFrame Services"
git branch -M main
git remote add origin https://github.com/srivalligade04/java-refresher-course.git
git push -u origin main

# Subsequent updates
git add .
git commit -m "✏️ Update Module 03 collections notes"
git push
```

---

## GitHub Wiki Setup

To add this wiki to your GitHub repo:

1. Go to your repo on GitHub
2. Click the **Wiki** tab
3. Click **Create the first page**
4. For each `.md` file in the `wiki/` folder:
   - Click **New Page**
   - Name it exactly as the filename (without `.md`): e.g., `Module-01-Java-Basics`
   - Paste the contents
   - Click **Save Page**

Or clone the wiki repo and push all files at once:

```bash
# GitHub wiki is a separate git repo
git clone https://github.com/srivalligade04/java-refresher-course.wiki.git
cd java-refresher-course.wiki

# Copy all wiki files
cp /path/to/wiki/*.md .

# Push
git add .
git commit -m "Add full wiki documentation"
git push
```

---

*← [Cheat Sheet](Cheat-Sheet) | [Home →](Home)*
