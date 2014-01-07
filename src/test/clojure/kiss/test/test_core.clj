(ns kiss.test.test-core
  (:use clojure.test)
  (:use kiss.core)
  (:import [kiss.lang Expression Environment] ))

(deftest basic-tests
  (is (== 1 1))
  (is (== 1 (keval 1))))

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

