package com.futureframeservices.javaRefresherCourse.patterns;

import java.util.*;

/**
 * ============================================================
 * com.futureframeservices.javaRefresherCourse.patterns
 * Module 07 — Design Patterns
 * Topics: Singleton, Factory, Builder, Observer, Strategy
 * ============================================================
 */
public class Module07_DesignPatterns {

    public static void run() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║  MODULE 07 — DESIGN PATTERNS             ║");
        System.out.println("╚══════════════════════════════════════════╝");

        demo01_Singleton();
        demo02_Factory();
        demo03_Builder();
        demo04_Observer();
        demo05_Strategy();
    }

    // ── 1. Singleton ─────────────────────────────────────────
    static void demo01_Singleton() {
        System.out.println("\n── 1. Singleton Pattern ──");

        AppConfig cfg1 = AppConfig.getInstance();
        AppConfig cfg2 = AppConfig.getInstance();
        System.out.println("  Same instance? " + (cfg1 == cfg2)); // true
        System.out.println("  App name:  " + cfg1.getAppName());
        System.out.println("  DB url:    " + cfg1.getDbUrl());
        cfg1.setAppName("FutureFrame-Prod");
        System.out.println("  Updated via cfg1, read via cfg2: " + cfg2.getAppName());
    }

    // ── 2. Factory ───────────────────────────────────────────
    static void demo02_Factory() {
        System.out.println("\n── 2. Factory Pattern ──");

        String[] channels = {"EMAIL", "SMS", "PUSH", "SLACK"};
        for (String ch : channels) {
            NotificationService svc = NotificationFactory.create(ch);
            svc.send("Srivalli", "Your Java build passed ✅");
        }

        // Invalid type
        try {
            NotificationFactory.create("FAX");
        } catch (IllegalArgumentException e) {
            System.out.println("  ⚠️  " + e.getMessage());
        }
    }

    // ── 3. Builder ───────────────────────────────────────────
    static void demo03_Builder() {
        System.out.println("\n── 3. Builder Pattern ──");

        // Minimal required fields
        JobPosting j1 = new JobPosting.Builder("Java Backend Engineer", "FutureFrame Services")
            .build();
        System.out.println("  " + j1);

        // Full spec
        JobPosting j2 = new JobPosting.Builder("Senior Full Stack Engineer", "FutureFrame Services")
            .location("Hyderabad, India (Hybrid)")
            .salaryRange(90_000, 130_000)
            .remote(true)
            .requiredExperience(5)
            .skills("Java", "Spring Boot", "React", "AWS", "Docker")
            .build();
        System.out.println("  " + j2);
    }

    // ── 4. Observer ──────────────────────────────────────────
    static void demo04_Observer() {
        System.out.println("\n── 4. Observer Pattern ──");

        JobBoard board = new JobBoard("FutureFrame Job Portal");
        board.subscribe(new EmailAlert("srivalli@futureframe.com"));
        board.subscribe(new SMSAlert("+91-98765-43210"));
        board.subscribe(new SlackAlert("#java-jobs"));

        board.postJob("Senior Java Engineer — Remote — $120k");
        System.out.println();
        board.postJob("React Developer — Hyderabad — $95k");
    }

    // ── 5. Strategy ──────────────────────────────────────────
    static void demo05_Strategy() {
        System.out.println("\n── 5. Strategy Pattern ──");

        int[] data = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("  Original:    " + Arrays.toString(data));

        SortContext ctx = new SortContext(new BubbleSortStrategy());
        ctx.sort(data);
        System.out.println("  BubbleSort:  " + Arrays.toString(data));

        int[] data2 = {64, 34, 25, 12, 22, 11, 90};
        ctx.setStrategy(new SelectionSortStrategy());
        ctx.sort(data2);
        System.out.println("  SelectionSort: " + Arrays.toString(data2));

        // Lambda strategy (Java 8+)
        int[] data3 = {64, 34, 25, 12, 22, 11, 90};
        ctx.setStrategy(arr -> Arrays.sort(arr)); // JDK sort as strategy
        ctx.sort(data3);
        System.out.println("  Arrays.sort: " + Arrays.toString(data3));
    }

    // ════════════════════════════════════════════════
    //  PATTERN IMPLEMENTATIONS
    // ════════════════════════════════════════════════

    // ── Singleton ──
    static class AppConfig {
        private static class Holder {
            static final AppConfig INSTANCE = new AppConfig();
        }
        private String appName = "FutureFrame-Dev";
        private final String dbUrl  = "jdbc:mysql://localhost:3306/futureframe_db";

        private AppConfig() {}
        public static AppConfig getInstance() { return Holder.INSTANCE; }
        public String getAppName()            { return appName; }
        public String getDbUrl()              { return dbUrl; }
        public void setAppName(String n)      { this.appName = n; }
    }

    // ── Factory ──
    interface NotificationService {
        void send(String recipient, String message);
    }
    static class EmailNotification implements NotificationService {
        public void send(String r, String m) {
            System.out.printf("  [EMAIL]  To: %-25s | %s%n", r, m); }
    }
    static class SMSNotification implements NotificationService {
        public void send(String r, String m) {
            System.out.printf("  [SMS]    To: %-25s | %s%n", r, m); }
    }
    static class PushNotification implements NotificationService {
        public void send(String r, String m) {
            System.out.printf("  [PUSH]   To: %-25s | %s%n", r, m); }
    }
    static class SlackNotification implements NotificationService {
        public void send(String r, String m) {
            System.out.printf("  [SLACK]  To: %-25s | %s%n", r, m); }
    }
    static class NotificationFactory {
        static NotificationService create(String type) {
            return switch (type.toUpperCase()) {
                case "EMAIL" -> new EmailNotification();
                case "SMS"   -> new SMSNotification();
                case "PUSH"  -> new PushNotification();
                case "SLACK" -> new SlackNotification();
                default -> throw new IllegalArgumentException("Unknown notification type: " + type);
            };
        }
    }

    // ── Builder ──
    static class JobPosting {
        private final String title, company;
        private final String location;
        private final double minSalary, maxSalary;
        private final boolean remote;
        private final int requiredExperience;
        private final List<String> skills;

        private JobPosting(Builder b) {
            this.title              = b.title;
            this.company            = b.company;
            this.location           = b.location;
            this.minSalary          = b.minSalary;
            this.maxSalary          = b.maxSalary;
            this.remote             = b.remote;
            this.requiredExperience = b.requiredExperience;
            this.skills             = b.skills;
        }

        @Override public String toString() {
            return String.format("JobPosting{title='%s', company='%s', loc='%s', salary=$%.0f-$%.0f, remote=%b, exp=%d+ yrs, skills=%s}",
                title, company, location, minSalary, maxSalary, remote, requiredExperience, skills);
        }

        static class Builder {
            private final String title, company;
            private String location           = "Remote";
            private double minSalary          = 0;
            private double maxSalary          = 0;
            private boolean remote            = false;
            private int requiredExperience    = 0;
            private List<String> skills       = new ArrayList<>();

            Builder(String title, String company) { this.title=title; this.company=company; }
            Builder location(String l)            { this.location=l;             return this; }
            Builder salaryRange(double min, double max) { minSalary=min; maxSalary=max; return this; }
            Builder remote(boolean r)             { this.remote=r;               return this; }
            Builder requiredExperience(int y)     { this.requiredExperience=y;   return this; }
            Builder skills(String... s)           { this.skills=Arrays.asList(s); return this; }
            JobPosting build()                    { return new JobPosting(this); }
        }
    }

    // ── Observer ──
    interface JobObserver { void onNewJob(String jobTitle); }

    static class JobBoard {
        private final String name;
        private final List<JobObserver> observers = new ArrayList<>();
        JobBoard(String name) { this.name = name; }
        void subscribe(JobObserver o)   { observers.add(o); }
        void unsubscribe(JobObserver o) { observers.remove(o); }
        void postJob(String title) {
            System.out.println("  [" + name + "] New posting: " + title);
            observers.forEach(o -> o.onNewJob(title));
        }
    }
    static class EmailAlert implements JobObserver {
        String email;
        EmailAlert(String email) { this.email = email; }
        public void onNewJob(String t) { System.out.println("    📧 Email → " + email + ": " + t); }
    }
    static class SMSAlert implements JobObserver {
        String phone;
        SMSAlert(String phone) { this.phone = phone; }
        public void onNewJob(String t) { System.out.println("    📱 SMS → " + phone + ": " + t); }
    }
    static class SlackAlert implements JobObserver {
        String channel;
        SlackAlert(String ch) { this.channel = ch; }
        public void onNewJob(String t) { System.out.println("    💬 Slack → " + channel + ": " + t); }
    }

    // ── Strategy ──
    @FunctionalInterface interface SortStrategy { void sort(int[] arr); }

    static class BubbleSortStrategy implements SortStrategy {
        public void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n-1; i++)
                for (int j = 0; j < n-i-1; j++)
                    if (arr[j] > arr[j+1]) { int t=arr[j]; arr[j]=arr[j+1]; arr[j+1]=t; }
        }
    }
    static class SelectionSortStrategy implements SortStrategy {
        public void sort(int[] arr) {
            int n = arr.length;
            for (int i = 0; i < n-1; i++) {
                int min = i;
                for (int j = i+1; j < n; j++) if (arr[j] < arr[min]) min = j;
                int t = arr[min]; arr[min] = arr[i]; arr[i] = t;
            }
        }
    }
    static class SortContext {
        private SortStrategy strategy;
        SortContext(SortStrategy s) { this.strategy = s; }
        void setStrategy(SortStrategy s) { this.strategy = s; }
        void sort(int[] arr) { strategy.sort(arr); }
    }
}
