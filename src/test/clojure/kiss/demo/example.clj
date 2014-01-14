(ns kiss.demo.example
  (:use kiss.core))

;; ====================================================================
;; Kiss works with immutable Environments. Let's play with one
;; ====================================================================

;; define an environment to work with
(def env (environment {foo 1, 
                       bar 2,
                       f clojure.core/inc
                       baz (clojure.core/+ foo bar)}))

;; === Environments are specialised Clojure maps

(env 'foo)
; => 1

(keys env)
; => (baz foo bar f)

(class env)
; => kiss.lang.Environment

;; === Expression results are computed as values

(env 'f)
;; => #<core$inc clojure.core$inc@4648acd3>    
;;    i.e. a Clojure function, looked up via the var #'clojure.core/inc

(env 'baz)
;; => 3
;;    i.e. the expression (+ foo bar) has been computed
