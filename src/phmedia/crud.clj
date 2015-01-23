(ns phmedia.crud
  (:require  [clojure.java.io :as io])
  )

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
