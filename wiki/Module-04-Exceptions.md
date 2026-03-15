# 📘 Module 04 — Exceptions & I/O

> **Package:** `com.futureframeservices.javaRefresherCourse.exceptions`  
> **File:** `Module04_Exceptions.java`  
> **Time:** ~20 minutes

---

## 📋 Topics Covered

- [Exception Hierarchy](#exception-hierarchy)
- [Checked vs Unchecked](#checked-vs-unchecked)
- [try-catch-finally](#try-catch-finally)
- [try-with-resources](#try-with-resources)
- [Custom Exceptions](#custom-exceptions)
- [Exception Chaining](#exception-chaining)
- [Best Practices](#best-practices)
- [Interview Q&A](#-interview-qa)

---

## Exception Hierarchy

```
             Throwable
            /          \
         Error        Exception
         (JVM)       /          \
                 Checked       Unchecked
                 (must handle) (RuntimeException)
                    │               │
             IOException      NullPointerException
             SQLException     ArrayIndexOutOfBounds
             FileNotFound     ClassCastException
             ParseException   IllegalArgumentException
                              IllegalStateException
                              NumberFormatException
                              StackOverflowError ←(Error, don't catch)
```

---

## Checked vs Unchecked

| | Checked | Unchecked |
|---|---|---|
| Extends | `Exception` | `RuntimeException` |
| Must handle? | ✅ Yes (compile error if not) | ❌ Optional |
| Represents | **External failures** | **Programming bugs** |
| Examples | `IOException`, `SQLException` | `NPE`, `ArrayIndexOutOfBounds` |
| In method sig | Must declare with `throws` | No declaration needed |

---

## try-catch-finally

```java
try {
    // Code that might throw
    int result = 10 / 0;

} catch (ArithmeticException e) {
    // Handle specific exception FIRST
    System.out.println("Math error: " + e.getMessage());

} catch (Exception e) {
    // Broader catch LAST
    e.printStackTrace();

} finally {
    // ALWAYS runs — even if exception thrown, or return called
    // Use for: close connections, release resources, cleanup
    System.out.println("Cleanup happens here");
}

// Multi-catch (Java 7+) — for unrelated exceptions with same handler
try {
    riskyMethod();
} catch (IOException | SQLException e) {
    log.error("Data error", e);
}
```

> ⚠️ **finally always runs** — even if `return` is called in try or catch!  
> Exception: `System.exit()` or JVM crash.

---

## try-with-resources

> Auto-closes any `AutoCloseable` resource. No need for explicit `finally`. **(Java 7+)**

```java
// OLD WAY — verbose, error-prone
FileReader fr = null;
try {
    fr = new FileReader("file.txt");
    // use fr
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (fr != null) {
        try { fr.close(); } catch (IOException e) { }
    }
}

// ✅ NEW WAY — try-with-resources (auto-close)
try (FileReader fr = new FileReader("file.txt");
     BufferedReader br = new BufferedReader(fr)) {

    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }

} catch (IOException e) {
    e.printStackTrace();
}
// fr and br closed automatically in reverse order

// Works with any AutoCloseable — including custom classes
class DBConnection implements AutoCloseable {
    public void close() { System.out.println("Connection closed"); }
}
try (DBConnection conn = new DBConnection()) {
    conn.query("SELECT ...");
} // auto-closed!
```

---

## Custom Exceptions

```java
// 1. Checked custom exception — extends Exception
public class InsufficientFundsException extends Exception {
    private final double amount;

    public InsufficientFundsException(double amount) {
        super("Insufficient funds. Need: $" + amount);
        this.amount = amount;
    }

    public double getAmount() { return amount; }
}

// 2. Unchecked custom exception — extends RuntimeException
public class ValidationException extends RuntimeException {
    private final String field;

    public ValidationException(String field, String message) {
        super("Validation failed for '" + field + "': " + message);
        this.field = field;
    }

    public String getField() { return field; }
}

// Usage
public void withdraw(double amount) throws InsufficientFundsException {
    if (amount > balance) throw new InsufficientFundsException(amount);
    balance -= amount;
}

public void validateAge(int age) {
    if (age < 0 || age > 150)
        throw new ValidationException("age", age + " is not a valid age");
}
```

---

## Exception Chaining

```java
// Preserve the original cause when wrapping exceptions
public User loadUser(Long id) {
    try {
        return userDao.findById(id);         // throws SQLException
    } catch (SQLException dbEx) {
        // ✅ Chain original exception — preserve stack trace
        throw new ServiceException("Failed to load user id=" + id, dbEx);
    }
}

// Access the chain
try {
    loadUser(42L);
} catch (ServiceException e) {
    System.out.println("Service error: " + e.getMessage());
    System.out.println("Root cause:    " + e.getCause().getMessage());
    e.printStackTrace(); // prints full chain
}
```

---

## Best Practices

```java
// ✅ DO: Catch specific exceptions
} catch (FileNotFoundException e) { ... }

// ❌ DON'T: Catch Exception or Throwable (too broad — hides bugs)
} catch (Exception e) { ... }

// ✅ DO: Always include the exception in log
} catch (IOException e) {
    logger.error("Failed to read file: {}", filename, e);  // ← e at end!
}

// ❌ DON'T: Log only message — you lose stack trace!
} catch (IOException e) {
    logger.error("Failed: " + e.getMessage()); // ← missing stack trace!
}

// ❌ DON'T: Swallow exceptions silently
} catch (Exception e) { }  // ← worst practice ever!

// ✅ DO: Use try-with-resources for AutoCloseable
try (Connection conn = getConnection()) { ... }

// ❌ DON'T: Use exceptions for flow control
try {
    return list.get(index); // don't use exceptions as if-else!
} catch (IndexOutOfBoundsException e) { return null; }
// DO: if (index < list.size()) return list.get(index);
```

---

## ❓ Interview Q&A

**Q: What is the difference between `throw` and `throws`?**
> - `throw` — actually **throws** an exception (in method body): `throw new IllegalArgumentException("bad")`
> - `throws` — **declares** that method might throw (in signature): `public void read() throws IOException`

**Q: Can we have `try` without `catch`?**
> Yes — `try { } finally { }` is valid. Good for cleanup without needing to catch.

**Q: What happens if exception is thrown inside `finally`?**
> The `finally` exception **overrides** the original exception — original is lost! This is a common bug. Always protect finally blocks.

**Q: What is `Error` vs `Exception`?**
> - `Error` — JVM-level failures: `OutOfMemoryError`, `StackOverflowError`. **Don't catch these** — your app can't recover.
> - `Exception` — application-level issues. You should handle these.

**Q: Can we catch multiple exceptions in one catch block?**
> Yes — multi-catch (Java 7+): `catch (IOException | SQLException e)`. The variable is implicitly `final`.

**Q: What is exception chaining and why is it important?**
> Wrapping a low-level exception in a higher-level one while preserving the original via the `cause` parameter. Critical for debugging — you see the full root cause in stack traces.

---

*← [Module 03 — Collections](Module-03-Collections) | [Module 05 — Multithreading →](Module-05-Multithreading)*
