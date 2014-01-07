(ns kiss.compiler)

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn clojure-compile
  "Converts kiss code to an executable Clojure form"
  [& body]
  `(do ~@body))