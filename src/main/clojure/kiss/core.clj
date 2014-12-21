(ns kiss.core
  (:require [kiss.compiler :as compiler])
  (:refer-clojure :exclude [compile])
  (:import [kiss.lang RT Environment Analyser Expression KFn Result])
  (:use [mikera.cljutils error]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

;; EXPERIMENTAL - API subject to change!!!
;;
;; Don't rely on any of this stuff being here in the future

(defmacro environment
  "Creates an Environment with the given Symbol -> Expression mappings."
  ([]
    `RT/ENVIRONMENT)
  ([mappings]
    `(environment RT/ENVIRONMENT ~mappings))
  ([env mappings]
    `(reduce 
       (fn [^Environment e# [^Symbol k# v#]] (.define e# k# (analyse v#))) 
       ~env 
       (quote ~mappings))))

(defn empty-environment
  "Returns an empty Kiss Environment"
  ([]
    Environment/EMPTY))

(defn new-environment
  "Returns an empty Kiss Environment"
  ([]
    (environment)))

(defn analyse
  "Analyse a form, resulting a Kiss Expression AST"
  (^Expression [form]
    (analyse (environment) form))
  (^Expression [^Environment env form]
    (Analyser/analyse env form)))

(defn compile
  (^KFn [form]
    (compile (environment) form))
  (^KFn [^Environment env form]
    (let [ex (analyse env form)]
      (kiss.lang.Compiler/compile env ex))))




(defn kmerge
  "Merge Kiss Environments, returning a new Environment"
  (^Environment [^Environment a] a)
  (^Environment [^Environment a ^Environment b] (.merge a b))
  (^Environment [^Environment a ^Environment b & more ]
    (reduce kmerge (kmerge a b) more)))

(defn result 
  "Returns the latest evaluation result from a given Kiss Environment"
  ([^Result e]
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
    `(kisst (environment) ~body))
  ([env body]
    `(let [env# ~env
           ex# (compile env# (quote ~body))]
       (.getReturnType ex#)))) 

(defn kisse* 
  ([^Environment env form]
    (let [body (analyse env form)]
      (.getEnvironment (.interpret body env)))))

(defmacro kisse
  "Compiles and executes Kiss code in the given Environment, returning the new Environment."
  ([body]
    `(kisse (environment) ~body))
  ([env body]
    `(let [env# ~env
           body# (quote ~body)]
       (kisse* env# body#))))

(def kiss-repl-env (atom (environment)))

(defn kissify 
  "TODO: figure out how to hack the REPL"
  ([]
    (alter-var-root #'clojure.core/eval 
                  (fn [form] 
                    (swap! kiss-repl-env (fn [env] (kisse* env form)))
                    (.getResult ^Result @kiss-repl-env)))))
