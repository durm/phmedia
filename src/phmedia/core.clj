(ns phmedia.core
    (:use       compojure.core
                [hiccup.middleware :only (wrap-base-url)])
    (:require   [compojure.route :as route]
                [compojure.handler :as handler]
                [compojure.response :as response]
                [clojure.java.io :as io])
    (:gen-class)
)


(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-path []
  (let [filename (uuid)]
    (->
      (io/file (subs filename 0 3) (subs filename 3 6) filename)
      (.getPath))
    )
  )

(defn phcreate
  ([] (println "create")))
(defn phread [] (println "read"))
(defn phupdate [] (println "update"))
(defn phdelete [] (println "delete"))

(defroutes main-routes
    (GET "/" [] (get-path))
    (route/not-found "Page not found"))

(def app
    (-> (handler/site main-routes)
    (wrap-base-url)))
