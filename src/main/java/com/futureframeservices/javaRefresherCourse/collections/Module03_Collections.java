package com.futureframeservices.javaRefresherCourse.collections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================
 * com.futureframeservices.javaRefresherCourse.collections
 * Module 03 — Collections Framework
 * Topics: List, Set, Map, Queue, Comparator, Sorting
 * ============================================================
 */
public class Module03_Collections {

    public static void run() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║  MODULE 03 — COLLECTIONS FRAMEWORK       ║");
        System.out.println("╚══════════════════════════════════════════╝");

        demo01_ListOperations();
        demo02_SetOperations();
        demo03_MapOperations();
        demo04_QueueAndDeque();
        demo05_SortingAndComparator();
        demo06_WordFrequency();
    }

    // ── 1. List ──────────────────────────────────────────────
    static void demo01_ListOperations() {
        System.out.println("\n── 1. List (ArrayList) ──");

        List<String> techs = new ArrayList<>(Arrays.asList(
                "Spring Boot", "Java", "Docker", "Java", "Kubernetes", "AWS"));

        System.out.println("Original:          " + techs);
        techs.remove("Java");       // removes FIRST occurrence
        System.out.println("After remove Java: " + techs);

        techs.add(0, "Microservices");
        System.out.println("After add at 0:    " + techs);

        Collections.sort(techs);
        System.out.println("Sorted:            " + techs);

        System.out.println("Contains Docker:   " + techs.contains("Docker"));
        System.out.println("Index of AWS:      " + techs.indexOf("AWS"));
        System.out.println("subList(0,3):      " + techs.subList(0, 3));

        // removeIf (safe during iteration)
        techs.removeIf(t -> t.length() > 10);
        System.out.println("After removeIf(>10 chars): " + techs);
    }

    // ── 2. Set ───────────────────────────────────────────────
    static void demo02_SetOperations() {
        System.out.println("\n── 2. Set Operations ──");

        Set<Integer> teamA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> teamB = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));

        // Union
        Set<Integer> union = new HashSet<>(teamA);
        union.addAll(teamB);

        // Intersection
        Set<Integer> intersection = new HashSet<>(teamA);
        intersection.retainAll(teamB);

        // Difference A - B
        Set<Integer> diff = new HashSet<>(teamA);
        diff.removeAll(teamB);

        System.out.println("Team A:            " + new TreeSet<>(teamA));
        System.out.println("Team B:            " + new TreeSet<>(teamB));
        System.out.println("Union:             " + new TreeSet<>(union));
        System.out.println("Intersection:      " + new TreeSet<>(intersection));
        System.out.println("A - B (diff):      " + new TreeSet<>(diff));

        // LinkedHashSet preserves insertion order
        Set<String> ordered = new LinkedHashSet<>(
            Arrays.asList("banana", "apple", "cherry", "apple", "date"));
        System.out.println("LinkedHashSet:     " + ordered); // no duplicate, insertion order

        // TreeSet auto-sorts
        Set<String> sorted = new TreeSet<>(ordered);
        System.out.println("TreeSet (sorted):  " + sorted);
    }

    // ── 3. Map ───────────────────────────────────────────────
    static void demo03_MapOperations() {
        System.out.println("\n── 3. Map Operations ──");

        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Alice",   92);
        scores.put("Bob",     85);
        scores.put("Charlie", 97);
        scores.put("Diana",   88);
        scores.put("Eve",     92);

        System.out.println("Scores: " + scores);
        System.out.println("getOrDefault Frank: " + scores.getOrDefault("Frank", 0));

        // merge — increment existing value
        scores.merge("Alice", 3, Integer::sum);
        System.out.println("Alice after merge +3: " + scores.get("Alice"));

        // putIfAbsent
        scores.putIfAbsent("Bob", 999); // won't change Bob
        System.out.println("Bob after putIfAbsent: " + scores.get("Bob"));

        // Sort by value descending
        System.out.println("\nRanking (sorted by score desc):");
        scores.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(e -> System.out.printf("  %-10s → %d%n", e.getKey(), e.getValue()));

        // computeIfAbsent — great for grouping
        Map<Integer, List<String>> byLength = new TreeMap<>();
        for (String name : scores.keySet()) {
            byLength.computeIfAbsent(name.length(), k -> new ArrayList<>()).add(name);
        }
        System.out.println("\nGrouped by name length: " + byLength);
    }

    // ── 4. Queue & Deque ─────────────────────────────────────
    static void demo04_QueueAndDeque() {
        System.out.println("\n── 4. Queue & Deque ──");

        // Min-Heap PriorityQueue
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int[] data = {5, 2, 8, 1, 9, 3, 7};
        for (int n : data) minHeap.offer(n);

        System.out.print("PriorityQueue (min-heap) poll order: ");
        while (!minHeap.isEmpty()) System.out.print(minHeap.poll() + " ");
        System.out.println();

        // Max-Heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int n : data) maxHeap.offer(n);
        System.out.print("PriorityQueue (max-heap) poll order: ");
        while (!maxHeap.isEmpty()) System.out.print(maxHeap.poll() + " ");
        System.out.println();

        // ArrayDeque as Stack (LIFO)
        Deque<String> stack = new ArrayDeque<>();
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        System.out.println("Stack peek: " + stack.peek());
        System.out.println("Stack pop:  " + stack.pop() + " (LIFO)");

        // ArrayDeque as Queue (FIFO)
        Deque<String> queue = new ArrayDeque<>();
        queue.offer("Task-1");
        queue.offer("Task-2");
        queue.offer("Task-3");
        System.out.print("Queue poll order (FIFO): ");
        while (!queue.isEmpty()) System.out.print(queue.poll() + " ");
        System.out.println();
    }

    // ── 5. Sorting & Comparator ──────────────────────────────
    static void demo05_SortingAndComparator() {
        System.out.println("\n── 5. Sorting & Comparator ──");

        List<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee("Charlie", "Engineering", 95_000, 5),
            new Employee("Alice",   "Marketing",   70_000, 3),
            new Employee("Bob",     "Engineering",  95_000, 7),
            new Employee("Diana",   "HR",           80_000, 4),
            new Employee("Eve",     "Marketing",   90_000, 6)
        ));

        // Sort by salary desc, then by name asc
        employees.sort(Comparator.comparingDouble(Employee::salary)
                                 .reversed()
                                 .thenComparing(Employee::name));

        System.out.println("By salary desc → name asc:");
        employees.forEach(e ->
            System.out.printf("  %-10s %-15s $%-8.0f yrs=%d%n",
                e.name(), e.department(), e.salary(), e.years()));
    }

    // ── 6. Word Frequency (classic interview problem) ────────
    static void demo06_WordFrequency() {
        System.out.println("\n── 6. Word Frequency Counter ──");

        String text = "the quick brown fox jumps over the lazy dog the fox";
        Map<String, Long> freq = Arrays.stream(text.split("\\s+"))
            .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        System.out.println("Top 5 words by frequency:");
        freq.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .forEach(e -> System.out.printf("  %-10s → %d%n", e.getKey(), e.getValue()));
    }

    // record used as DTO inside module
    record Employee(String name, String department, double salary, int years) {}
}
