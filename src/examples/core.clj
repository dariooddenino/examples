(ns examples.core
  (:require [clojure.string :as str]
            [hickory.select :as s]
            [hickory.core :refer :all]
            [clj-webdriver.taxi :refer :all]
            [clj-webdriver.driver :refer [init-driver]])
  
  ;;PhantomJS is necessary because the examples get dynamically injected
  (import org.openqa.selenium.phantomjs.PhantomJSDriver
          org.openqa.selenium.remote.DesiredCapabilities))

(set-driver! (init-driver {:webdriver (PhantomJSDriver. (DesiredCapabilities.))}))

(def cache (atom {}))

(defn cached
  [fun key]
  (if-let [hit (get @cache key)]
      hit
      (let [result (fun key)]
        (swap! cache assoc key result)
        result)))

(defn build-url
  [vname]
  (str "https://clojuredocs.org/" vname))

(defn extract-examples [body]
  (apply str
         (s/select
          (s/descendant (s/class "syntaxify")
                        (s/not (s/tag :span)))
          body)))

(defn get-examples [ex]
  (do (-> ex
          build-url
          to)
      (-> (html "body")
          parse
          as-hickory
          extract-examples)))

(defn print-ex
  [name examples]
  (println "-----------------------------------------------")
  (println name)
  (println "-----------------------------------------------")
  (println examples))

(defmacro examples
  "Prints examples taken from clojuredocs.org"
  [name]
  `(let [clean# #(->> (str %) (re-find #"#'(.*)") last)
         ex# (-> '~name resolve clean#)
         examples# (cached get-examples ex#)]
     (print-ex ex# examples#)))
