(ns kiss.test.test-examples
  (:use clojure.test)
  (:use kiss.core)
  (:use [mikera.cljutils error]) 
  (:import [kiss.lang Expression Environment] ))

(deftest test-defines
  (is (= [1 2 3]
         (kiss
           (do
             (def g (fn [x] (clojure.core/inc x)))
             (def map (fn [f xs] (clojure.core/map f xs)))
             (map g [0 1 2]))))))