(ns kiss.test.test-clojure
  (:use clojure.test)
  (:use kiss.core)
  (:use [mikera.cljutils error]) 
  (:import [kiss.lang Expression Environment] ))

(deftest number-tests
  (is (= 5 (kiss (clojure.core/+ 2 3)))))

;; TODO: figure out how to make Clojure macros work
;;(deftest macro-tests
;;  (is (= 2 (kiss (clojure.core/or 2 3)))))

