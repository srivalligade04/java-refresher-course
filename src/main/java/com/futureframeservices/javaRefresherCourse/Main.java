package com.futureframeservices.javaRefresherCourse;

import com.futureframeservices.javaRefresherCourse.basics.Module01_JavaBasics;
import com.futureframeservices.javaRefresherCourse.oop.Module02_OOP;
import com.futureframeservices.javaRefresherCourse.collections.Module03_Collections;
import com.futureframeservices.javaRefresherCourse.exceptions.Module04_Exceptions;
import com.futureframeservices.javaRefresherCourse.multithreading.Module05_Multithreading;
import com.futureframeservices.javaRefresherCourse.java8.Module06_Java8Features;
import com.futureframeservices.javaRefresherCourse.patterns.Module07_DesignPatterns;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║     com.futureframeservices.javaRefresherCourse             ║
 * ║     Java Refresher Course — FutureFrame Services            ║
 * ║     Author  : Srivalli                                      ║
 * ║     Version : 1.0.0                                         ║
 * ╚══════════════════════════════════════════════════════════════╝
 *
 * MODULES:
 *   01 — Java Basics        (Primitives, Strings, Arrays)
 *   02 — OOP                (Encapsulation, Inheritance, Polymorphism)
 *   03 — Collections        (List, Set, Map, Queue)
 *   04 — Exceptions & I/O   (try-catch, Custom Exceptions)
 *   05 — Multithreading     (Threads, Executors, AtomicInteger)
 *   06 — Java 8+ Features   (Lambdas, Streams, Optional)
 *   07 — Design Patterns    (Singleton, Factory, Builder, Observer, Strategy)
 */
public class Main {

    public static void main(String[] args) {

        printBanner();

        long start = System.currentTimeMillis();

        // Run each module in sequence
        Module01_JavaBasics.run();
        Module02_OOP.run();
        Module03_Collections.run();
        Module04_Exceptions.run();
        Module05_Multithreading.run();
        Module06_Java8Features.run();
        Module07_DesignPatterns.run();

        long elapsed = System.currentTimeMillis() - start;

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║  ✅ ALL MODULES COMPLETED                 ║");
        System.out.printf( "║  ⏱  Total time: %3d ms                   ║%n", elapsed);
        System.out.println("║  🎯 You're interview-ready!               ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    static void printBanner() {
        System.out.println();
        System.out.println("  ███████╗██╗   ██╗████████╗██╗   ██╗██████╗ ███████╗");
        System.out.println("  ██╔════╝██║   ██║╚══██╔══╝██║   ██║██╔══██╗██╔════╝");
        System.out.println("  █████╗  ██║   ██║   ██║   ██║   ██║██████╔╝█████╗  ");
        System.out.println("  ██╔══╝  ██║   ██║   ██║   ██║   ██║██╔══██╗██╔══╝  ");
        System.out.println("  ██║     ╚██████╔╝   ██║   ╚██████╔╝██║  ██║███████╗");
        System.out.println("  ╚═╝      ╚═════╝    ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝");
        System.out.println();
        System.out.println("  ┌─────────────────────────────────────────────────────┐");
        System.out.println("  │  Java Refresher Course — FutureFrame Services       │");
        System.out.println("  │  Package: com.futureframeservices.javaRefresherCourse│");
        System.out.println("  │  Author : Srivalli  |  Version: 1.0.0              │");
        System.out.println("  │  Java 17 · 7 Modules · Interview-Ready              │");
        System.out.println("  └─────────────────────────────────────────────────────┘");
    }
}
