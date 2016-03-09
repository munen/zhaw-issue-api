(ns zhaw-weng-api.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [zhaw-weng-api.layout :refer [error-page]]
            [zhaw-weng-api.routes.home :refer [home-routes]]
            [zhaw-weng-api.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [ring.middleware.cors :refer [wrap-cors]]
            [zhaw-weng-api.middleware :as middleware]))

(def app-routes
  (routes
   (wrap-cors #'service-routes
              :access-control-allow-origin #".+"
              :access-control-allow-methods [:get :put :post :delete])
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(def app (middleware/wrap-base #'app-routes))
