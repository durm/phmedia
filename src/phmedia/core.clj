(ns phmedia.core
  (:use       compojure.core
              [hiccup.middleware :only (wrap-base-url)]
              [hiccup.core])
  (:require   [compojure.route :as route]
              [compojure.handler :as handler]
              [compojure.response :as response]
              (ring.middleware [multipart-params :as mp])
              [clojure.java.io :as io]
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

; REST-functions
(defn phcreate [tempfile ofile]
  (if-not (.exists ofile)
    (io/copy tempfile ofile)
    (throw (Exception. "Resource exists."))
    )
  )
(defn phread [ofile]
  (if (.exists ofile)
    ofile
    (throw (Exception. "Resource doesnot exist."))
    )
  )
(defn phupdate [tempfile ofile]
  (if (.exists ofile)
    (io/copy tempfile ofile)
    (throw (Exception. "Resource doesnot exist."))
    )
  )
(defn phdelete [ofile]
  (if (.exists ofile)
    (io/delete-file ofile)
    (throw (Exception. "Resource doesnot exist."))
    )
  )

; Response
(defn get-success-resp
  [operation fid] (html [operation {:id fid :timestamp (l/local-now)}])
  )

; REST-responses
(defn phcreate-response
  [tempfile] (let [fid (uuid) ofile (get-file fid)]
               (try
                 (do
                   (phcreate tempfile ofile)
                   (get-success-resp :created fid)
                   )
                 (catch Exception e (str "Exception: " (.getMessage e))))
               )
  )
(defn phread-response
  [fid] (let [ofile (get-file fid)]
               (try
                 (do
                   (phread ofile)
                   )
                 (catch Exception e (str "Exception: " (.getMessage e))))
               )
  )
(defn phupdate-response
  [fid tempfile] (let [ofile (get-file fid)]
               (try
                 (do
                   (phupdate tempfile ofile)
                   (get-success-resp :updated fid)
                   )
                 (catch Exception e (str "Exception: " (.getMessage e))))
               )
  )
(defn phdelete-response
  [fid] (let [ofile (get-file fid)]
               (try
                 (do
                   (phdelete ofile)
                   (get-success-resp :deleted fid)
                   )
                 (catch Exception e (str "Exception: " (.getMessage e))))
               )
  )

(defroutes main-routes

  (GET "/" [] "Usage")

  (GET "/:fid" [fid] (phread-response fid))
  (POST "/" {{{tempfile :tempfile filename :filename} :file} :params :as params} (phcreate-response tempfile))
  (PUT "/:fid" {{{tempfile :tempfile filename :filename} :file fid :fid} :params :as params} (phupdate-response fid tempfile))
  (DELETE "/:fid" [fid] (phdelete-response fid))

  (route/not-found "Page not found")

  )

(def app
  (-> (handler/site main-routes)
      (mp/wrap-multipart-params)
      (wrap-base-url)
      ))
