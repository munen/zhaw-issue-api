(ns user
  (:require [mount.core :as mount]
            zhaw-weng-api.core))

(defn start []
  (mount/start-without #'zhaw-weng-api.core/repl-server))

(defn stop []
  (mount/stop-except #'zhaw-weng-api.core/repl-server))

(defn restart []
  (stop)
  (start))


