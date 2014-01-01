(ns kiss.core)

(defmacro kiss
  "Compiles and executes kiss code"
  [& body]
  `(do ~@body))