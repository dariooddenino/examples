(defproject examples "0.1.0"
  :description "Prints examples from clojuredocs.org"
  :url "https://github.com/dariooddenino/examples"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :jvm-ops [(str "-Dphantomjs.binary.path=" (System/getenv "PHANTOMJS")) ]
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [hickory "0.6.0"]
                 [clj-webdriver/clj-webdriver "0.7.2"]
                 [com.github.detro.ghostdriver/phantomjsdriver "1.1.0"]])
