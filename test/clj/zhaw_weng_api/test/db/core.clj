(ns zhaw-weng-api.test.db.core
  (:require [zhaw-weng-api.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [zhaw-weng-api.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'zhaw-weng-api.config/env
      #'zhaw-weng-api.db.core/*db*)
    (migrations/migrate ["migrate"] (env :database-url))
    (f)))

(deftest test-issues
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (testing "generated functions from HugSQL are working"
      (let [id (:id (db/create-issue!
                     t-conn
                     {:title      "Test Issue 1"}))]
      (is (= {:id         id
              :title      "Test Issue 1"}
             (db/get-issue t-conn {:id id})))
      (is (= 1 (db/update-issue!
                t-conn
                {:id         id
                 :title      "Test Issue Updated"})))
      (is (= {:id         id
              :title      "Test Issue Updated"}
             (db/get-issue t-conn {:id id})))
      (is (= 1 (db/delete-issue!
                t-conn
                {:id         id})))
      (is (= nil
             (db/get-issue t-conn {:id id})))))))
