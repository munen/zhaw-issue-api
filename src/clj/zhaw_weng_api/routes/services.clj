(ns zhaw-weng-api.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]))

(s/defschema Issue {(s/optional-key :id) Long
                    :cid s/Keyword
                    :done Boolean
                    :title String
                    :due-date String})

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  (context "/api" []
           :tags ["issues"]

           (POST "/issues" []
                 :return   (s/maybe Issue)
                 :body     [issue (s/maybe Issue)]
                 :summary  "Create and save an Issue"
                 (created issue))

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
