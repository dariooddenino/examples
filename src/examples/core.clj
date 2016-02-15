(ns examples.core
  (:require [clojure.string :as str]
            [hickory.select :as s]
            [hickory.core :refer :all]
            [clj-http.client :as c]))

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
  (str "http://clojuredocs.org/" vname))

(defn get-data
  [htree]
  (-> (s/select (s/child (s/tag :head)
                         (s/tag :script))
                htree)
      first :content last))

(defn clean-data
  [data]
  (let [trimmed-data (subs data 31 (- (count data) 8))]
    (-> trimmed-data
        (str/replace #"\\\"" "\"")
        (str/replace #"\\\"" "\"")
        read-string)))

(defn get-examples
  [ex]
  (-> ex
      build-url
      c/get
      :body
      parse
      as-hickory
      get-data
      clean-data
      :examples
      ))

(defn clean-name
  [name-s]
  (as-> name-s $
        (str $)
        (re-find #"#'(.*)" $)
        (last $)
        (str/replace $ "?" "_q")))

(defn print-ex
  [name examples]
  (println "-----------------------------------------------")
  (println name)
  (println "-----------------------------------------------")
  (doseq [ex examples]
    (dorun (map println (-> ex :body (str/split #"\\n"))))
    (println)
    (println "-----------")
    (println)))

(defmacro examples
  "Prints examples taken from clojuredocs.org"
  [fun]
  `(let [ex# (-> '~fun resolve clean-name)
         examples# (cached get-examples ex#)]
     (print-ex ex# examples#)))

(defmacro clear-examples-cache
  "Clear examples cache for the function passed as argument

  If no argument is provided, the entire cache is cleared"
  ([] `(do (reset! cache {})
           (println "Cache cleared!")))
  ([fun] `(let [ex# (-> '~fun resolve clean-name)]
            (do (swap! cache dissoc ex#)
                (println (str ex# " cache cleared!"))))))

