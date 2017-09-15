(ns user
  (:require [mount.core :as mount]
            zhaw-issue-api.core))

(defn start []
  (mount/start-without #'zhaw-issue-api.core/repl-server))

(defn stop []
  (mount/stop-except #'zhaw-issue-api.core/repl-server))

(defn restart []
  (stop)
  (start))


