(ns zhaw-issue-api.routes.home
  (:require [zhaw-issue-api.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (response/redirect "/swagger-ui"))
  (GET "/about" [] (about-page)))
