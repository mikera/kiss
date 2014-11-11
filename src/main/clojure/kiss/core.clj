(ns kiss.core
  (:require [kiss.compiler :as compiler])
  (:refer-clojure :exclude [compile])
  (:import [kiss.lang Environment Analyser Expression KFn])
  (:use [mikera.cljutils error]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

;; EXPERIMENTAL - API subject to change!!!
;;
;; Don't rely on any of this stuff being here in the future

(defn empty-environment
  "Returns an empty Kiss Environment"
  ([]
    Environment/EMPTY))

(defn analyse
  "Analyse a form, resulting a Kiss Expression AST"
  (^Expression [form]
    (analyse Environment/EMPTY form))
  (^Expression [^Environment env form]
    (Analyser/analyse env form)))

(defn compile
  (^KFn [form]
    (compile Environment/EMPTY form))
  (^KFn [^Environment env form]
    (let [ex (analyse env form)]
      (kiss.lang.Compiler/compile env ex))))

(defmacro environment
  "Creates an Environment with the given Symbol -> Expression mappings."
  ([]
    `Environment/EMPTY)
  ([mappings]
    `(environment Environment/EMPTY ~mappings))
  ([env mappings]
    `(reduce 
       (fn [^Environment e# [^Symbol k# v#]] (.define e# k# (analyse v#))) 
       ~env 
       (quote ~mappings))))


(defn kmerge
  "Merge Kiss Environments, returning a new Environment"
  (^Environment [^Environment a] a)
  (^Environment [^Environment a ^Environment b] (.merge a b))
  (^Environment [^Environment a ^Environment b & more ]
    (reduce kmerge (kmerge a b) more)))

(defn result 
  "Returns the latest evaluation result from a given Kiss Environment"
  ([^Environment e]
    (.getResult e)))

(defmacro kiss
  "Compiles and executes Kiss code in the given Environment, returning the result"
  ([body]
    `(let [env# (environment)]
       (kiss env# ~body)))
  ([env body]
    `(let [env# ~env
           ex# (compile env# (quote ~body))]
       (ex#))))

(defmacro kisst
  "Returns the type of a given expression, without executing it."
  ([body]
    `(kisst Environment/EMPTY ~body))
  ([env body]
    `(let [env# ~env
           ex# (compile env# (quote ~body))]
       (.getReturnType ex#)))) 

(defmacro kisse
  "Compiles and executes Kiss code in the given Environment, returning the updated Environment.

   The last result can be accessed if needed via the 'result' function."
  ([body]
    `(kisse Environment/EMPTY ~body))
  ([env body]
    `(let [env# ~env
           ex# (analyse env# (quote ~body))]
       (.interpret ex# env#))))
