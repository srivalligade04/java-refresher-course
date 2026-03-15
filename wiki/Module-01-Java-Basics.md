# 📘 Module 01 — Java Basics

> **Package:** `com.futureframeservices.javaRefresherCourse.basics`  
> **File:** `Module01_JavaBasics.java`  
> **Time:** ~30 minutes

---

## 📋 Topics Covered

- [Primitive Data Types](#1-primitive-data-types)
- [Wrapper Classes & Autoboxing](#2-wrapper-classes--autoboxing)
- [String — Immutability](#3-string--immutability)
- [StringBuilder vs StringBuffer](#4-stringbuilder-vs-stringbuffer)
- [Arrays](#5-arrays)
- [Type Casting](#6-type-casting)
- [Interview Q&A](#-interview-qa)

---

## 1. Primitive Data Types

| Type | Size | Range | Default |
|------|------|-------|---------|
| `byte` | 1 byte | -128 to 127 | 0 |
| `short` | 2 bytes | -32,768 to 32,767 | 0 |
| `int` | 4 bytes | -2³¹ to 2³¹-1 | 0 |
| `long` | 8 bytes | -2⁶³ to 2⁶³-1 | 0L |
| `float` | 4 bytes | ~7 decimal digits | 0.0f |
| `double` | 8 bytes | ~15 decimal digits | 0.0d |
| `char` | 2 bytes | 0 to 65,535 (Unicode) | `'\u0000'` |
| `boolean` | 1 bit | true / false | false |

> 💡 **char is 2 bytes in Java** (unlike C/C++ where it's 1 byte) because Java uses Unicode (UTF-16).

---

## 2. Wrapper Classes & Autoboxing

```java
// Autoboxing: primitive → Wrapper
int x = 5;
Integer y = x;          // int → Integer (compiler adds Integer.valueOf(x))

// Unboxing: Wrapper → primitive
int z = y;              // Integer → int (compiler adds y.intValue())

// ⚠️ Integer Cache — IMPORTANT for interviews!
Integer a = 127;
Integer b = 127;
System.out.println(a == b);   // TRUE  — cached in range -128 to 127

Integer c = 128;
Integer d = 128;
System.out.println(c == d);   // FALSE — new objects above 127
System.out.println(c.equals(d)); // TRUE ✅ — always use equals()

// ⚠️ NPE Risk with unboxing
Integer nullVal = null;
int bad = nullVal;  // NullPointerException!
```

> 🔑 **Rule:** Always use `.equals()` for `Integer` comparison, never `==` unless you're sure it's in the cache range.

---

## 3. String — Immutability

**Why is String immutable?**
1. **Security** — used as keys in HashMap, DB connection strings
2. **Thread safety** — multiple threads can share safely
3. **String Pool** — JVM can cache and reuse strings
4. **HashCode caching** — computed once, stored forever

```java
String s1 = "Java";              // String Pool
String s2 = "Java";              // Same pool reference
String s3 = new String("Java");  // New Heap object (avoid!)

s1 == s2        // true  (same pool reference)
s1 == s3        // false (different object)
s1.equals(s3)   // true  ✅ content equal
```

### Most-Used String Methods

```java
String s = "  Hello, FutureFrame!  ";

s.length()              // 22
s.trim()                // "Hello, FutureFrame!"
s.strip()               // same as trim (Java 11, handles Unicode whitespace)
s.toUpperCase()         // "  HELLO, FUTUREFRAME!  "
s.toLowerCase()         // "  hello, futureframe!  "
s.contains("Future")   // true
s.startsWith("  He")   // true
s.endsWith("!  ")      // true
s.replace("Frame","")  // "  Hello, Future!  "
s.indexOf("Frame")     // 14
s.substring(9, 20)     // "FutureFrame"
s.split(",")           // ["  Hello", " FutureFrame!  "]
s.charAt(7)            // 'H'
s.isEmpty()            // false
s.isBlank()            // false (Java 11)

// Conversions
String.valueOf(42)           // "42"
Integer.parseInt("42")       // 42
Double.parseDouble("3.14")   // 3.14
"hello".toCharArray()        // ['h','e','l','l','o']

// String.format
String.format("%-10s | %5d | %.2f", "Alice", 90, 4.5)
// "Alice      |    90 | 4.50"
```

---

## 4. StringBuilder vs StringBuffer

| Feature | `String` | `StringBuilder` | `StringBuffer` |
|---------|----------|-----------------|----------------|
| Mutable? | ❌ No | ✅ Yes | ✅ Yes |
| Thread-safe? | ✅ Yes | ❌ No | ✅ Yes (synchronized) |
| Performance | Slowest (new obj per concat) | Fastest | Medium |
| Use when | Immutable values | Single-thread loops | Multi-thread concat |

```java
// ❌ BAD — creates 1000 String objects in heap
String result = "";
for (int i = 0; i < 1000; i++) result += i;

// ✅ GOOD — one mutable buffer
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) sb.append(i);
String result = sb.toString();

// StringBuilder methods
StringBuilder sb = new StringBuilder("Hello");
sb.append(" World");       // "Hello World"
sb.insert(5, ",");         // "Hello, World"
sb.delete(7, 12);          // "Hello, "
sb.reverse();              // " ,olleH"
sb.replace(0, 2, "XX");    // "XX,olleH"
sb.length();               // current length
sb.charAt(0);              // 'X'
sb.indexOf("olleH");       // 3
```

---

## 5. Arrays

```java
// Declaration and initialization
int[] arr = new int[5];               // [0, 0, 0, 0, 0]
int[] arr2 = {5, 3, 1, 4, 2};
String[] names = {"Alice", "Bob", "Charlie"};

// Key: length is a FIELD, not a method
arr.length      // ✅
arr.length()    // ❌ compile error

// java.util.Arrays utility class
Arrays.sort(arr2);                     // [1, 2, 3, 4, 5] — in place
Arrays.toString(arr2);                 // "[1, 2, 3, 4, 5]"
Arrays.fill(arr, 7);                   // [7, 7, 7, 7, 7]
Arrays.copyOf(arr2, 3);               // [1, 2, 3]
Arrays.copyOfRange(arr2, 1, 4);       // [2, 3, 4]
Arrays.binarySearch(arr2, 3);         // 2 (array must be sorted first!)
Arrays.equals(arr2, new int[]{1,2,3,4,5}); // true

// 2D arrays
int[][] matrix = new int[3][3];
int[][] matrix2 = {{1,2,3},{4,5,6},{7,8,9}};
Arrays.deepToString(matrix2);  // "[[1, 2, 3], [4, 5, 6], [7, 8, 9]]"
```

---

## 6. Type Casting

```java
// Widening — automatic, no data loss
int i = 100;
long l = i;       // int → long ✅ automatic
double d = l;     // long → double ✅ automatic

// Narrowing — explicit cast, may lose data
double pi = 3.99;
int piInt = (int) pi;   // 3 — TRUNCATES (not rounds!)

// Overflow example
int big = 300;
byte b = (byte) big;    // 44 — overflow wraps around!
```

---

## ❓ Interview Q&A

**Q: Why is String immutable in Java?**
> Security, thread safety, String Pool caching, and hashCode caching.

**Q: What is the Integer cache range?**
> `-128 to 127`. Integers in this range are cached — `==` returns `true`. Outside this range, `==` returns `false`. **Always use `.equals()` for Integer comparison.**

**Q: What is the difference between `String`, `StringBuilder`, and `StringBuffer`?**
> `String` = immutable. `StringBuilder` = mutable, NOT thread-safe (use in single-thread). `StringBuffer` = mutable, thread-safe but slower (legacy, rarely used now).

**Q: What is autoboxing? What risk does it carry?**
> Autoboxing = automatic conversion between primitive and wrapper. Risk = `NullPointerException` when unboxing a `null` wrapper.

**Q: What is the default value of local variables in Java?**
> Local variables have **NO default** — must be initialized before use or compiler error. Instance variables default to `0`, `false`, `null`.

---

*← [Home](Home) | [Module 02 — OOP →](Module-02-OOP)*
