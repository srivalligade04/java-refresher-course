# 📘 Module 02 — Object-Oriented Programming

> **Package:** `com.futureframeservices.javaRefresherCourse.oop`  
> **File:** `Module02_OOP.java`  
> **Time:** ~45 minutes

---

## 📋 Topics Covered

- [The 4 Pillars of OOP](#1-the-4-pillars-of-oop)
- [Encapsulation](#-encapsulation)
- [Inheritance](#-inheritance)
- [Polymorphism](#-polymorphism)
- [Abstraction](#-abstraction)
- [Abstract Class vs Interface](#2-abstract-class-vs-interface)
- [final, static, this, super](#3-keywords-final-static-this-super)
- [Constructor Chaining](#4-constructor-chaining)
- [Interview Q&A](#-interview-qa)

---

## 1. The 4 Pillars of OOP

```
┌─────────────────────────────────────────────┐
│  ENCAPSULATION   │  Hide internal state      │
│  INHERITANCE     │  Reuse parent code        │
│  POLYMORPHISM    │  One interface, many forms│
│  ABSTRACTION     │  Hide how, show what      │
└─────────────────────────────────────────────┘
```

---

### 🔒 Encapsulation

> Bundle **data + behavior** inside a class. Expose only what's needed via access modifiers.

```java
public class BankAccount {
    private double balance;     // ← HIDDEN (private)
    private String owner;

    // CONTROLLED access
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Must be positive");
        balance += amount;      // validation before modification
    }
}
```

**Access Modifiers:**

| Modifier | Same Class | Same Package | Subclass | Everywhere |
|----------|-----------|--------------|----------|------------|
| `private` | ✅ | ❌ | ❌ | ❌ |
| *(default)* | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

---

### 🧬 Inheritance

> Child class **reuses** fields and methods from parent using `extends`.

```java
class Animal {
    String name;
    void eat() { System.out.println(name + " eats"); }
}

class Dog extends Animal {
    void bark() { System.out.println(name + " barks!"); }
}

// Usage
Dog dog = new Dog();
dog.name = "Rex";
dog.eat();   // inherited from Animal
dog.bark();  // Dog's own method
```

**Key inheritance rules:**
- Java supports **single inheritance** for classes (one `extends`)
- All classes implicitly extend `Object`
- `super()` — call parent constructor, must be **first line**
- `super.method()` — call parent method from override

---

### 🎭 Polymorphism

**Compile-time (Method Overloading)** — same name, different parameters, same class:

```java
class Calculator {
    int add(int a, int b)           { return a + b; }
    double add(double a, double b)  { return a + b; }
    int add(int a, int b, int c)    { return a + b + c; }
    // Different: param count, param types, or both
}
```

**Runtime (Method Overriding)** — child redefines parent method:

```java
class Shape {
    double area() { return 0; }
}
class Circle extends Shape {
    double radius;
    @Override
    double area() { return Math.PI * radius * radius; }
}

// Runtime polymorphism — parent ref, child object
Shape s = new Circle();
s.area();   // calls Circle.area() at RUNTIME → dynamic dispatch
```

> 💡 **Rule of thumb:** Overloading = compile-time (which method to call). Overriding = runtime (which class's version to use).

---

### 🫥 Abstraction

```java
// Abstract class — partial abstraction
abstract class Vehicle {
    String brand;

    abstract void start();      // must be implemented by subclass
    abstract void stop();

    void fuelUp() {             // concrete — shared by all vehicles
        System.out.println(brand + " fueling up...");
    }
}

// Interface — full abstraction + capability contract
interface Electric {
    void charge();

    default int getBatteryPercent() {   // Java 8: default method
        return 100; // can be overridden
    }

    static String getType() {           // Java 8: static method
        return "Electric Vehicle";
    }
}

class TeslaModel3 extends Vehicle implements Electric {
    TeslaModel3() { this.brand = "Tesla"; }

    @Override public void start() { System.out.println("Silent EV start"); }
    @Override public void stop()  { System.out.println("Regenerative braking"); }
    @Override public void charge(){ System.out.println("Charging at Supercharger"); }
}
```

---

## 2. Abstract Class vs Interface

| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Keyword | `extends` | `implements` |
| Multiple inheritance | ❌ One only | ✅ Many interfaces |
| Constructor | ✅ Yes | ❌ No |
| Instance variables | ✅ Yes | ❌ No (only `public static final`) |
| Method types | abstract + concrete | abstract, default, static |
| Use for | IS-A + shared code | CAN-DO capability |

**When to use what:**
```
Abstract class → "Dog IS-A Animal" (shared fields + methods)
Interface      → "Bird CAN-DO Flyable" (capability, no shared state)
```

---

## 3. Keywords: `final`, `static`, `this`, `super`

```java
// final — 3 uses
final int MAX = 100;         // constant (must initialize)
final class MathUtils {}     // can't be extended
final void compute() {}      // can't be overridden

// static — belongs to CLASS, not instance
class Counter {
    static int count = 0;            // shared across all instances
    static void resetAll() { count = 0; }
    Counter() { count++; }
}
Counter.count;      // access without creating object
Counter.resetAll(); // call without object

// this — refers to current instance
class Point {
    int x, y;
    Point(int x, int y) {
        this.x = x;   // this.x = field, x = parameter
        this.y = y;
    }
    Point() { this(0, 0); }  // calls Point(int,int) — constructor chaining
}

// super — refers to parent
class ColorPoint extends Point {
    String color;
    ColorPoint(int x, int y, String color) {
        super(x, y);        // must be first line! calls parent constructor
        this.color = color;
    }
    @Override public String toString() {
        return super.toString() + " color=" + color; // call parent method
    }
}
```

---

## 4. Constructor Chaining

```java
class Employee {
    String name, department;
    double salary;

    // Chained constructors — each calls the next
    Employee(String name) {
        this(name, "General");          // → calls below
    }
    Employee(String name, String dept) {
        this(name, dept, 50_000);       // → calls below
    }
    Employee(String name, String dept, double salary) {
        this.name = name;               // ← only one place that actually sets fields
        this.department = dept;
        this.salary = salary;
    }
}

// Usage
Employee e1 = new Employee("Alice");                          // name=Alice, dept=General, sal=50000
Employee e2 = new Employee("Bob", "Engineering");             // name=Bob, dept=Engineering, sal=50000
Employee e3 = new Employee("Charlie", "Engineering", 95000); // all specified
```

---

## ❓ Interview Q&A

**Q: What is the difference between method overloading and overriding?**

| | Overloading | Overriding |
|--|--|--|
| Binding | Compile-time | Runtime |
| Location | Same class | Parent-Child |
| Signature | Must differ | Must be same |
| Return type | Can differ | Same (or covariant) |
| Access | Any | Can't be more restrictive |

**Q: Can we override a `static` method?**
> No — static methods are **hidden**, not overridden. The call is resolved by the **reference type**, not the object type.

**Q: Can we override a `private` method?**
> No — private methods are not visible to subclasses. You can define a method with the same name, but it's a completely new method (no polymorphism).

**Q: What is the diamond problem? How does Java solve it?**
> When two interfaces have a default method with the same name. Java forces the implementing class to **override** the conflicting method.

```java
interface A { default void greet() { System.out.println("A"); } }
interface B { default void greet() { System.out.println("B"); } }
class C implements A, B {
    @Override public void greet() { A.super.greet(); } // must resolve!
}
```

**Q: What is covariant return type?**
> An overriding method can return a **subtype** of the parent's return type.
```java
class Animal { Animal create() { return new Animal(); } }
class Dog extends Animal {
    @Override Dog create() { return new Dog(); } // Dog is subtype of Animal ✅
}
```

**Q: Can abstract class have a constructor?**
> Yes! Though you can't instantiate it directly, the constructor is called via `super()` from the subclass.

---

*← [Module 01 — Java Basics](Module-01-Java-Basics) | [Module 03 — Collections →](Module-03-Collections)*
