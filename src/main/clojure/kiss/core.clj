(ns kiss.core
  (:require [kiss.compiler :as compiler]))

(defmacro kiss
  "Compiles and executes kiss code"
  [& body]
  (apply compiler/clojure-compile body))