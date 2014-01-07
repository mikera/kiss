(ns kiss.compiler)

(defn clojure-compile
  "Converts kiss code to an executable Clojure form"
  [& body]
  `(do ~@body))