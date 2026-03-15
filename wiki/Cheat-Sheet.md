# 📄 Java Cheat Sheet

> Quick reference card — print this and keep it on your desk!

---

## Primitives
```
byte(1B) < short(2B) < int(4B) < long(8B) < float(4B) < double(8B) < char(2B)
Integer cache: -128 to 127 → == works. Outside → use .equals()!
Default values: int=0, double=0.0, boolean=false, char='\u0000', Object=null
Local variables: NO default — must initialize or compiler error!
```

---

## String Methods
```java
s.length()           s.charAt(i)          s.substring(a,b)
s.indexOf(c)         s.lastIndexOf(c)     s.contains(x)
s.startsWith(x)      s.endsWith(x)        s.matches(regex)
s.replace(old,new)   s.replaceAll(regex,new)
s.split(",")         s.split(",", limit)
s.trim()             s.strip()            s.isBlank()    // Java 11
s.toLowerCase()      s.toUpperCase()      s.toCharArray()
s.equals(x)          s.equalsIgnoreCase(x)
s.compareTo(x)       s.compareToIgnoreCase(x)
String.valueOf(42)   Integer.parseInt("42")   Double.parseDouble("3.14")
String.format("%-10s %5d %.2f", str, num, dbl)
```

---

## Collections Quick Pick
```
List   → ArrayList  (reads, general use — 90% of cases)
         LinkedList  (frequent add/remove at head)
Set    → HashSet        (O(1) contains, no order)
         LinkedHashSet  (insertion order)
         TreeSet        (sorted, O(log n))
Map    → HashMap        (O(1) get/put, no order)
         LinkedHashMap  (insertion order)
         TreeMap        (sorted keys, O(log n))
Queue  → ArrayDeque     (stack or queue, faster than Stack/LinkedList)
         PriorityQueue  (min-heap by default)
Thread → ConcurrentHashMap, CopyOnWriteArrayList
```

---

## Time Complexity
```
              get     add      remove   contains
ArrayList     O(1)    O(1)amrt  O(n)     O(n)
LinkedList    O(n)    O(1)      O(1)*    O(n)
HashMap       O(1)    O(1)      O(1)     O(1)
TreeMap       O(lgn)  O(lgn)    O(lgn)   O(lgn)
HashSet       -       O(1)      O(1)     O(1)
TreeSet       -       O(lgn)    O(lgn)   O(lgn)
PriorityQueue O(1)pk  O(lgn)    O(lgn)   O(n)
```

---

## Stream Pipeline
```java
collection.stream()           // or .parallelStream()
  // Intermediate (lazy, return Stream)
  .filter(pred)               // keep matching
  .map(fn)                    // 1-to-1 transform
  .flatMap(fn)                // 1-to-many + flatten
  .sorted() / .sorted(comp)
  .distinct()                 // remove duplicates
  .limit(n)                   // first n
  .skip(n)                    // skip first n
  .peek(consumer)             // debug
  // Terminal (eager, trigger pipeline)
  .collect(Collectors.toList/toSet/toMap/groupingBy/joining)
  .forEach(consumer)
  .count()                    // long
  .findFirst() / .findAny()   // Optional<T>
  .min(comp) / .max(comp)     // Optional<T>
  .anyMatch() .allMatch() .noneMatch()  // boolean
  .reduce(identity, accumulator)        // T
  .mapToInt(fn).sum() / .average() / .summaryStatistics()
```

---

## Collectors Reference
```java
Collectors.toList()
Collectors.toSet()
Collectors.toUnmodifiableList()              // Java 10
Collectors.toMap(keyFn, valueFn)
Collectors.toMap(keyFn, valueFn, mergeFn)   // handle duplicates
Collectors.groupingBy(classifier)
Collectors.groupingBy(classifier, downstream)
Collectors.partitioningBy(predicate)
Collectors.joining(delim, prefix, suffix)
Collectors.counting()
Collectors.summarizingInt/Double
Collectors.averagingInt/Double
Collectors.mapping(fn, downstream)
```

---

## Functional Interfaces
```java
Function<T,R>    T→R        .apply(T)   .andThen(f) .compose(f)
Consumer<T>      T→void     .accept(T)  .andThen(c)
Supplier<T>      ()→T       .get()
Predicate<T>     T→boolean  .test(T)    .and(p) .or(p) .negate()
BiFunction<T,U,R>  T,U→R    .apply(T,U)
UnaryOperator<T>   T→T      (extends Function<T,T>)
BinaryOperator<T>  T,T→T    Integer::sum, Math::max
```

