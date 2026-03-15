# 📘 Module 06 — Java 8+ Features

> **Package:** `com.futureframeservices.javaRefresherCourse.java8`  
> **File:** `Module06_Java8Features.java`  
> **Time:** ~30 minutes

---

## 📋 Topics Covered

- [Lambda Expressions](#1-lambda-expressions)
- [Functional Interfaces](#2-functional-interfaces)
- [Method References](#3-method-references)
- [Streams API](#4-streams-api)
- [Stream Collectors](#5-stream-collectors)
- [Optional](#6-optional)
- [Java 9–21 Highlights](#7-java-9-21-highlights)
- [Interview Q&A](#-interview-qa)

---

## 1. Lambda Expressions

```java
// Old anonymous class
Runnable r1 = new Runnable() {
    public void run() { System.out.println("Hello"); }
};

// Lambda — concise
Runnable r2 = () -> System.out.println("Hello");

// With parameters
Comparator<String> comp = (a, b) -> a.compareTo(b);

// With body block
Comparator<String> comp2 = (a, b) -> {
    System.out.println("Comparing " + a + " and " + b);
    return a.compareTo(b);
};
```

---

## 2. Functional Interfaces

> An interface with **exactly one abstract method**. Use `@FunctionalInterface` annotation.

### The Big 4

```java
// Function<T, R> — takes T, returns R
Function<String, Integer> length = String::length;
length.apply("Hello");   // 5

Function<Integer, Integer> square = n -> n * n;
Function<String, Integer> lengthThenSquare = length.andThen(square);
lengthThenSquare.apply("Hello");  // 25

// Consumer<T> — takes T, returns void
Consumer<String> print = System.out::println;
print.accept("FutureFrame");

// Supplier<T> — takes nothing, returns T
Supplier<List<String>> listFactory = ArrayList::new;
List<String> list = listFactory.get();

// Predicate<T> — takes T, returns boolean
Predicate<String> isEmpty  = String::isEmpty;
Predicate<String> notEmpty = isEmpty.negate();
Predicate<Integer> isEven  = n -> n % 2 == 0;
Predicate<Integer> isLarge = n -> n > 100;
Predicate<Integer> bigEven = isEven.and(isLarge);

isEmpty.test("");       // true
notEmpty.test("hi");    // true
bigEven.test(200);      // true
bigEven.test(50);       // false (even but not large)
```

### Other Useful Interfaces

```java
BiFunction<String, Integer, String> repeat = (s, n) -> s.repeat(n);
repeat.apply("Ha", 3);  // "HaHaHa"

UnaryOperator<String> upper = String::toUpperCase;
upper.apply("hello");   // "HELLO"

BinaryOperator<Integer> add = Integer::sum;
add.apply(3, 4);        // 7
```

---

## 3. Method References

```java
// 4 types
ClassName::staticMethod     // Integer::parseInt
object::instanceMethod      // System.out::println
ClassName::instanceMethod   // String::toUpperCase
ClassName::new              // ArrayList::new

// Examples
List<String> names = Arrays.asList("bob", "alice", "charlie");

names.forEach(System.out::println);              // instance on specific object
names.sort(String::compareToIgnoreCase);         // instance on type (each element)
names.stream().map(String::toUpperCase)...       // transform each
names.stream().map(Integer::parseInt)...         // static method
Supplier<List<String>> s = ArrayList::new;       // constructor ref
```

---

## 4. Streams API

> Streams are **lazy** — intermediate ops don't execute until a terminal op is called.  
> Streams are **not reusable** — create a new stream each time.

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Intermediate operations (lazy)
.filter(n -> n % 2 == 0)           // keep evens: [2,4,6,8,10]
.map(n -> n * n)                   // transform: [4,16,36,64,100]
.flatMap(Collection::stream)       // flatten nested
.sorted()                          // natural order
.sorted(Comparator.reverseOrder()) // reverse
.distinct()                        // remove duplicates
.limit(5)                          // first 5
.skip(3)                           // skip first 3
.peek(System.out::println)         // debug without consuming

// Terminal operations (eager — trigger pipeline)
.collect(Collectors.toList())
.collect(Collectors.toSet())
.collect(Collectors.toMap(...))
.forEach(System.out::println)
.count()                           // long
.findFirst()                       // Optional<T>
.findAny()                         // Optional<T> (faster in parallel)
.min(Comparator.naturalOrder())    // Optional<T>
.max(Comparator.naturalOrder())    // Optional<T>
.anyMatch(pred)                    // boolean — short-circuits
.allMatch(pred)                    // boolean
.noneMatch(pred)                   // boolean
.reduce(0, Integer::sum)           // T
.toArray()                         // Object[]
.sum() / .average() / .summaryStatistics()  // IntStream / DoubleStream
```

### Practical Examples

```java
List<String> engineers = employees.stream()
    .filter(e -> e.department().equals("Engineering"))
    .map(Employee::name)
    .sorted()
    .collect(Collectors.toList());

OptionalDouble avgSalary = employees.stream()
    .mapToDouble(Employee::salary)
    .average();

// flatMap — flatten nested collections
List<List<Integer>> nested = List.of(List.of(1,2), List.of(3,4), List.of(5,6));
List<Integer> flat = nested.stream()
    .flatMap(Collection::stream)
    .collect(Collectors.toList()); // [1,2,3,4,5,6]

// Chained comparator in stream
employees.stream()
    .sorted(Comparator.comparing(Employee::department)
                      .thenComparingDouble(Employee::salary).reversed())
    .forEach(System.out::println);
```

---

## 5. Stream Collectors

```java
// groupingBy
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::department));

// groupingBy + counting
Map<String, Long> countByDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::department, Collectors.counting()));

// groupingBy + averaging
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::department,
             Collectors.averagingDouble(Employee::salary)));

// groupingBy + mapping
Map<String, List<String>> namesByDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::department,
             Collectors.mapping(Employee::name, Collectors.toList())));

