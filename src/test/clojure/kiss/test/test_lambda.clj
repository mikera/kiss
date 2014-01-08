(ns kiss.test.test-lambda
  (:use clojure.test)
  (:use kiss.core))

(deftest test-clojure-iterop
  (testing "lambdas produce Clojure fns"
    (let [kfn (kiss (fn [] 3))]
      (is (== 3 (kfn))))
    (let [kfn2 (kiss (fn [x y] (clojure.core/+ x y)))]
      (is (== 3 (kfn2 1 2)))))
  (testing "Clojure functions work in lambdas"
    (is (== 3 (kiss ((fn [] (clojure.core/+ 1 2))))))))