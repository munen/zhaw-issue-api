(ns zhaw-weng-api.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [zhaw-weng-api.handler :refer :all]))

(deftest test-app
  
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= 200 (:status response)))))

  ;; (testing "create issue"
  ;;   (let [response (app (request :post "/api/issues" {:cid "string"
  ;;                                                     :done true
  ;;                                                     :title "string"
  ;;                                                     :due-date "string"} ))]
  ;;     ;; (println response)
  ;;     ;; (println (slurp (:body response)))
  ;;     (is (= 200 (:status response)))))

  (testing "add stuff"
    (let [response (app (request :get "/tests/plus" {:x 5 :y 2}))]
      (is (= 200 (:status response)))
      (is (= "7" (slurp (:body response))))))

  (testing "delete stuff"
    (let [response (app (request :post "/tests/minus" {:x "12" :y "10"}))]
      (is (= "2" (slurp (:body response))))
      (is (= 200 (:status response)))))

  (testing "swagger-ui route 2"
    (let [response (app (request :get "/swagger-ui/index.html"))]
      (is (= 200 (:status response)))))

  (testing "swagger-ui route 1"
    (let [response (app (request :get "/swagger-ui"))]
      (is (= 302 (:status response)))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= 404 (:status response))))))
