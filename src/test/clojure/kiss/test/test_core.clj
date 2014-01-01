(ns kiss.test.test-core
  (:use clojure.test)
  (:use kiss.core))

(deftest basic-tests
  (is (== 1 1))
  (is (== 1 (kiss 1))))