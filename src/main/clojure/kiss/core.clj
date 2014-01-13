(ns kiss.core
  (:require [kiss.compiler :as compiler])
  (:import [kiss.lang Environment Analyser Expression])
  (:use [mikera.cljutils error]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

;; EXPERIMENTAL - API subject to change!!!
;;
;; Don't rely on any of this stuff being here in the future

(defn empty-environment
  "Returns an empty kiss environment"
  ([]
    Environment/EMPTY))

(defn environment
  "Creates an environment with the given symbol / value mappings"
  ([]
    (empty-environment))
  ([mappings]
    (reduce (fn [e [k v]] (assoc e k v)) (empty-environment) mappings)))

(defn analyse
  ([form]
    (Analyser/analyse form)))

(defn optimise
  ([form]
    (kiss.lang.Compiler/compile form)))

(defn kmerge
  "Merge kiss environments, returning a new environment"
  (^Environment [^Environment a] a)
  (^Environment [^Environment a ^Environment b] (TODO))
  (^Environment [^Environment a ^Environment b & more ]
    (reduce kmerge (kmerge a b) more)))

(defn result 
  "Returns the latest evaluation result from a given kiss Environment"
  ([^Environment e]
    (.getResult e)))

(defmacro kiss
  "Compiles and executes kiss code in the given environment, returning the result"
  ([body]
    `(let [env# Environment/EMPTY
           ex# (optimise (quote ~body))]
       (.eval ex# env#)))
  ([env body]
    `(let [env# ~env
           ex# (optimise (quote ~body))]
       (.eval ex# env#))))

(defmacro kisse
  "Compiles and executes kiss code in the given environment, returning the updated environment"
  ([body]
    `(let [env# Environment/EMPTY
           ex# (optimise (quote ~body))]
       (.compute ex# env#)))
  ([env body]
    `(let [env# ~env
           ex# (optimise (quote ~body))]
       (.compute ex# env#))))
