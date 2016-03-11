(ns zhaw-weng-api.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [zhaw-weng-api.db.core :as db]
            [schema.core :as s]))

(s/defschema Issue {(s/optional-key :id) Long
                    (s/optional-key :project_id) Long
                    :cid String
                    :done Boolean
                    :title String
                    :due_date java.util.Date})

(s/defschema Project {(s/optional-key :id) Long
                      :title String})

(defn add-issue! [new-issue project_id]
  "Add an issue to the Database and return it as a map with the new ID"
  (let [id (:id (db/create-issue! (assoc new-issue :project_id project_id)))
        issue (assoc new-issue :id id)]
    issue))

(defn add-project! [new-project]
  "Add an project to the Database and return it as a map with the new ID"
  (let [id (:id (db/create-project! new-project))
        project (assoc new-project :id id)]
    project))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}
  (context "/api" []

           (context "/projects" []
                    :tags ["Project API"]

                    (POST "/" []
                          :return Project
                          :body [project Project]
                          :summary "Create and save a project"
                          (println "In the project!")
                          (ok (add-project! project)))

                    (DELETE "/:id" []
                            :path-params [id :- Long]
                            :summary "Deletes a project"
                            (ok (db/delete-project! {:id id}))))

           (context "/project/:project_id" []
                    :tags ["Issues API"]
                    :path-params [project_id :- Long]

                    (DELETE "/issues/:id" []
                            :path-params [id :- Long]
                            :summary "Deletes an issue"
                            (ok (db/delete-issue! {:id id
                                                   :project_id project_id})))

                    (PUT "/issues/:id" []
                         :path-params [id :- Long]
                         :return Issue
                         :body [issue Issue]
                         :summary "Updates an issue"
                         (db/update-issue! (assoc issue :id id
                                                  :project_id project_id))
                         (ok (db/get-issue {:id id
                                            :project_id project_id})))

                    (POST "/issues" []
                          :return Issue
                          :body [issue Issue]
                          :summary "Create and save an issue"
                          (ok (add-issue! issue project_id)))

                    (GET "/issues" []
                         :return [Issue]
                         :summary "Retrieve all issues"
                         (ok (db/get-issues {:project_id project_id}))))

           (context "/tests" []
                    :tags ["Practice HTTP based services"]

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
                          (ok issue)))))
