(ns examples.core
  (:require [clojure.string :as str]
            [hickory.select :as s])
  (use clj-webdriver.taxi)
  (use hickory.core)
  (import org.openqa.selenium.phantomjs.PhantomJSDriver
          org.openqa.selenium.remote.DesiredCapabilities)
  (use [clj-webdriver.driver :only [init-driver]]))

(set-driver! (init-driver {:webdriver (PhantomJSDriver. (DesiredCapabilities.))}))

(def cache (atom {}))

(defn build-url
  [vname]
  (str "https://clojuredocs.org/" vname))

(defn clean
  [fname]
  (last (re-find #"#'(.*)" (str fname))))


(defmacro examples
  [name]
  `(let [x# (clean (resolve '~name))]
     (if (not (nil? (get-in @cache [x#])))
       (get-in @cache [x#])
       (do (to (build-url x#))
           (let [result# (as-hickory (parse (html "body")))
                 parsed# (apply str
                                (s/select
                                  (s/descendant (s/class "syntaxify")
                                                (s/not (s/tag :span)))
                                  result#))]
             (do (swap! cache assoc-in [x#] parsed#)
                 parsed#))))))

(examples not-empty)
