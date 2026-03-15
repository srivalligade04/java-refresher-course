package com.futureframeservices.javaRefresherCourse.java8;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * ============================================================
 * com.futureframeservices.javaRefresherCourse.java8
 * Module 06 — Java 8+ Features
 * Topics: Lambdas, Streams, Optional, Functional Interfaces,
 *         Method References, Collectors, CompletableFuture
 * ============================================================
 */
public class Module06_Java8Features {

    record Developer(String name, String tech, String level, double salary, int experience) {}

    public static void run() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║  MODULE 06 — JAVA 8+ FEATURES            ║");
        System.out.println("╚══════════════════════════════════════════╝");

        demo01_LambdaAndFunctionalInterfaces();
        demo02_MethodReferences();
        demo03_StreamsPipeline();
        demo04_StreamCollectors();
        demo05_Optional();
        demo06_StreamsPracticalProblems();
    }

    static final List<Developer> DEVS = List.of(
        new Developer("Alice",   "Java",    "Senior", 110_000, 8),
        new Developer("Bob",     "Python",  "Junior",  65_000, 2),
        new Developer("Charlie", "Java",    "Lead",   130_000, 12),
        new Developer("Diana",   "React",   "Mid",     85_000, 5),
        new Developer("Eve",     "Java",    "Senior",  105_000, 7),
        new Developer("Frank",   "Python",  "Senior",  100_000, 9),
        new Developer("Grace",   "React",   "Junior",  60_000,  1),
        new Developer("Heidi",   "Java",    "Mid",      80_000, 4)
    );

    // ── 1. Lambda & Functional Interfaces ───────────────────
    static void demo01_LambdaAndFunctionalInterfaces() {
        System.out.println("\n── 1. Lambdas & Functional Interfaces ──");

        // Function<T, R> — transform
        Function<String, Integer> strLen      = String::length;
        Function<Integer, Integer> square     = n -> n * n;
        Function<String, Integer> lenThenSq   = strLen.andThen(square);
        System.out.println("  'FutureFrame'.length² = " + lenThenSq.apply("FutureFrame"));

        // Predicate<T> — test
        Predicate<Developer> isSenior        = d -> d.level().equals("Senior");
        Predicate<Developer> isJavaDev       = d -> d.tech().equals("Java");
        Predicate<Developer> seniorJavaDev   = isSenior.and(isJavaDev);

        System.out.println("  Senior Java devs:");
        DEVS.stream().filter(seniorJavaDev)
            .forEach(d -> System.out.printf("    %-10s $%.0f%n", d.name(), d.salary()));

        // Consumer<T> — consume
        Consumer<Developer> printDev = d ->
            System.out.printf("    %-10s %-8s %-8s%n", d.name(), d.tech(), d.level());

        System.out.println("  All developers:");
        DEVS.forEach(printDev);

        // Supplier<T> — produce
        Supplier<List<Developer>> devListFactory = ArrayList::new;
        List<Developer> copy = devListFactory.get();
        copy.addAll(DEVS);
        System.out.println("  Supplier created list with " + copy.size() + " devs");

        // BiFunction
        BiFunction<Developer, Double, String> appraise =
            (d, bonus) -> d.name() + " bonus: $" + String.format("%.0f", d.salary() * bonus);
        System.out.println("  " + appraise.apply(DEVS.get(0), 0.10));
    }

    // ── 2. Method References ─────────────────────────────────
    static void demo02_MethodReferences() {
        System.out.println("\n── 2. Method References ──");

        List<String> names = Arrays.asList("charlie", "alice", "bob", "diana", "eve");

        // Static method reference
        names.stream().map(String::toUpperCase)
            .forEach(s -> System.out.print("  " + s + " "));
        System.out.println();

        // Instance method reference on type
        names.sort(String::compareToIgnoreCase);
        System.out.println("  Sorted: " + names);

        // Instance method reference on specific object
        String prefix = "Dev:";
        Function<String, String> addPrefix = prefix::concat;
        names.stream().map(addPrefix).forEach(s -> System.out.print(s + " "));
        System.out.println();

        // Constructor reference
        Supplier<ArrayList<String>> listMaker = ArrayList::new;
        ArrayList<String> fresh = listMaker.get();
        fresh.add("New item");
        System.out.println("  Constructor ref created: " + fresh);
    }

    // ── 3. Streams Pipeline ──────────────────────────────────
    static void demo03_StreamsPipeline() {
        System.out.println("\n── 3. Streams Pipeline ──");

        // Senior devs earning > 100k, sorted by salary desc
        System.out.println("  Senior devs > $100k:");
        DEVS.stream()
            .filter(d -> d.level().equals("Senior") && d.salary() > 100_000)
            .sorted(Comparator.comparingDouble(Developer::salary).reversed())
            .map(d -> String.format("    %-10s $%.0f", d.name(), d.salary()))
            .forEach(System.out::println);

        // Average salary
        OptionalDouble avgSalary = DEVS.stream()
            .mapToDouble(Developer::salary)
            .average();
        System.out.printf("  Average salary: $%.0f%n", avgSalary.orElse(0));

        // Total payroll
        double total = DEVS.stream().mapToDouble(Developer::salary).sum();
        System.out.printf("  Total payroll: $%.0f%n", total);

        // flatMap: split skills string into individual tags
        List<String> techStacks = List.of("Java,Spring,Hibernate", "Python,Django,FastAPI", "React,Node,TypeScript");
        List<String> allTechs = techStacks.stream()
            .flatMap(stack -> Arrays.stream(stack.split(",")))
            .sorted()
            .distinct()
            .collect(Collectors.toList());
        System.out.println("  All unique techs: " + allTechs);

        // reduce
        int totalYears = DEVS.stream()
            .mapToInt(Developer::experience)
            .reduce(0, Integer::sum);
        System.out.println("  Total experience (team): " + totalYears + " years");
    }

    // ── 4. Collectors ────────────────────────────────────────
    static void demo04_StreamCollectors() {
        System.out.println("\n── 4. Collectors ──");

        // groupingBy tech
        Map<String, List<String>> byTech = DEVS.stream()
            .collect(Collectors.groupingBy(
                Developer::tech,
                Collectors.mapping(Developer::name, Collectors.toList())));
        System.out.println("  Grouped by tech:");
        new TreeMap<>(byTech).forEach((tech, names) ->
            System.out.println("    " + tech + ": " + names));

        // groupingBy + counting
        Map<String, Long> countByLevel = DEVS.stream()
            .collect(Collectors.groupingBy(Developer::level, Collectors.counting()));
        System.out.println("  Headcount by level: " + new TreeMap<>(countByLevel));

        // groupingBy + average salary
        Map<String, Double> avgByTech = DEVS.stream()
            .collect(Collectors.groupingBy(Developer::tech,
                     Collectors.averagingDouble(Developer::salary)));
        System.out.println("  Avg salary by tech:");
        new TreeMap<>(avgByTech).forEach((tech, avg) ->
            System.out.printf("    %-8s $%.0f%n", tech, avg));

        // partitioningBy — senior vs non-senior
        Map<Boolean, Long> partition = DEVS.stream()
            .collect(Collectors.partitioningBy(
                d -> d.level().equals("Senior"), Collectors.counting()));
        System.out.println("  Senior: " + partition.get(true) + ", Non-senior: " + partition.get(false));

        // joining
        String roster = DEVS.stream()
            .map(Developer::name)
            .collect(Collectors.joining(", ", "Team[", "]"));
        System.out.println("  " + roster);

        // Statistics
        DoubleSummaryStatistics stats = DEVS.stream()
            .mapToDouble(Developer::salary)
            .summaryStatistics();
        System.out.printf("  Salary stats → min=$%.0f max=$%.0f avg=$%.0f count=%d%n",
            stats.getMin(), stats.getMax(), stats.getAverage(), stats.getCount());
    }

    // ── 5. Optional ──────────────────────────────────────────
    static void demo05_Optional() {
        System.out.println("\n── 5. Optional ──");

        // Find first Java Senior
        Optional<Developer> javaSenior = DEVS.stream()
            .filter(d -> d.tech().equals("Java") && d.level().equals("Senior"))
            .findFirst();
        System.out.println("  First Java Senior: " + javaSenior.map(Developer::name).orElse("None"));

        // orElse, orElseGet, orElseThrow
        Optional<Developer> notFound = DEVS.stream()
            .filter(d -> d.tech().equals("COBOL"))
            .findFirst();
        System.out.println("  COBOL dev: " + notFound.map(Developer::name).orElse("Not Found"));

        // map + filter chain
        String result = DEVS.stream()
            .filter(d -> d.name().equals("Charlie"))
            .findFirst()
            .map(d -> d.name() + " earns $" + (int) d.salary())
            .orElse("Developer not found");
        System.out.println("  " + result);

        // ifPresent
        DEVS.stream()
            .filter(d -> d.experience() > 10)
            .findFirst()
            .ifPresent(d -> System.out.println("  Most experienced: " + d.name() + " (" + d.experience() + " yrs)"));
    }

    // ── 6. Practical Interview Problems ─────────────────────
    static void demo06_StreamsPracticalProblems() {
        System.out.println("\n── 6. Practical Stream Problems ──");

        // P1: Top 3 highest-paid developers
        System.out.println("  Top 3 highest paid:");
        DEVS.stream()
            .sorted(Comparator.comparingDouble(Developer::salary).reversed())
            .limit(3)
            .forEach(d -> System.out.printf("    %-10s $%.0f%n", d.name(), d.salary()));

        // P2: Count distinct tech stacks
        long distinctTechs = DEVS.stream().map(Developer::tech).distinct().count();
        System.out.println("  Distinct tech stacks: " + distinctTechs);

        // P3: Is anyone a Python Lead?
        boolean pythonLead = DEVS.stream()
            .anyMatch(d -> d.tech().equals("Python") && d.level().equals("Lead"));
        System.out.println("  Any Python Lead? " + pythonLead);

        // P4: All seniors earn > 90k?
        boolean allSeniorsEarnWell = DEVS.stream()
            .filter(d -> d.level().equals("Senior"))
            .allMatch(d -> d.salary() > 90_000);
        System.out.println("  All Seniors earn > $90k? " + allSeniorsEarnWell);

        // P5: Name → Salary map
        Map<String, Double> nameToSalary = DEVS.stream()
            .collect(Collectors.toMap(Developer::name, Developer::salary));
        System.out.println("  Alice's salary: $" + (int) nameToSalary.get("Alice").doubleValue());

        // P6: Sum of salaries of Java devs only
        double javaTotalSalary = DEVS.stream()
            .filter(d -> d.tech().equals("Java"))
            .mapToDouble(Developer::salary)
            .sum();
        System.out.printf("  Total Java team payroll: $%.0f%n", javaTotalSalary);
    }
}
