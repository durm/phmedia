(ns phmedia.utils
  (:require  [clojure.java.io :as io])
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

(defn get-req-file-info
  [params] (dissoc (get (get params :params) :file) :tempfile)
  )