---

## Optional
```java
Optional.of(v)                // NPE if null
Optional.ofNullable(v)        // safe
Optional.empty()

.isPresent() .isEmpty()       // check
.get()                        // ⚠️ throws if empty!
.orElse(default)
.orElseGet(supplier)
.orElseThrow(exceptionFn)
.ifPresent(consumer)
.ifPresentOrElse(consumer, emptyAction)
.map(fn) .flatMap(fn) .filter(pred)
```

---

## Exception Hierarchy
```
Throwable
├── Error (don't catch!)
│     OutOfMemoryError, StackOverflowError
└── Exception
      ├── Checked (must handle, compile error if not)
      │     IOException, SQLException, FileNotFoundException, ParseException
      └── RuntimeException (optional)
            NullPointerException, ArrayIndexOutOfBoundsException
            ClassCastException, IllegalArgumentException
            IllegalStateException, NumberFormatException
            ConcurrentModificationException
```

---

## Thread Safety
```
Unsafe:   ArrayList, HashMap, StringBuilder
Safe:     Vector, Hashtable, StringBuffer  (legacy — avoid!)
Prefer:   CopyOnWriteArrayList             (read-heavy)
          ConcurrentHashMap                (map operations)
          AtomicInteger/Long/Reference     (counters)
          synchronized block               (critical sections)
          ReentrantLock                    (advanced locking)
          volatile                         (simple flags only)
```

---

## Spring Boot Annotations
```java
// Class-level
@SpringBootApplication          // @Configuration + @EnableAutoConfiguration + @ComponentScan
@RestController                 // @Controller + @ResponseBody
@Controller @Service @Repository @Component   // bean registration
@Configuration @Bean            // programmatic bean creation
@Entity @Table @Id @GeneratedValue            // JPA

// Method-level
@GetMapping @PostMapping @PutMapping @DeleteMapping @PatchMapping
@Transactional @Scheduled @EventListener

// Parameter-level
@PathVariable @RequestParam @RequestBody @RequestHeader
@Autowired @Qualifier @Value @Primary

// Class-level (validation)
@Valid @Validated
@NotNull @NotBlank @NotEmpty @Size @Min @Max @Email @Pattern @Positive
```

---

## Java Version Timeline
```
Java 8  (2014) → Lambdas, Streams, Optional, Default methods, Date/Time API ⭐
Java 9  (2017) → Modules, List.of(), Map.of(), private interface methods
Java 10 (2018) → var (local variable type inference)
Java 11 (2018) → String.isBlank/lines/strip, HTTP Client, var in lambdas  [LTS] ⭐
Java 14 (2020) → Records (preview), Switch expressions (standard)
Java 15 (2020) → Text blocks (standard)
Java 16 (2021) → Records (standard), instanceof pattern matching
Java 17 (2021) → Sealed classes, strong encapsulation  [LTS] ⭐
Java 21 (2023) → Virtual Threads, Record patterns, Sequenced collections  [LTS] ⭐
```

---

## Design Patterns One-Liner
```
Singleton   → one instance (static inner class or enum)
Factory     → create without knowing concrete type
Builder     → step-by-step construction (fluent API)
Observer    → notify subscribers on state change
Strategy    → swap algorithms at runtime
Decorator   → add behavior without changing original class
Proxy       → controlled/lazy access to another object
Adapter     → make incompatible interfaces work together
```

---

## Common Algorithms in Interviews
```java
// Sort array of objects
Arrays.sort(arr, Comparator.comparingInt(o -> o.value));

// Binary search (sorted array only!)
int idx = Arrays.binarySearch(arr, target);

// Reverse array in-place
for (int l=0, r=arr.length-1; l<r; l++,r--) {int t=arr[l];arr[l]=arr[r];arr[r]=t;}

// Count chars
Map<Character,Integer> freq = new HashMap<>();
for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);

// Two-pointer pattern
int l = 0, r = arr.length - 1;
while (l < r) { /* process */ l++; r--; }

// Sliding window
int max = 0, sum = 0;
for (int i = 0; i < k; i++) sum += arr[i]; max = sum;
for (int i = k; i < arr.length; i++) { sum += arr[i] - arr[i-k]; max = Math.max(max,sum); }
```

---

*← [Interview Q&A Master](Interview-QnA-Master) | [How to Run →](How-To-Run)*
