package com.futureframeservices.javaRefresherCourse.oop;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * com.futureframeservices.javaRefresherCourse.oop
 * Module 02 — Object-Oriented Programming
 * Topics: Encapsulation, Inheritance, Polymorphism, Abstraction
 * ============================================================
 */
public class Module02_OOP {

    public static void run() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║  MODULE 02 — OOP CONCEPTS                ║");
        System.out.println("╚══════════════════════════════════════════╝");

        demo01_Encapsulation();
        demo02_InheritanceAndPolymorphism();
        demo03_AbstractAndInterface();
        demo04_ConstructorChaining();
    }

    // ── 1. Encapsulation ─────────────────────────────────────
    static void demo01_Encapsulation() {
        System.out.println("\n── 1. Encapsulation ──");

        BankAccount acc = new BankAccount("FutureFrame Services", 10_000.0);
        System.out.println("Created: " + acc);
        acc.deposit(5_000);
        System.out.println("After deposit $5000: " + acc);
        try {
            acc.withdraw(20_000); // should fail
        } catch (IllegalStateException e) {
            System.out.println("⚠️  " + e.getMessage());
        }
        acc.withdraw(3_000);
        System.out.println("After withdraw $3000: " + acc);
    }

    // ── 2. Inheritance & Runtime Polymorphism ────────────────
    static void demo02_InheritanceAndPolymorphism() {
        System.out.println("\n── 2. Inheritance & Polymorphism ──");

        // Parent reference — child object: RUNTIME polymorphism
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle("Red", 7));
        shapes.add(new Rectangle("Blue", 4, 6));
        shapes.add(new Triangle("Green", 3, 4, 5, 6));

        for (Shape s : shapes) {
            s.describe(); // calls correct subclass method at runtime
        }

        // instanceof + casting
        for (Shape s : shapes) {
            if (s instanceof Circle c) { // Java 16 pattern matching
                System.out.println("  → Circle radius: " + c.radius);
            }
        }
    }

    // ── 3. Abstract Class & Interface ────────────────────────
    static void demo03_AbstractAndInterface() {
        System.out.println("\n── 3. Abstract Class & Interface ──");

        // Interface with default method
        Printable card = new BusinessCard("Srivalli", "FutureFrame Services");
        card.print();
        card.printWithBorder(); // default method

        // Multiple interface implementation
        SmartDevice phone = new SmartDevice("iPhone 15");
        phone.turnOn();
        phone.connect("WiFi-5G");
        phone.takePicture();
        System.out.println("Device: " + phone.getInfo());
    }

    // ── 4. Constructor Chaining ──────────────────────────────
    static void demo04_ConstructorChaining() {
        System.out.println("\n── 4. Constructor Chaining ──");

        Employee e1 = new Employee("Alice");
        Employee e2 = new Employee("Bob", "Engineering");
        Employee e3 = new Employee("Charlie", "Engineering", 95_000);

        System.out.println(e1);
        System.out.println(e2);
        System.out.println(e3);
    }

    // ════════════════════════════════════════════════
    //  INNER CLASSES USED IN DEMOS
    // ════════════════════════════════════════════════

    // Encapsulation
    static class BankAccount {
        private final String owner;
        private double balance;

        BankAccount(String owner, double balance) {
            this.owner = owner;
            this.balance = balance;
        }

        void deposit(double amount) {
            if (amount <= 0) throw new IllegalArgumentException("Positive amount only");
            balance += amount;
        }

        void withdraw(double amount) {
            if (amount > balance) throw new IllegalStateException(
                "Insufficient funds: balance $" + balance + ", requested $" + amount);
            balance -= amount;
        }

        @Override public String toString() {
            return String.format("BankAccount[owner=%s, balance=$%.2f]", owner, balance);
        }
    }

    // Inheritance / Polymorphism
    static abstract class Shape {
        String color;
        Shape(String color) { this.color = color; }
        abstract double area();
        abstract double perimeter();
        void describe() {
            System.out.printf("  %-12s color=%-6s area=%7.2f  perimeter=%7.2f%n",
                getClass().getSimpleName(), color, area(), perimeter());
        }
    }

    static class Circle extends Shape {
        double radius;
        Circle(String color, double radius) { super(color); this.radius = radius; }
        @Override public double area()      { return Math.PI * radius * radius; }
        @Override public double perimeter() { return 2 * Math.PI * radius; }
    }

    static class Rectangle extends Shape {
        double w, h;
        Rectangle(String c, double w, double h) { super(c); this.w=w; this.h=h; }
        @Override public double area()      { return w * h; }
        @Override public double perimeter() { return 2*(w+h); }
    }

    static class Triangle extends Shape {
        double a, b, c, height;
        Triangle(String col, double a, double b, double c, double height) {
            super(col); this.a=a; this.b=b; this.c=c; this.height=height;
        }
        @Override public double area()      { return 0.5 * a * height; }
        @Override public double perimeter() { return a + b + c; }
    }

    // Interface
    interface Printable {
        void print();
        default void printWithBorder() {
            System.out.println("  ┌─────────────────────────┐");
            print();
            System.out.println("  └─────────────────────────┘");
        }
    }

    static class BusinessCard implements Printable {
        String name, company;
        BusinessCard(String n, String c) { name=n; company=c; }
        @Override public void print() {
            System.out.println("  │ " + name + " @ " + company + " │");
        }
    }

    interface PowerControl { void turnOn(); void turnOff(); }
    interface NetworkCapable { void connect(String network); }
    interface Camera { void takePicture(); }

    static class SmartDevice implements PowerControl, NetworkCapable, Camera {
        String model;
        SmartDevice(String model) { this.model = model; }
        @Override public void turnOn()             { System.out.println("  " + model + " powered ON"); }
        @Override public void turnOff()            { System.out.println("  " + model + " powered OFF"); }
        @Override public void connect(String net)  { System.out.println("  Connected to: " + net); }
        @Override public void takePicture()        { System.out.println("  📸 Picture taken!"); }
        public    String getInfo()                 { return "SmartDevice[" + model + "]"; }
    }

    // Constructor chaining
    static class Employee {
        String name, department;
        double salary;

        Employee(String name)                            { this(name, "General"); }
        Employee(String name, String dept)               { this(name, dept, 50_000); }
        Employee(String name, String dept, double sal)   {
            this.name=name; this.department=dept; this.salary=sal;
        }

        @Override public String toString() {
            return String.format("Employee[%-10s dept=%-15s salary=$%.0f]", name, department, salary);
        }
    }
}
