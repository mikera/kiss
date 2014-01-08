(ns kiss.test.test-lambda
  (:use clojure.test)
  (:use [mikera.cljutils error])
  (:use kiss.core))

(deftest test-lambda-application
  (testing "Apply a lambda to select a single argument"
    (is (== 4 (kiss ((fn [x y] x) 4 5))))))

(deftest test-higher-order
  (testing "Higher order functions returning a lambda"
    (is (== 4 (kiss (((fn [f] (fn [g] 4)) 5) 6)))))
  (testing "Higher order functions returning a lambda, overriding lexical params"
    (is (== 4 (kiss (((fn [f] (fn [f] 4)) 5) 6))))))

(deftest test-clojure-iterop
  (testing "Kiss lambdas produce Clojure fns"
    (let [kfn (kiss (fn [] 3))]
      (is (== 3 (kfn))))
    (let [kfn2 (kiss (fn [x y] (clojure.core/+ x y)))]
      (is (== 3 (kfn2 1 2)))))
  (testing "Clojure functions work in Kiss lambdas"
    (is (== 3 (kiss ((fn [] (clojure.core/+ 1 2))))))))

(deftest test-arity-error
  (testing "Arity errors result in an exception"
    (is (error? (kiss ((fn [] 3) "foo"))))
    (is (error? (kiss ((fn [x] 3)))))))
