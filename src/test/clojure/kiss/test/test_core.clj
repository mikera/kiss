(ns kiss.test.test-core
  (:use clojure.test)
  (:use kiss.core)
  (:import [kiss.lang Expression Environment] ))

(deftest basic-tests
  (is (== 1 1))
  (is (== 1 (kiss 1))))

(deftest environment-tests
  (let [e (empty-environment)]
    (is (empty? (seq e)))
    (let [e (assoc e 'foo 1)]
      (is (== 1 (e 'foo))))))

(deftest test-analyser
  (let [^Environment e (empty-environment)
        ^Expression ex (analyse 1)]
    (is (instance? Expression ex))
    (is (== 1 (.eval ex e)))))

(deftest test-lookup
  (let [^Environment e (empty-environment)
        e (assoc e 'foo 10) 
        ^Expression ex (analyse 'foo)]
    (is (instance? Expression ex))
    (is (== 10 (.eval ex e)))
    (is (== 10 (kiss e foo)))
    (is (== 17 (kiss e (let [foo 17] foo))))))

(deftest test-let
  (is (== 13 (kiss (let [a 13] a)))))

(deftest test-clojure-fn
  (is (== 3 (kiss (clojure.core/+ 1 2)))))

(deftest test-lambda
  (is (== 3 (kiss ((fn [x] 3) 2)))))
