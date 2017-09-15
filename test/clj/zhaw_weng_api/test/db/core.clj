(ns zhaw-weng-api.test.db.core-test
  (:require [zhaw-weng-api.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [zhaw-weng-api.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start #'zhaw-weng-api.config/env
                 #'zhaw-weng-api.db.core/*db*)
    (migrations/migrate ["migrate"] (env :database-url))
    (f)))

(deftest test-issues
  (jdbc/with-db-transaction [t-conn *db*]
    (jdbc/db-set-rollback-only! t-conn)
    (testing "generated functions from HugSQL are working"
      (let [project {:title     "Test Project 1"
                     :client_id "some-uuid-1"
                     :active    true}
            project_id (:id (db/create-project! t-conn project))
            issue {:client_id  "some-uuid-2"
                   :project_client_id "some-uuid-1"
                   :due_date   (java.util.Date.)
                   :done       false
                   :priority   "1"
                   :title      "Test Issue 1"
                   :project_id project_id}
            id (:id (db/create-issue! t-conn issue))]

        (is (= (assoc issue :id id )
               (db/get-issue t-conn {:id id :project_id project_id})))

        (is (= 1
               (db/update-issue!
                t-conn
                (assoc issue
                       :id id
                       :title "Test Issue Updated"))))

        (is (= (assoc issue :id id :title "Test Issue Updated")
               (db/get-issue t-conn {:id id :project_id project_id})))

        (is (= 1 (db/delete-issue!
                  t-conn
                  {:id id :project_id project_id})))

        (is (= nil
               (db/get-issue t-conn {:id id :project_id project_id})))))))
