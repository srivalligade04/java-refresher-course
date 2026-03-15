# 📘 Module 03 — Collections Framework

> **Package:** `com.futureframeservices.javaRefresherCourse.collections`  
> **File:** `Module03_Collections.java`  
> **Time:** ~45 minutes

---

## 📋 Topics Covered

- [Collections Hierarchy](#collections-hierarchy)
- [List — ArrayList & LinkedList](#1-list)
- [Set — HashSet, LinkedHashSet, TreeSet](#2-set)
- [Map — HashMap, LinkedHashMap, TreeMap](#3-map)
- [Queue & Deque](#4-queue--deque)
- [Sorting & Comparator](#5-sorting--comparator)
- [HashMap Internals 🔥](#-hashmap-internals--top-interview-topic)
- [Interview Q&A](#-interview-qa)

---

## Collections Hierarchy

```
                    Iterable<E>
                        │
                  Collection<E>
           ┌────────────┼─────────────┐
         List<E>      Set<E>        Queue<E>
           │            │               │
      ArrayList      HashSet        PriorityQueue
      LinkedList  LinkedHashSet     ArrayDeque
      Vector         TreeSet

                   Map<K,V>  (separate hierarchy)
            ┌──────────┼──────────┐
         HashMap    TreeMap  LinkedHashMap
         Hashtable  ConcurrentHashMap
```

---

## 1. List

### ArrayList vs LinkedList

| Operation | ArrayList | LinkedList |
|-----------|-----------|------------|
| Backed by | Dynamic array | Doubly linked list |
| `get(i)` | O(1) ✅ | O(n) ❌ |
| `add(end)` | O(1) amortized | O(1) |
| `add(middle)` | O(n) | O(1) ✅ |
| `remove(middle)` | O(n) | O(1) ✅ |
| Memory | Less | More (node overhead) |
| Use when | Frequent reads | Frequent insert/delete |

> 🔑 **Use ArrayList in 90% of cases.** Only switch to LinkedList when you have many insertions/deletions at head or middle.

```java
List<String> list = new ArrayList<>();
list.add("Spring Boot");
list.add("Docker");
list.add(0, "Java");            // insert at index 0
list.remove("Docker");          // remove by value (first occurrence)
list.remove(0);                 // remove by index
list.get(0);                    // O(1) random access
list.size();
list.contains("Java");
list.indexOf("Java");
list.set(0, "Kotlin");          // replace

Collections.sort(list);
Collections.reverse(list);
Collections.shuffle(list);

list.subList(0, 2);             // [0, 2) view
list.removeIf(s -> s.length() > 5);  // safe removal during iteration

// Immutable list (Java 9+)
List<String> immutable = List.of("a", "b", "c");
```

---

## 2. Set

```java
// HashSet — O(1) ops, NO order guaranteed
Set<String> hashSet = new HashSet<>();

// LinkedHashSet — preserves INSERTION order
Set<String> linkedSet = new LinkedHashSet<>();

// TreeSet — SORTED order, O(log n)
Set<String> treeSet = new TreeSet<>();

// Set operations
Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
Set<Integer> b = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));

Set<Integer> union        = new HashSet<>(a); union.addAll(b);        // {1,2,3,4,5,6,7,8}
Set<Integer> intersection = new HashSet<>(a); intersection.retainAll(b); // {4,5}
Set<Integer> difference   = new HashSet<>(a); difference.removeAll(b);   // {1,2,3}
```

---

## 3. Map

### Core Operations

```java
Map<String, Integer> scores = new HashMap<>();

// Put & Get
scores.put("Alice", 92);
scores.get("Alice");                    // 92
scores.getOrDefault("Frank", 0);       // 0 — safe get

// Check
scores.containsKey("Alice");           // true
scores.containsValue(92);              // true

// Remove
scores.remove("Alice");
scores.remove("Alice", 92);            // remove only if value matches

// Advanced
scores.putIfAbsent("Bob", 85);         // won't overwrite existing
scores.merge("Alice", 5, Integer::sum); // Alice = 92 + 5 = 97
scores.computeIfAbsent("newKey", k -> computeDefault(k));

// Iterate
scores.forEach((k, v) -> System.out.println(k + "=" + v));
for (Map.Entry<String, Integer> e : scores.entrySet()) {
    System.out.println(e.getKey() + " → " + e.getValue());
}

scores.keySet();    // Set of keys
scores.values();    // Collection of values
scores.entrySet();  // Set of key-value pairs
```

### HashMap vs TreeMap vs LinkedHashMap

| | HashMap | TreeMap | LinkedHashMap |
|---|---|---|---|
| Order | ❌ No order | ✅ Sorted by key | ✅ Insertion order |
| Performance | O(1) avg | O(log n) | O(1) |
| null keys | 1 allowed | ❌ No | 1 allowed |
| Use when | Fast lookup | Sorted iteration | Ordered iteration |

---

## 🔥 HashMap Internals — Top Interview Topic

```
put("Alice", 92):
  1. hashCode("Alice") → hash → bucket index = hash % capacity
  2. If bucket empty    → store new Entry
  3. If bucket occupied → check equals():
       - equals() true  → UPDATE value
       - equals() false → ADD to chain (collision!)

Java 8+: Chain > 8 entries → convert to Red-Black Tree (O(log n) worst case)
```

**Key facts:**
- Default capacity: **16**
- Load factor: **0.75** (rehash when 75% full)
- Rehash: doubles capacity, recomputes all bucket positions
- `hashCode()` + `equals()` contract — **must override together!**

```java
// ⚠️ What breaks HashMap — equals without hashCode
class Person {
    String name;
    @Override public boolean equals(Object o) {
        return name.equals(((Person)o).name);
    }
    // hashCode NOT overridden → uses Object.hashCode() = memory address
}

Person p1 = new Person("Alice");
Person p2 = new Person("Alice");

p1.equals(p2)                   // true (our equals)
Map<Person,String> map = new HashMap<>();
map.put(p1, "Engineer");
map.get(p2)                     // null! p2 hashes to different bucket!
```

---

## 4. Queue & Deque

```java
// PriorityQueue — Min-Heap by default
PriorityQueue<Integer> minPQ = new PriorityQueue<>();
minPQ.offer(5); minPQ.offer(1); minPQ.offer(3);
minPQ.peek();   // 1 — view min without removing
minPQ.poll();   // 1 — remove and return min

// Max-Heap
PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Comparator.reverseOrder());

// Custom PriorityQueue
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

// ArrayDeque — as Queue (FIFO)
Deque<String> queue = new ArrayDeque<>();
queue.offer("Task1"); queue.offer("Task2");
queue.poll();    // "Task1" — FIFO

// ArrayDeque — as Stack (LIFO)  ← faster than java.util.Stack!
Deque<String> stack = new ArrayDeque<>();
stack.push("A"); stack.push("B"); stack.push("C");
stack.pop();     // "C" — LIFO

// Queue method pairs (null-safe vs exception-throwing)
// offer() vs add()    — both add, offer returns false, add throws
// poll()  vs remove() — both remove, poll returns null, remove throws
// peek()  vs element()— both view, peek returns null, element throws
```

---

## 5. Sorting & Comparator

```java
// Natural order (implements Comparable)
List<String> names = Arrays.asList("Charlie","Alice","Bob");
Collections.sort(names);              // [Alice, Bob, Charlie]

// Comparator lambda
names.sort(Comparator.reverseOrder());                      // [Charlie, Bob, Alice]
names.sort(Comparator.comparingInt(String::length));        // by length
names.sort(Comparator.comparingInt(String::length)
           .thenComparing(Comparator.naturalOrder()));       // multi-level

// Custom objects
List<Employee> employees = new ArrayList<>();
employees.sort(Comparator.comparing(Employee::getDepartment)
                          .thenComparingDouble(Employee::getSalary)
                          .reversed());

// Comparable vs Comparator
// Comparable → "natural order" defined inside the class (compareTo)
// Comparator → "custom order" defined outside (compare, or lambda)
```

---

## ❓ Interview Q&A

**Q: How does HashMap work internally?**
> `put(key, value)` → `hashCode(key)` → bucket index. Collision → chained list. Java 8: chain > 8 → Red-Black Tree. Rehash at 75% capacity.

**Q: What happens if two objects have the same hashCode?**
> **Collision** — both go into the same bucket (linked list). `equals()` is used to find the correct one. Performance degrades but still correct.

**Q: Why must you override `hashCode()` when you override `equals()`?**
> Contract: if `a.equals(b)` then `a.hashCode() == b.hashCode()`. If you break this, HashMap cannot find your object — it looks in the wrong bucket.

**Q: What is the difference between `fail-fast` and `fail-safe` iterators?**
> **Fail-fast** (ArrayList, HashMap): throws `ConcurrentModificationException` if modified during iteration.  
> **Fail-safe** (CopyOnWriteArrayList, ConcurrentHashMap): works on a snapshot, no exception.

**Q: How do you make a Collection thread-safe?**
```java
List<String> safe = Collections.synchronizedList(new ArrayList<>());  // coarse lock
List<String> cow  = new CopyOnWriteArrayList<>();                      // best for read-heavy
Map<K,V> map      = new ConcurrentHashMap<>();                         // best for maps
```

**Q: ArrayList initial capacity and growth factor?**
> Initial capacity: **10**. Growth: **×1.5** (newCapacity = oldCapacity + oldCapacity/2).

**Q: What is the time complexity of HashMap operations?**
> Average: O(1) for get, put, remove. Worst case (all collisions): O(n). Java 8 TreeNode: O(log n) worst case.

---

*← [Module 02 — OOP](Module-02-OOP) | [Module 04 — Exceptions →](Module-04-Exceptions)*
