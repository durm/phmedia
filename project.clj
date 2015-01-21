(defproject phmedia "0.0.1"
  :description "Ph-media - media storage"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.9.1"]]
  :ring {:handler phmedia.core/app}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [hiccup "1.0.5"]
                 [clj-time "0.9.0"]])
