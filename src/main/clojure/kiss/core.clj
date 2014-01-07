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

(defn analyse
  (^Expression [form]
    (Analyser/analyse form)))

(defn kmerge
  "Merge kiss environments, returning a new environment"
  (^Environment [^Environment a] a)
  (^Environment [^Environment a ^Environment b] (TODO))
  (^Environment [^Environment a ^Environment b & more ]
    (reduce kmerge (kmerge a b) more)))

(defmacro kiss
  "Compiles and executes kiss code in the given environment, returning the result"
  ([body]
    `(let [env# Environment/EMPTY
           ex# (analyse (quote ~body))]
       (.eval ex# env#)))
  ([env & body]
    `(let [env# ~env
           ex# (analyse (quote ~body))]
       (.eval ex# env#))))
