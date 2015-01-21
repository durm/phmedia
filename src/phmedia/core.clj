(ns phmedia.core
  (:use       compojure.core
              [hiccup.middleware :only (wrap-base-url)]
              [hiccup.core])
  (:require   [compojure.route :as route]
              [compojure.handler :as handler]
              [compojure.response :as response]
              (ring.middleware [multipart-params :as mp])
              [clojure.java.io :as io]
              [ring.util.response :as resp]
              [clj-time.local :as l])
  (:gen-class)
  )


(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-file
  ([filename] (let [ofile (io/file "data" (subs filename 0 3) (subs filename 3 6) filename)]
                (io/make-parents ofile)
                ofile
                )
   )
  ([] (get-file (uuid)))
  )

(defn phcreate
  ([tempfile]
   (let [fid (uuid)
         ofile (get-file fid)]
     (io/copy tempfile ofile)
     fid)
   )
  )
(defn phread [fid] (get-file fid))
(defn phupdate [fid tempfile]
  (let [ofile (get-file fid)]
     (io/copy tempfile ofile)
     fid)
  )
(defn phdelete [fid]
  (let [ofile (get-file fid)]
     (io/delete-file ofile)
     fid)
  )

(defn phcreate-response
  [tempfile] (html [:created {:id (phcreate tempfile) :timestamp (l/local-now)}])
  )
(defn phread-response
  [fid] (phread fid)
  )
(defn phupdate-response
  [fid tempfile] (html [:updated {:id (phupdate fid tempfile) :timestamp (l/local-now)}])
  )
(defn phdelete-response
  [fid] (html [:deleted {:id (phdelete fid) :timestamp (l/local-now)}])
  )

(defroutes main-routes
  (GET "/" [] "as")
  (GET "/:fid" [fid] (phread-response fid))
  (POST "/" {{{tempfile :tempfile filename :filename} :file} :params :as params} (phcreate-response tempfile))
  (PUT "/:fid" {{{tempfile :tempfile filename :filename} :file fid :fid} :params :as params} (phupdate-response fid tempfile))
  (DELETE "/:fid" [fid] (phdelete-response fid))
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (mp/wrap-multipart-params)
      (wrap-base-url)
      ))
