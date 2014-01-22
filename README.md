Kiss
====

**K**iss is **I**mmutable, **S**tatically compiled and **S**ymbiotic (with Clojure).

This is an **EXPERIMENT** in programming language design. Who knows where it will go?

![Kiss!](https://raw.github.com/mikera/kiss/master/src/main/resources/kiss.png)

## Objectives

 - Productivity like **Clojure**
 - Performance like **Java**
 - Type safety like **Haskell**


## Example

```clojure
(ns my.clojure.project
  (:use kiss.core))
  
(defn call-kiss-from-clojure []
  (kiss 
    (str "Hello from Kiss!")))
```

For more working code examples see the [examples.clj](https://github.com/mikera/kiss/blob/master/src/test/clojure/kiss/demo/example.clj) namespace.

## Solution

Kiss takes the following approach to language design:

 - **Immutable environments** - all Kiss code is compiled against a specific immutable environment, potentially creating a new immutable environment (with any definitions updated). Environments are *first class* and represent the complete state of the code base, making them highly amenable to static analysis.
 - **Statically compiled** - Kiss objects all have a static type, and the compiler will use these to generate decent bytecode. Types are *first class* and can be used both at compile time and runtime. Exact features of the type system are still to be determined, but at a minimum will take full advantage of all JVM types.
 - **Symbiotic with Clojure** - Kiss is bootstrapped on top of Clojure, and designed to be used within a Clojure environment. You can call Clojure functions / macros transparently, and Kiss functions will be IFn instances that are equally usable from Clojure. Kiss will just use the Clojure reader and syntax directly. 

## See the Wiki for more details

 - [Detailed Rationale](https://github.com/mikera/kiss/wiki/Rationale)
 - [Differences with Typed Clojure](https://github.com/mikera/kiss/wiki/Differences-with-Typed-Clojure)
 - [Implementation Ideas](https://github.com/mikera/kiss/wiki/Implementation-Ideas)

