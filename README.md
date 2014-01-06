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

### Why we want static typing

There are many reasons to want the benefits of static typing, most notably:

 - **Performance** - with knowledge of static types, the compiler can eliminate runtime checks and produce more efficient code. Sometimes performance matters, sometimes it doesn't - but it's better to have the performance there for when you need it.
 - **Reliability** - static typing will tell you about many bugs at compile time. If your code doesn't type check, then it probably isn't logically correct (the converse is of course not true: code that type checks can still be very incorrect, although at least you know that you have eliminated a large class of typing bugs).
 - **Refactoring** - refactoring dynamically typed code is much harder. In part this is because you don't find out about errors until much later (at runtime or at least test time). In addition, static typing makes it much easier to create useful code analysis and refactoring tools.
 - **Less work** - To compensate for reliability and flexibility issues, programmers in dynamic langauges often write large test suites that do something equivalent to type checking of various cases (but such tests are still probably nowhere near exhaustive). There is also a tendency to create various schema lannguages / DSLs. With static typing, the compiler just does all this work for you, saving considerable effort.

All of the above can be worked around in various ways, but it's generally messy and unidiomatic to do so.

### Why we want immutable namespaces / environments

Clojure is founded on the idea that **immutable data is good**. Indeed, the use of immutable data (and the functional programming style that this enables) is one of the most compelling features of the language.

So why are namespaces mutable? Do they need to be, or is this just an accident of design derived from Clojure's Lisp heritage?

Mutable namespaces present a number of significant problems:

 - **Performance** is impacted by the fact that every single Clojure function call has to go through dynamic var lookups, just in case the var has changed. 99.99999% of the type this isn't true, but it's still a significant cost that you pay on *every function call in Clojure*
 - **Tools are hard to get right**: Code analysis / compilers / other code-aware tools are very hard to get right, because they can assume very little about the *state of namespaces*. Namespaces can be mutated arbitrarily, with no transactional protection.
 - **Circular dependencies** are very tricky in Clojure, in a large part because namespaces are constructed in an incremental, imperative style. Other languages which take a declarative approach to code can handle circular dependencies just fine (even Java!).


## Solution

Kiss aims to take the following approach:

 - **Immutable environments** - all Kiss code is compiled against a specific immutable environment, creating a new immutable environment (with any definitions updated). 
 - **Statically compiled** - Kiss objects will all have a static type, and the compiler will use these to generate decent bytecode. Exact features of the type system to be determined, but at a minimum will take full advantage of all JVM types.
 - **Symbiotic with Clojure** - Kiss will be bootstraped in Clojure, and designed to be used within Clojure. You can call Clojure functions / macros transparently, and Kiss functions will be IFn instances that are equally usable from Clojure. Kiss will just use the Clojure reader and syntax directly. 

### Implementation ideas

 - Kiss environments will **look like maps to Clojure code**, specifically maps from symbols to values (internally the environment will maintain more data, e.g. the types of each value)
 - There will be a `kiss` macro to execute Kiss code easily within Clojure source. This will probably be the normal way of using Kiss
 - Kiss will keep a **dependency graph** between compiled code in immutable environments, If you redefine something, this will enable all dependent code in the environment to be updated. This will recreate the "dynamic redefinition" behaviour that is familiar to Clojure users, without requiring full recompilations
 - It will be possible to define something with a missing dependency: this will create a **"deferred dependency"**. This will enable modules / units of code to be loaded independently, while still allowing circular references to be resolved later. Actually trying to evaluate something that is a deferred dependency will be an error of course.
 - It should be possible to `merge` Kiss environments. This could be useful for tasks like reconfiguring a module dynamically etc.
 - It should be possible to `fork` Kiss environments to create independent instances from the same base environment. Structural sharing should ensure that this is highly efficient
 - Restricted Kiss environments should work as an effective sandbox: there will be no escape from the sandbox except via definitions that already exist in the environment.
 - Kiss environments are independent, with no inherrent global state. This will make them practical for running e.g. isolated instances of applcation servers.