// partitioningBy — splits into true/false
Map<Boolean, List<Employee>> partition = employees.stream()
    .collect(Collectors.partitioningBy(e -> e.salary() > 90_000));

// joining
String nameList = employees.stream()
    .map(Employee::name)
    .collect(Collectors.joining(", ", "[", "]"));
// [Alice, Bob, Charlie]

// toMap
Map<String, Double> nameToSalary = employees.stream()
    .collect(Collectors.toMap(Employee::name, Employee::salary));

// Duplicate key handling
Map<String, Double> safe = employees.stream()
    .collect(Collectors.toMap(
        Employee::department,
        Employee::salary,
        Double::sum));  // merge: sum salaries for same dept

// Statistics
DoubleSummaryStatistics stats = employees.stream()
    .mapToDouble(Employee::salary)
    .summaryStatistics();
stats.getMin(); stats.getMax(); stats.getAverage(); stats.getSum(); stats.getCount();
```

---

## 6. Optional

> Represents a value that may or may not be present. Avoids `null` checks and NPE.

```java
// Creating
Optional<String> present = Optional.of("Hello");        // throws NPE if null!
Optional<String> nullable = Optional.ofNullable(null);  // safe
Optional<String> empty    = Optional.empty();

// Checking & Getting
present.isPresent()     // true
present.isEmpty()       // false (Java 11)
present.get()           // "Hello" — throws NoSuchElementException if empty!

// Safe retrieval
present.orElse("default")                          // "Hello"
empty.orElse("default")                            // "default"
empty.orElseGet(() -> computeDefault())            // lazy evaluation
empty.orElseThrow(() -> new RuntimeException("!")) // throw if empty

// Conditional
present.ifPresent(System.out::println)             // print if present
present.ifPresentOrElse(
    v  -> System.out.println("Got: " + v),
    () -> System.out.println("Empty"));            // Java 9

// Transform
present.map(String::toUpperCase)                   // Optional["HELLO"]
present.filter(s -> s.length() > 3)               // Optional["Hello"]
present.flatMap(s -> Optional.of(s + "!"))         // Optional["Hello!"]

// Chaining — eliminates null checks
Optional.ofNullable(user)
    .map(User::getAddress)
    .map(Address::getCity)
    .map(City::getZipCode)
    .orElse("Unknown");
```

---

## 7. Java 9–21 Highlights

```java
// Java 9: Collection factory methods
List<String> list = List.of("a", "b", "c");       // immutable
Set<Integer> set  = Set.of(1, 2, 3);              // immutable
Map<String,Integer> map = Map.of("a", 1, "b", 2); // immutable

// Java 10: var — local type inference
var list = new ArrayList<String>();   // type inferred as ArrayList<String>
var map  = new HashMap<String, Integer>();

// Java 14: Records (standard in Java 16)
public record Point(int x, int y) {}  // auto: constructor, getters, equals, hashCode, toString
Point p = new Point(3, 4);
p.x();  p.y();  // accessor methods

// Java 15: Text Blocks
String json = """
    {
      "name": "Srivalli",
      "company": "FutureFrame Services"
    }
    """;

// Java 16: Pattern Matching instanceof
if (obj instanceof String s) {   // no explicit cast needed
    System.out.println(s.length());
}

// Java 17: Sealed Classes
public sealed interface Shape permits Circle, Rectangle {}
public record Circle(double radius) implements Shape {}
public record Rectangle(double w, double h) implements Shape {}

// Switch expression with sealed class — exhaustive, no default needed!
double area = switch (shape) {
    case Circle c    -> Math.PI * c.radius() * c.radius();
    case Rectangle r -> r.w() * r.h();
};

// Java 21: Virtual Threads
Thread.ofVirtual().start(() -> handleRequest());
```

---

## ❓ Interview Q&A

**Q: What is the difference between `map()` and `flatMap()`?**
> - `map()` — 1-to-1 transform, wraps result in stream element
> - `flatMap()` — 1-to-many, flattens the result into a single stream
```java
Stream.of("Hello World", "Java Streams")
    .map(s -> s.split(" "))          // Stream<String[]>   — 2 arrays
    .flatMap(Arrays::stream)         // Stream<String>     — 4 strings
    .collect(Collectors.toList());   // [Hello, World, Java, Streams]
```

**Q: What are intermediate vs terminal operations?**
> **Intermediate** = lazy, return another Stream: `filter`, `map`, `sorted`, `distinct`, `limit`  
> **Terminal** = eager, trigger pipeline, consume stream: `collect`, `forEach`, `count`, `reduce`, `findFirst`

**Q: What is `reduce()`?**
> Combines all elements into a single result using an accumulator function.
```java
int sum     = Stream.of(1,2,3,4,5).reduce(0, Integer::sum);      // 15
int product = Stream.of(1,2,3,4,5).reduce(1, (a,b) -> a*b);     // 120
```

**Q: What is a method reference? When do you use one?**
> Shorthand for a lambda that just calls an existing method. Use it when the lambda does nothing except delegate to a method. Makes code more readable.

**Q: What is `Optional` and why use it?**
> A container that may or may not hold a value. Forces callers to handle the "no value" case explicitly, making null handling safer and more expressive. Designed for **return types** — not for fields or method parameters.

---

*← [Module 05 — Multithreading](Module-05-Multithreading) | [Module 07 — Design Patterns →](Module-07-Design-Patterns)*
