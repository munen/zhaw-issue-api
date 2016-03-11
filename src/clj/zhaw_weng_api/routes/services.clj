(ns zhaw-weng-api.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [zhaw-weng-api.db.core :as db]
            [schema.core :as s]))

(s/defschema Issue {(s/optional-key :id) Long
                    :cid String
                    :done Boolean
                    :title String
                    :due_date java.util.Date})

(defn add-issue! [new-issue]
  "Add an issue to the Database and return it as a map with the new ID"
  (let [id (:id (db/create-issue! new-issue))
        issue (assoc new-issue :id id)]
    issue))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  (context "/api" []
           :tags ["Issues API"]

           (POST "/issues" []
                 :return Issue
                 :body [issue Issue]
                 :summary "Create and save an issue"
                 (ok (add-issue! issue)))

           ;; (db/create-issue! {:title "bar"}) 

           (GET "/issues" []
                 :return [Issue]
                 :summary "Retrieve all issues"
                 (ok (db/get-issues))))

  (context "/tests" []
           :tags ["practice HTTP based services"]

           (GET "/plus" []
                :return       Long
                :query-params [x :- Long, {y :- Long 1}]
                :summary      "x+y with query-parameters. y defaults to 1."
                (ok (+ x y)))

           (POST "/minus" []
                 :return      Long
                 :form-params [x :- Long, y :- Long]
                 :summary     "x-y with body-parameters."
                 (ok (- x y)))

           (PUT "/echo" []
                :return   [{:hot Boolean}]
                :body     [body [{:hot Boolean}]]
                :summary  "echoes a vector of anonymous hotties"
                (ok body))

           (POST "/echo" []
                 :return   (s/maybe Issue)
                 :body     [issue (s/maybe Issue)]
                 :summary  "echoes a Issue from json-body"
                 (ok issue))))
