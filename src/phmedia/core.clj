(ns phmedia.core
    (:use       compojure.core
                [hiccup.middleware :only (wrap-base-url)])
    (:require   [compojure.route :as route]
                [compojure.handler :as handler]
                [compojure.response :as response])
    (:gen-class)
)


(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn phcreate [] (println "create"))
(defn phread [] (println "read"))
(defn phupdate [] (println "update"))
(defn phdelete [] (println "delete"))

(defroutes main-routes
    (GET "/" [] (uuid))
    (route/not-found "Page not found"))

(def app
    (-> (handler/site main-routes)
    (wrap-base-url)))