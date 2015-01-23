(ns phmedia.core
  (:use       compojure.core
              [hiccup.middleware :only (wrap-base-url)])
  (:require   [compojure.route :as route]
              [compojure.handler :as handler]
              (ring.middleware [multipart-params :as mp])
              [phmedia.views :as phviews]
              [phmedia.utils :as phutils]
              [ring.adapter.jetty :as ring])
  (:gen-class)
  )

(defroutes main-routes

  (GET "/" [] (phviews/usage))

  (GET "/:fid" [fid] (phviews/phread-response fid))
  (POST "/" {{{tempfile :tempfile filename :filename} :file} :params :as params} (phviews/phcreate-response tempfile (phutils/get-req-file-info  params)))
  (PUT "/:fid" {{{tempfile :tempfile filename :filename} :file fid :fid} :params :as params} (phviews/phupdate-response fid tempfile (phutils/get-req-file-info params)))
  (DELETE "/:fid" [fid] (phviews/phdelete-response fid))

  (route/not-found "Page not found")

  )

(def app
  (-> (handler/site main-routes)
      (mp/wrap-multipart-params)
      (wrap-base-url)
      ))

(defn -main []
  (ring/run-jetty app {:port 8080 :join? false}))
