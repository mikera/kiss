(ns kiss.demo.example
  (:use kiss.core))

;; ====================================================================
;; Kiss works with immutable Environments. Let's play with one
;; ====================================================================

;; define an environment to work with
(def env (environment {foo 1, 
                       bar 3,
                       f clojure.core/inc
                       baz (clojure.core/+ foo bar)}))

;; ================================================= 
;; Environments are specialised Clojure maps

(map? env)
; => true

(env 'foo)
; => 1

(keys env)
; => (baz foo bar f)

(class env)
; => kiss.lang.Environment

;; ================================================= 
;; Expression results are computed as values

(env 'f)
;; => #<core$inc clojure.core$inc@4648acd3>    
;;    i.e. a Clojure function, looked up via the var #'clojure.core/inc

(env 'baz)
;; => 4
;;    i.e. the expression (+ foo bar) has been computed


;; =================================================
;; Changing an environment
;; - leaves original environment unchanged
;; - causes update of dependent Expressions (!!!!!!)

(let [env1 env
      env2 (assoc env 'foo 2)]
  [(env1 'foo) (env2 'foo)])
; => [1 2]


;; ====================================================================
;; Kiss code can be executed against any envirnment
;; ====================================================================

(kiss env (f foo))
;; => 2

;; ================================================= 
;; Clojure functions can also be used

(kiss env (clojure.core/range bar))
;; => (0 1 2)