package com.futureframeservices.javaRefresherCourse.basics;

import java.util.Arrays;

/**
 * ============================================================
 * com.futureframeservices.javaRefresherCourse.basics
 * Module 01 — Java Basics
 * Topics: Primitives, Wrappers, Strings, StringBuilder, Arrays
 * ============================================================
 */
public class Module01_JavaBasics {

    public static void run() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║  MODULE 01 — JAVA BASICS                 ║");
        System.out.println("╚══════════════════════════════════════════╝");

        demo01_PrimitivesAndWrappers();
        demo02_StringImmutability();
        demo03_StringBuilder();
        demo04_Arrays();
        demo05_TypeCasting();
    }

    // ── 1. Primitives & Wrappers ─────────────────────────────
    static void demo01_PrimitivesAndWrappers() {
        System.out.println("\n── 1. Primitives & Wrappers ──");

        // Integer cache: -128 to 127 cached → == works
        Integer a = 100, b = 100;
        System.out.println("Integer 100 == 100 (cached):  " + (a == b));     // true

        Integer c = 200, d = 200;
        System.out.println("Integer 200 == 200 (heap):    " + (c == d));     // false
        System.out.println("Integer 200 .equals() 200:    " + c.equals(d));  // true ✅

        // Autoboxing / Unboxing
        int primitive = 42;
        Integer boxed = primitive;           // autoboxing
        int unboxed   = boxed;               // unboxing
        System.out.println("Autoboxed → Unboxed: " + primitive + " → " + boxed + " → " + unboxed);

        // NPE risk with unboxing null
        Integer nullInt = null;
        try {
            int bad = nullInt; // NullPointerException!
        } catch (NullPointerException e) {
            System.out.println("⚠️  Unboxing null throws NullPointerException!");
        }

        // Primitive sizes
        System.out.println("Max int:    " + Integer.MAX_VALUE);
        System.out.println("Max long:   " + Long.MAX_VALUE);
        System.out.println("Max double: " + Double.MAX_VALUE);
    }

    // ── 2. String Immutability ───────────────────────────────
    static void demo02_StringImmutability() {
        System.out.println("\n── 2. String Immutability ──");

        String s1 = "Java";              // String pool
        String s2 = "Java";              // same pool reference
        String s3 = new String("Java");  // new heap object

        System.out.println("s1 == s2 (pool):      " + (s1 == s2));       // true
        System.out.println("s1 == s3 (heap):      " + (s1 == s3));       // false
        System.out.println("s1.equals(s3):        " + s1.equals(s3));    // true ✅

        // Useful String methods
        String str = "  Hello, FutureFrame Services!  ";
        System.out.println("trim():       '" + str.trim() + "'");
        System.out.println("length():     "  + str.trim().length());
        System.out.println("toUpperCase:" + str.trim().toUpperCase());
        System.out.println("contains():   " + str.contains("FutureFrame"));
        System.out.println("replace():    " + str.trim().replace("FutureFrame", "Future"));
        System.out.println("substring():  " + str.trim().substring(7, 18));
        System.out.println("indexOf():    " + str.trim().indexOf("Frame"));

        // Split
        String csv = "Java,Spring,AWS,Docker,Kubernetes";
        String[] skills = csv.split(",");
        System.out.println("Split skills: " + Arrays.toString(skills));

        // String.format
        String formatted = String.format("%-15s | %5d | %.2f", "Alice", 90, 4.5);
        System.out.println("Formatted: " + formatted);
    }

    // ── 3. StringBuilder ─────────────────────────────────────
    static void demo03_StringBuilder() {
        System.out.println("\n── 3. StringBuilder ──");

        StringBuilder sb = new StringBuilder();
        sb.append("FutureFrame");
        sb.append(" Services");
        sb.insert(11, ",");           // FutureFrame, Services
        sb.replace(0, 11, "Future"); // Future, Services
        System.out.println("After ops: " + sb);

        sb.reverse();
        System.out.println("Reversed:  " + sb);
        sb.reverse(); // restore
        sb.delete(7, sb.length());
        System.out.println("After delete: " + sb);

        // Efficient CSV builder
        String[] items = {"Java", "Spring Boot", "AWS", "Docker"};
        StringBuilder csv = new StringBuilder("[");
        for (int i = 0; i < items.length; i++) {
            csv.append(items[i]);
            if (i < items.length - 1) csv.append(", ");
        }
        csv.append("]");
        System.out.println("Skills CSV: " + csv);
    }

    // ── 4. Arrays ────────────────────────────────────────────
    static void demo04_Arrays() {
        System.out.println("\n── 4. Arrays ──");

        int[] arr = {64, 25, 12, 22, 11};
        System.out.println("Original:    " + Arrays.toString(arr));

        Arrays.sort(arr);
        System.out.println("Sorted:      " + Arrays.toString(arr));

        int idx = Arrays.binarySearch(arr, 22);
        System.out.println("Index of 22: " + idx);

        int[] copy = Arrays.copyOfRange(arr, 1, 4);
        System.out.println("Copy[1..4]:  " + Arrays.toString(copy));

        // 2D array
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.println("  " + Arrays.toString(row));
        }

        // Diagonal sum
        int diagSum = 0;
        for (int i = 0; i < matrix.length; i++) diagSum += matrix[i][i];
        System.out.println("Diagonal sum: " + diagSum); // 1+5+9 = 15
    }

    // ── 5. Type Casting ──────────────────────────────────────
    static void demo05_TypeCasting() {
        System.out.println("\n── 5. Type Casting ──");

        // Widening (implicit)
        int  i  = 100;
        long l  = i;        // int → long (no cast needed)
        double d = l;       // long → double
        System.out.println("Widening: int " + i + " → long " + l + " → double " + d);

        // Narrowing (explicit — may lose data)
        double pi   = 3.99999;
        int    piInt = (int) pi; // truncates to 3
        System.out.println("Narrowing: double " + pi + " → int " + piInt);

        // String ↔ Number conversions
        int    parsed  = Integer.parseInt("42");
        double parsedD = Double.parseDouble("3.14");
        String fromInt = String.valueOf(100);
        System.out.println("parseInt:    " + parsed);
        System.out.println("parseDouble: " + parsedD);
        System.out.println("valueOf:     " + fromInt);
    }
}
