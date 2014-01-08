Kiss
====

**K**iss is **I**mmutable, **S**tatically compiled and **S**ymbiotic (with Clojure).

This is an EXPERIMENT in programming language design. Who knows where it will go?

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

## Rationale

I've found Clojure to be a beautiful language to work with. In fact, it's probably my favourite language right now.

However it has, in my humble opinion, two notable weaknesses:

 - The lack of static typing
 - A tricky mutable namespace system

I believe these issues can be addressed without compromising the otherwise excellent design of Clojure. So Kiss is an experiement to try and create a mini-language that addresses these two issues, while otherwise being pretty much exactly like (and bootstrapped on top of!) the Clojure that we know and love.

## Solution

Kiss aims to take the following approach:

 - **Immutable environments** - all Kiss code is compiled against a specific immutable environment, creating a new immutable environment (with any definitions updated). 
 - **Statically compiled** - Kiss objects will all have a static type, and the compiler will use these to generate decent bytecode. Exact features of the type system to be determined, but at a minimum will take full advantage of all JVM types.
 - **Symbiotic with Clojure** - Kiss will be bootstraped in Clojure, and designed to be used within Clojure. You can call Clojure functions / macros transparently, and Kiss functions will be IFn instances that are equally usable from Clojure. Kiss will just use the Clojure reader and syntax directly. 

## See the Wiki for more details

 - [Detailed Rationale](https://github.com/mikera/kiss/wiki/Rationale)
 - [Differences with Typed Clojure](https://github.com/mikera/kiss/wiki/Differences-with-Typed-Clojure)
 - [Implementation Ideas](https://github.com/mikera/kiss/wiki/Implementation-Ideas)

