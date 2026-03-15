# 🏆 Master Interview Q&A

> **100+ questions** across all 7 modules. Review this the night before your interview!  
> ⭐ = Frequently asked | 🔥 = High priority

---

## 📑 Categories

- [Java Fundamentals](#java-fundamentals)
- [OOP](#oop)
- [Collections & Data Structures](#collections--data-structures)
- [Exceptions](#exceptions)
- [Multithreading](#multithreading)
- [Java 8+ Features](#java-8-features)
- [Design Patterns](#design-patterns)
- [Spring Boot](#spring-boot)
- [Coding Problems](#coding-problems)

---

## Java Fundamentals

**⭐ Q: What is JDK vs JRE vs JVM?**
> - **JVM** — runs bytecode (platform-specific)
> - **JRE** — JVM + standard libraries (to **run** Java)
> - **JDK** — JRE + compiler + tools (to **develop** Java)

**⭐ Q: What is the difference between `==` and `.equals()`?**
> - `==` — compares **references** (memory addresses) for objects; values for primitives
> - `.equals()` — compares **content/value**
> - Always use `.equals()` for String and objects!

**⭐ Q: Why is String immutable?**
> Security (used in network connections, DB URLs), thread safety, String Pool caching, hashCode caching.

**⭐ Q: What is the String Pool?**
> Special area in heap where JVM caches String literals. `String s = "hello"` checks pool first. `new String("hello")` always creates a new heap object (avoid!).

**Q: What is the Integer cache range?**
> `-128 to 127`. Integers in this range are reused (==works). Outside: new objects (use equals).

**Q: What is autoboxing? What danger does it have?**
> Automatic conversion between primitive and wrapper. Danger: unboxing `null` throws `NullPointerException`.

**Q: What is the difference between `final`, `finally`, `finalize`?**
> - `final` — keyword: constant, non-extendable class, non-overridable method
> - `finally` — block: always runs after try/catch
> - `finalize()` — method: called by GC before object collected (deprecated Java 9+, avoid)

**Q: What triggers Garbage Collection?**
> When heap is full. You can *suggest* `System.gc()` but can't force it. Modern GC: G1GC (default), ZGC, Shenandoah.

**Q: What is the difference between `>>` and `>>>`?**
> `>>` = signed right shift (preserves sign bit). `>>>` = unsigned right shift (fills with 0).

**Q: What is a static initializer block?**
```java
class Config {
    static String url;
    static { url = System.getenv("DB_URL"); } // runs ONCE when class loads
}
```

---

## OOP

**🔥 Q: What are the 4 pillars of OOP?**
> **Encapsulation** (hide state), **Inheritance** (reuse parent code), **Polymorphism** (one interface many forms), **Abstraction** (hide how, show what).

**⭐ Q: Overloading vs Overriding?**
| | Overloading | Overriding |
|--|--|--|
| Binding | Compile-time | Runtime |
| Location | Same class | Parent-Child |
| Signature | Must differ | Must be same |
| `@Override` | No | ✅ Use it |

**⭐ Q: Abstract class vs Interface?**
| | Abstract Class | Interface |
|--|--|--|
| Keyword | extends (one) | implements (many) |
| Constructor | ✅ | ❌ |
| Instance vars | ✅ | ❌ (static final only) |
| Use for | IS-A + shared code | CAN-DO capability |

**⭐ Q: Can we override static methods?**
> No — static methods are **hidden**, not overridden. Resolution depends on reference type.

**Q: What is the diamond problem?**
> Two interfaces have same default method. Implementing class must override to resolve.

**Q: What is covariant return type?**
> Overriding method can return a **subtype** of the parent's return type.

**Q: Can abstract class have a constructor?**
> Yes — called via `super()` from subclass.

**Q: What is an immutable class?**
> `final` class, `private final` fields, no setters, defensive copies of mutable fields (like `Date`).

---

## Collections & Data Structures

**🔥 Q: How does HashMap work internally?**
> `hashCode(key)` → bucket index. Collision → chain. Java 8: chain > 8 → Red-Black Tree. Rehash at 75% load factor. Always override `hashCode()` and `equals()` together!

**⭐ Q: HashMap vs Hashtable vs ConcurrentHashMap?**
| | HashMap | Hashtable | ConcurrentHashMap |
|--|--|--|--|
| Thread-safe | ❌ | ✅ (whole map locked) | ✅ (bucket-level lock) |
| null key | ✅ 1 | ❌ | ❌ |
| Performance | Best | Worst | High throughput |
| Use? | Single-thread | ❌ Legacy | Multi-thread ✅ |

**⭐ Q: ArrayList vs LinkedList?**
> ArrayList: `get(i)` O(1), random access, 90% of cases.  
> LinkedList: `add/remove` head O(1), when frequent insertions at front.

**⭐ Q: Why override hashCode() when you override equals()?**
> Contract: `a.equals(b)` → `a.hashCode() == b.hashCode()`. Break this → HashMap can't find your object.

**Q: What is fail-fast vs fail-safe iterator?**
> Fail-fast (ArrayList, HashMap): `ConcurrentModificationException` if modified during iteration.  
> Fail-safe (CopyOnWriteArrayList, ConcurrentHashMap): works on snapshot, no exception.

**Q: How to make a Collection thread-safe?**
```java
Collections.synchronizedList(new ArrayList<>())  // coarse lock
new CopyOnWriteArrayList<>()                      // best for read-heavy
new ConcurrentHashMap<>()                         // best for maps
```

**Q: What is the initial capacity and growth of ArrayList?**
> Initial: **10**. Growth: **×1.5** (oldCapacity + oldCapacity/2).

**Q: PriorityQueue default order?**
> **Min-heap** by default (smallest element polled first). Use `Comparator.reverseOrder()` for max-heap.

---

## Exceptions

**⭐ Q: Checked vs Unchecked exceptions?**
> Checked (extends `Exception`): must handle at compile time. External failures (IO, SQL).  
> Unchecked (extends `RuntimeException`): optional. Programming bugs (NPE, ClassCast).

**Q: `throw` vs `throws`?**
> `throw` — **throws** an exception in method body.  
> `throws` — **declares** method might throw in signature.

**Q: Does finally always run?**
> Yes — even if exception thrown or return called. Except: `System.exit()` or JVM crash.

**Q: What happens if exception is thrown in finally?**
> The finally exception **replaces** the original exception — original is silently lost!

**Q: What is exception chaining?**
> Wrapping original exception: `throw new ServiceException("msg", originalException)`. Preserves root cause.

---

## Multithreading

**🔥 Q: synchronized vs volatile?**
| | synchronized | volatile |
|--|--|--|
| Guarantees | Visibility + Atomicity + Mutex | Visibility only |
| Use for | `count++`, compound ops | Simple flags |
| `count++` safe? | ✅ (use AtomicInteger better) | ❌ |

**⭐ Q: What is a race condition?**
> Multiple threads accessing shared data where outcome depends on thread scheduling. Classic: `count++` (read-modify-write = 3 non-atomic steps). Fix: `synchronized`, `AtomicInteger`, `ReentrantLock`.

**⭐ Q: wait() vs sleep()?**
| | wait() | sleep() |
|--|--|--|
| Class | Object | Thread |
| Releases lock? | ✅ | ❌ |
| Must be in synchronized? | ✅ | ❌ |
| Wake up | notify() | timeout |

**Q: What is a deadlock?**
> Thread A holds Lock1, waits for Lock2. Thread B holds Lock2, waits for Lock1. Both stuck forever. Prevention: always acquire locks in same order.

**Q: What is ThreadLocal?**
> Thread-specific variable — each thread has its own copy. No synchronization needed. Always `remove()` in finally to prevent memory leaks.

**Q: What are Virtual Threads (Java 21)?**
> Lightweight JVM-managed threads. Create millions cheaply. Blocking I/O doesn't block OS thread. Ideal for I/O-bound workloads.

---

## Java 8+ Features

**🔥 Q: What is a functional interface?**
> Interface with exactly **one abstract method**. Can have default/static methods. Annotate with `@FunctionalInterface`. Enables lambda expressions.

**⭐ Q: map() vs flatMap()?**
> `map()` — 1-to-1 transform: `Stream<String>` → `Stream<Integer>`  
> `flatMap()` — 1-to-many + flatten: `Stream<List<String>>` → `Stream<String>`

**⭐ Q: Intermediate vs Terminal stream operations?**
> Intermediate (lazy): `filter`, `map`, `sorted`, `distinct`, `limit`, `skip`  
> Terminal (triggers execution): `collect`, `forEach`, `count`, `reduce`, `findFirst`, `anyMatch`

**Q: What is Optional? Best practices?**
> Container for a value that may be absent. Use for return types only (not fields/params). Never call `.get()` without checking — use `.orElse()`, `.orElseThrow()`, `.ifPresent()`.

**Q: reduce() example?**
```java
int sum = Stream.of(1,2,3,4,5).reduce(0, Integer::sum); // 15
```

**Q: What is `groupingBy`?**
```java
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));
```

---

## Design Patterns

**Q: What is Singleton? Thread-safe implementation?**
> One instance. Bill Pugh (static inner class) or Enum — both thread-safe, lazy, no sync overhead.

**Q: Where is Singleton used in Spring?**
> Spring beans are Singleton scope by default. `Runtime.getRuntime()`, Logger factories.

**Q: Factory vs Abstract Factory?**
> Factory — creates one product type.  
> Abstract Factory — creates a **family** of related products.

**Q: Builder pattern use case?**
> Objects with many optional parameters. Avoids telescoping constructors. `@Builder` in Lombok.

**Q: Observer pattern in Java/Spring?**
> Spring `@EventListener`, Java `PropertyChangeListener`, RxJava/Reactor reactive streams.

**Q: Strategy pattern real example?**
> `Comparator` in `Collections.sort()`. Payment strategies (card, UPI, wallet). Spring Security's authentication strategies.

---

## Spring Boot

**⭐ Q: `@Controller` vs `@RestController`?**
> `@RestController` = `@Controller` + `@ResponseBody` on every method. Returns JSON directly.

**⭐ Q: `@Transactional` — how does it work?**
> AOP proxy wraps method in DB transaction. Success → commit. RuntimeException → rollback.  
> ⚠️ Only works on calls from **outside** the bean (proxy limitation).

**Q: `@Component` vs `@Service` vs `@Repository`?**
> All register a Spring bean. `@Repository` adds exception translation (SQL → Spring DataAccessException).

**Q: What is N+1 problem in JPA?**
> 1 query for N parents + N queries for each child. Fix: `JOIN FETCH`, `@EntityGraph`, or DTO projections.

**Q: `@Bean` vs `@Component`?**
> `@Component` — on your class (auto-detected). `@Bean` — on method in `@Configuration` (use for 3rd party classes you can't annotate).

---

## Coding Problems

```java
// Reverse a String
new StringBuilder(s).reverse().toString();

// Check palindrome
boolean isPalindrome(String s) {
    String clean = s.toLowerCase().replaceAll("[^a-z0-9]","");
    int l = 0, r = clean.length()-1;
    while (l < r) {
        if (clean.charAt(l++) != clean.charAt(r--)) return false;
    }
    return true;
}

// Find duplicates in array
Set<Integer> seen = new HashSet<>();
Arrays.stream(arr).filter(n -> !seen.add(n)).distinct().forEach(System.out::println);

// Word frequency (top 3)
Arrays.stream(text.split("\\s+"))
    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
    .entrySet().stream()
    .sorted(Map.Entry.<String,Long>comparingByValue().reversed())
    .limit(3)
    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

// First non-repeating character
char firstUnique(String s) {
    Map<Character, Long> freq = s.chars().mapToObj(c -> (char)c)
        .collect(Collectors.groupingBy(Function.identity(),
                 LinkedHashMap::new, Collectors.counting()));
    return freq.entrySet().stream()
        .filter(e -> e.getValue() == 1)
        .map(Map.Entry::getKey)
        .findFirst().orElse('\0');
}

// FizzBuzz with Streams (Java 8)
IntStream.rangeClosed(1, 100)
    .mapToObj(i -> i % 15 == 0 ? "FizzBuzz"
                : i % 3 == 0  ? "Fizz"
                : i % 5 == 0  ? "Buzz"
                : String.valueOf(i))
    .forEach(System.out::println);

// Fibonacci with Stream
Stream.iterate(new long[]{0,1}, f -> new long[]{f[1], f[0]+f[1]})
    .limit(10).map(f -> f[0]).forEach(System.out::println);

// Anagram check
boolean isAnagram(String a, String b) {
    char[] ca = a.toCharArray(); char[] cb = b.toCharArray();
    Arrays.sort(ca); Arrays.sort(cb);
    return Arrays.equals(ca, cb);
}
```

---

*← [Module 07 — Design Patterns](Module-07-Design-Patterns) | [Cheat Sheet →](Cheat-Sheet)*
