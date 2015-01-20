(ns phmedia.core
  (:use       compojure.core
              [hiccup.middleware :only (wrap-base-url)]
              [hiccup.core])
  (:require   [compojure.route :as route]
              [compojure.handler :as handler]
              [compojure.response :as response]
              (ring.middleware [multipart-params :as mp])
              [clojure.java.io :as io])
  (:gen-class)
  )


(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-path
  ([filename] (->
               (io/file "data" (subs filename 0 3) (subs filename 3 6) filename)
               (.getPath))
   )
  ([] (get-path (uuid)))
  )

(defn phcreate
  ([tempfile]
   (let [fid (uuid)]
     (io/copy tempfile (get-path fid))
     fid)
     )
  )
(defn phread [] (println "read"))
(defn phupdate [] (println "update"))
(defn phdelete [] (println "delete"))

(defroutes main-routes
  (GET "/" [] (get-path))
  (POST "/" {{{tempfile :tempfile filename :filename} :file} :params :as params}
        (html [:created {:id (phcreate tempfile)}]))
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (mp/wrap-multipart-params)
      (wrap-base-url)
      ))
