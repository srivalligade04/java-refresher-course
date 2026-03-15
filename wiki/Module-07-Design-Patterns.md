# 📘 Module 07 — Design Patterns

> **Package:** `com.futureframeservices.javaRefresherCourse.patterns`  
> **File:** `Module07_DesignPatterns.java`  
> **Time:** ~20 minutes

---

## 📋 Topics Covered

- [Pattern Categories](#pattern-categories)
- [Singleton](#1-singleton--creational)
- [Factory](#2-factory--creational)
- [Builder](#3-builder--creational)
- [Observer](#4-observer--behavioral)
- [Strategy](#5-strategy--behavioral)
- [Quick Reference Table](#quick-reference)
- [Interview Q&A](#-interview-qa)

---

## Pattern Categories

```
CREATIONAL (object creation)        BEHAVIORAL (communication)
  ├─ Singleton                        ├─ Observer
  ├─ Factory / Abstract Factory       ├─ Strategy
  ├─ Builder                          ├─ Command
  └─ Prototype                        └─ Iterator

STRUCTURAL (composing objects)
  ├─ Decorator
  ├─ Proxy
  ├─ Adapter
  └─ Facade
```

---

## 1. Singleton — Creational

> **Intent:** Ensure a class has only **one instance**, and provide global access to it.

### Thread-Safe Implementation (Bill Pugh — Best Approach)

```java
public class AppConfig {
    // Inner class is loaded only when getInstance() is called
    private static class Holder {
        static final AppConfig INSTANCE = new AppConfig();
    }

    private AppConfig() {}  // private constructor

    public static AppConfig getInstance() {
        return Holder.INSTANCE;  // lazy, thread-safe, no synchronization overhead
    }

    // Your config methods
    public String getDbUrl() { return "jdbc:mysql://localhost/futureframe"; }
}

// Usage
AppConfig cfg = AppConfig.getInstance();
AppConfig cfg2 = AppConfig.getInstance();
cfg == cfg2;  // true — same instance!
```

### Enum Singleton (Simplest, Serialization-Safe)

```java
public enum DatabasePool {
    INSTANCE;
    private int maxConnections = 10;
    public int getMaxConnections() { return maxConnections; }
}

// Usage
DatabasePool.INSTANCE.getMaxConnections();
```

### When to Use
- Database connection pool
- App configuration / properties
- Logger
- Thread pools
- Cache manager

---

## 2. Factory — Creational

> **Intent:** Define an interface for creating objects but let subclasses or a factory method decide which class to instantiate. **Decouples** object creation from usage.

```java
// Step 1: Define the product interface
interface NotificationService {
    void send(String recipient, String message);
}

// Step 2: Concrete implementations
class EmailNotification implements NotificationService {
    public void send(String r, String m) {
        System.out.println("[EMAIL] To: " + r + " | " + m);
    }
}
class SMSNotification implements NotificationService {
    public void send(String r, String m) {
        System.out.println("[SMS] To: " + r + " | " + m);
    }
}
class SlackNotification implements NotificationService {
    public void send(String r, String m) {
        System.out.println("[SLACK] To: " + r + " | " + m);
    }
}

// Step 3: Factory class
class NotificationFactory {
    public static NotificationService create(String type) {
        return switch (type.toUpperCase()) {
            case "EMAIL" -> new EmailNotification();
            case "SMS"   -> new SMSNotification();
            case "SLACK" -> new SlackNotification();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}

// Usage — caller doesn't know which class is instantiated!
String channel = getChannelFromConfig(); // "EMAIL"
NotificationService svc = NotificationFactory.create(channel);
svc.send("srivalli@futureframe.com", "Build passed ✅");
```

---

## 3. Builder — Creational

> **Intent:** Construct complex objects step by step. Separate construction from representation.  
> **Use when:** Object has many optional parameters (avoids telescoping constructors).

```java
public class JobPosting {
    // Required
    private final String title;
    private final String company;
    // Optional
    private final String location;
    private final double minSalary;
    private final double maxSalary;
    private final boolean remote;
    private final int minExperience;
    private final List<String> skills;

    private JobPosting(Builder b) {
        this.title         = b.title;
        this.company       = b.company;
        this.location      = b.location;
        this.minSalary     = b.minSalary;
        this.maxSalary     = b.maxSalary;
        this.remote        = b.remote;
        this.minExperience = b.minExperience;
        this.skills        = b.skills;
    }

    // Static nested Builder
    public static class Builder {
        private final String title, company;         // required
        private String location    = "Remote";        // defaults
        private double minSalary   = 0;
        private double maxSalary   = 0;
        private boolean remote     = false;
        private int minExperience  = 0;
        private List<String> skills = new ArrayList<>();

        public Builder(String title, String company) {
            this.title = title;
            this.company = company;
        }

        // Fluent setters — return this for chaining
        public Builder location(String l)            { this.location = l;      return this; }
        public Builder salary(double min, double max){ minSalary=min; maxSalary=max; return this; }
        public Builder remote(boolean r)             { this.remote = r;         return this; }
        public Builder experience(int years)         { this.minExperience=years; return this; }
        public Builder skills(String... s)           { skills = Arrays.asList(s); return this; }
        public JobPosting build()                    { return new JobPosting(this); }
    }
}

// Usage — clean, readable, no confusion about which param is which
JobPosting job = new JobPosting.Builder("Senior Java Engineer", "FutureFrame Services")
    .location("Hyderabad, India")
    .salary(90_000, 130_000)
    .remote(true)
    .experience(5)
    .skills("Java", "Spring Boot", "AWS", "Docker", "Kubernetes")
    .build();
```

> 💡 Lombok's `@Builder` annotation generates all of this automatically!

---

## 4. Observer — Behavioral

> **Intent:** When one object changes state, all its dependents are **notified automatically**.  
> Also known as: Publish-Subscribe, Event-Listener.

```java
// Step 1: Observer interface
interface JobAlertObserver {
    void onNewJob(String jobTitle, String company);
}

// Step 2: Subject (Observable)
class JobBoard {
    private final List<JobAlertObserver> subscribers = new ArrayList<>();

    public void subscribe(JobAlertObserver o)   { subscribers.add(o); }
    public void unsubscribe(JobAlertObserver o) { subscribers.remove(o); }

    public void postJob(String title, String company) {
        System.out.println("📋 New posting: " + title + " @ " + company);
        // Notify ALL subscribers
        subscribers.forEach(o -> o.onNewJob(title, company));
    }
}

// Step 3: Concrete observers
class EmailSubscriber implements JobAlertObserver {
    String email;
    EmailSubscriber(String email) { this.email = email; }
    public void onNewJob(String t, String c) {
        System.out.println("  📧 Email → " + email + ": " + t + " at " + c);
    }
}

// Lambda observer (Java 8+)
JobAlertObserver slackBot = (t, c) ->
    System.out.println("  💬 #jobs: " + t + " @ " + c);

// Usage
JobBoard board = new JobBoard();
board.subscribe(new EmailSubscriber("srivalli@futureframe.com"));
board.subscribe(slackBot);
board.postJob("Java Tech Lead", "FutureFrame Services");
```

### Real-World Examples
- Spring `ApplicationEventPublisher` / `@EventListener`
- Java `PropertyChangeListener`
- Android button click listeners
- Reactive Streams (RxJava, Project Reactor)

---

## 5. Strategy — Behavioral

> **Intent:** Define a family of algorithms, encapsulate each one, and make them **interchangeable at runtime**.

```java
// Step 1: Strategy interface
@FunctionalInterface
interface PaymentStrategy {
    boolean pay(double amount);
}

// Step 2: Concrete strategies
class CreditCardPayment implements PaymentStrategy {
    String cardNumber;
    CreditCardPayment(String cn) { this.cardNumber = cn; }
    public boolean pay(double amount) {
        System.out.printf("  💳 Credit Card %s: $%.2f charged%n", cardNumber, amount);
        return true;
    }
}

class UPIPayment implements PaymentStrategy {
    String upiId;
    UPIPayment(String id) { this.upiId = id; }
    public boolean pay(double amount) {
        System.out.printf("  📱 UPI %s: $%.2f transferred%n", upiId, amount);
        return true;
    }
}

// Step 3: Context
class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void checkout(double amount) {
        if (paymentStrategy == null) throw new IllegalStateException("No payment method set");
        boolean success = paymentStrategy.pay(amount);
        System.out.println("  Payment " + (success ? "✅ successful" : "❌ failed"));
    }
}

// Usage — swap strategies at runtime
ShoppingCart cart = new ShoppingCart();

// User selects credit card
cart.setPaymentStrategy(new CreditCardPayment("****1234"));
cart.checkout(499.99);

// User switches to UPI
cart.setPaymentStrategy(new UPIPayment("srivalli@futureframe"));
cart.checkout(199.00);

// Lambda strategy (since PaymentStrategy is @FunctionalInterface)
cart.setPaymentStrategy(amount -> {
    System.out.println("  🏦 Net banking: $" + amount);
    return true;
});
cart.checkout(999.00);
```

---

## Quick Reference

| Pattern | Type | Problem Solved | Key Idea |
|---------|------|----------------|----------|
| **Singleton** | Creational | One instance everywhere | Private constructor + static method |
| **Factory** | Creational | Decouple creation from use | Return interface, hide concrete type |
| **Builder** | Creational | Complex object construction | Fluent API, optional params |
| **Observer** | Behavioral | Notify on state change | Subscribe/publish |
| **Strategy** | Behavioral | Swap algorithms at runtime | Inject behavior |
| **Decorator** | Structural | Add behavior without subclassing | Wrap and delegate |
| **Proxy** | Structural | Control access to object | Same interface, add logic |
| **Adapter** | Structural | Make incompatible interfaces work | Translate between interfaces |

---

## ❓ Interview Q&A

**Q: What is the difference between Factory and Abstract Factory?**
> **Factory** — creates one type of product via one method.  
> **Abstract Factory** — creates a *family* of related products (e.g., `createButton()` + `createCheckbox()` for Windows vs Mac).

**Q: What is the difference between Builder and Constructor?**
> **Builder** — for objects with many optional parameters, avoiding telescoping constructors. Provides a fluent, readable API. Immutable result.  
> **Constructor** — all parameters passed at once. Gets messy with many optional fields.

**Q: Where is Singleton used in Java/Spring?**
> - Spring beans are **Singleton by default** (`@Scope("singleton")`)
> - Java's `Runtime.getRuntime()`
> - `Collections.emptyList()`
> - Logger factories

**Q: Where is Observer used in Java/Spring?**
> - Spring `@EventListener` / `ApplicationEventPublisher`
> - `java.util.Observable` (legacy)
> - Java Swing event listeners
> - RxJava / Reactor reactive streams

**Q: Where is Strategy used in real projects?**
> - Spring Security `AuthenticationStrategy`
> - `java.util.Comparator` — different sort strategies
> - Payment processing (card, UPI, wallet)
> - Compression algorithms (ZIP, GZIP, BZIP)
> - `Collections.sort(list, comparator)` — comparator is the strategy!

---

*← [Module 06 — Java 8 Features](Module-06-Java8-Features) | [Interview Q&A Master →](Interview-QnA-Master)*
