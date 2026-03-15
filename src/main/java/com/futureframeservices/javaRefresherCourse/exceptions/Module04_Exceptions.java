package com.futureframeservices.javaRefresherCourse.exceptions;

import java.io.*;
import java.nio.file.*;

/**
 * ============================================================
 * com.futureframeservices.javaRefresherCourse.exceptions
 * Module 04 — Exceptions & I/O
 * Topics: Checked/Unchecked, try-catch-finally,
 *         try-with-resources, Custom Exceptions
 * ============================================================
 */
public class Module04_Exceptions {

    public static void run() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║  MODULE 04 — EXCEPTIONS & I/O            ║");
        System.out.println("╚══════════════════════════════════════════╝");

        demo01_BasicTryCatch();
        demo02_MultiCatchAndFinally();
        demo03_CustomExceptions();
        demo04_TryWithResources();
        demo05_ExceptionChaining();
    }

    // ── 1. Basic try-catch ───────────────────────────────────
    static void demo01_BasicTryCatch() {
        System.out.println("\n── 1. Basic try-catch ──");

        // ArithmeticException
        try {
            int result = 100 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        }

        // ArrayIndexOutOfBoundsException
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: index 10 out of bounds for length 3");
        }

        // NullPointerException
        try {
            String s = null;
            s.length();
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException: calling method on null reference");
        }

        // ClassCastException
        try {
            Object obj = "I am a String";
            Integer num = (Integer) obj; // wrong cast
        } catch (ClassCastException e) {
            System.out.println("Caught ClassCastException: " + e.getMessage());
        }

        // NumberFormatException
        try {
            int x = Integer.parseInt("not-a-number");
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        }
    }

    // ── 2. Multi-catch & finally ─────────────────────────────
    static void demo02_MultiCatchAndFinally() {
        System.out.println("\n── 2. Multi-catch & finally ──");

        System.out.println("Test A — successful flow:");
        String resultA = riskyMethod(10, "42");
        System.out.println("  Result: " + resultA);

        System.out.println("Test B — divide by zero:");
        String resultB = riskyMethod(0, "42");
        System.out.println("  Result: " + resultB);

        System.out.println("Test C — bad number format:");
        String resultC = riskyMethod(5, "abc");
        System.out.println("  Result: " + resultC);
    }

    static String riskyMethod(int divisor, String numStr) {
        try {
            int value = Integer.parseInt(numStr);
            int result = value / divisor;
            return "Success: " + result;
        } catch (NumberFormatException | ArithmeticException e) {
            // multi-catch — Java 7+
            System.out.println("  ⚠️  Caught [" + e.getClass().getSimpleName() + "]: " + e.getMessage());
            return "Error: " + e.getClass().getSimpleName();
        } finally {
            System.out.println("  → finally always runs (cleanup goes here)");
        }
    }

    // ── 3. Custom Exceptions ─────────────────────────────────
    static void demo03_CustomExceptions() {
        System.out.println("\n── 3. Custom Exceptions ──");

        // Checked custom exception
        try {
            processPayment(-500);
        } catch (InvalidPaymentException e) {
            System.out.println("Caught InvalidPaymentException: " + e.getMessage());
            System.out.println("  Invalid amount was: " + e.getAmount());
        }

        // Unchecked custom exception
        try {
            registerUser("", "test@example.com");
        } catch (ValidationException e) {
            System.out.println("Caught ValidationException: " + e.getMessage());
        }

        // Valid call
        try {
            registerUser("Srivalli", "srivalli@futureframe.com");
            System.out.println("✅ User registered successfully");
        } catch (ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void processPayment(double amount) throws InvalidPaymentException {
        if (amount <= 0) throw new InvalidPaymentException(amount);
        System.out.println("Payment processed: $" + amount);
    }

    static void registerUser(String name, String email) {
        if (name == null || name.isBlank())  throw new ValidationException("Name cannot be blank");
        if (!email.contains("@"))            throw new ValidationException("Invalid email: " + email);
        System.out.println("Registering: " + name + " (" + email + ")");
    }

    // ── 4. try-with-resources ────────────────────────────────
    static void demo04_TryWithResources() {
        System.out.println("\n── 4. try-with-resources ──");

        // Write a temp file then read it back
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("futureframe-", ".txt");
            Files.write(tempFile, "FutureFrame Services\nJava Refresher Course\nLine 3".getBytes());

            // Auto-close BufferedReader
            try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
                System.out.println("File contents:");
                String line;
                int lineNum = 1;
                while ((line = reader.readLine()) != null) {
                    System.out.println("  " + lineNum++ + ": " + line);
                }
            }
            System.out.println("✅ BufferedReader auto-closed by try-with-resources");

        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        } finally {
            if (tempFile != null) {
                try { Files.deleteIfExists(tempFile); } catch (IOException ignored) {}
            }
        }
    }

    // ── 5. Exception Chaining ────────────────────────────────
    static void demo05_ExceptionChaining() {
        System.out.println("\n── 5. Exception Chaining ──");

        try {
            loadUserFromDatabase(99L);
        } catch (ServiceException e) {
            System.out.println("ServiceException: " + e.getMessage());
            System.out.println("Caused by:        " + e.getCause().getClass().getSimpleName()
                               + " — " + e.getCause().getMessage());
        }
    }

    static void loadUserFromDatabase(Long id) {
        try {
            simulateDbCall(id); // throws checked exception
        } catch (Exception dbEx) {
            // Wrap in a RuntimeException — preserve original cause!
            throw new ServiceException("Failed to load user with id=" + id, dbEx);
        }
    }

    static void simulateDbCall(Long id) throws Exception {
        throw new Exception("Connection timeout after 30s for query SELECT * FROM users WHERE id=" + id);
    }

    // ════════════════════════════════════════════════
    //  CUSTOM EXCEPTION CLASSES
    // ════════════════════════════════════════════════

    // Checked Exception (must be declared or caught)
    static class InvalidPaymentException extends Exception {
        private final double amount;
        InvalidPaymentException(double amount) {
            super("Invalid payment amount: " + amount + ". Must be > 0.");
            this.amount = amount;
        }
        double getAmount() { return amount; }
    }

    // Unchecked Exception (RuntimeException)
    static class ValidationException extends RuntimeException {
        ValidationException(String message) { super(message); }
    }

    // Service-layer wrapper exception
    static class ServiceException extends RuntimeException {
        ServiceException(String message, Throwable cause) { super(message, cause); }
    }
}
