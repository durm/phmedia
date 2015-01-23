(ns phmedia.views
  (:use       [hiccup.core]
              [phmedia.utils]
              [phmedia.crud])
  (:require   [clj-time.local :as l])
  )

(defn get-success-resp
  [operation fid params] (html [operation (assoc params :id fid :timestamp (l/local-now))])
  )

(defn phcreate-response
  [tempfile params] (let [fid (uuid) ofile (get-file fid)]
                      (try
                        (do
                          (phcreate tempfile ofile)
                          (get-success-resp :created fid params)
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
  [fid tempfile params] (let [ofile (get-file fid)]
                          (try
                            (do
                              (phupdate tempfile ofile)
                              (get-success-resp :updated fid params)
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

(defn usage []
  (html [:div
         [:h1 "Ph-media"]
         [:h2 "Static content storage"]
         [:h3 "Usage"]
         [:ul
          [:li
           [:h4 "Create"]
           [:p "POST / (with `file` param)"]
           ]
          [:li
           [:h4 "Read"]
           [:p "GET /:id"]
           ]
          [:li
           [:h4 "Update"]
           [:p "PUT /:id (with `file` param)"]
           ]
          [:li
           [:h4 "Delete"]
           [:p "DELETE /:id"]
           ]
          ]
         ]
        )
  )
