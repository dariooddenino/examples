# examples

Clojure examples from clojuredocs.org for when `doc` is not enough!

[![](https://clojars.org/examples/latest-version.svg)](https://clojars.org/examples)

## Usage

### Installation

- Download phantomjs
- Create an environment variable `PHANTOMJS` that points to its executable
- Add the dependency to your project:
```clojure
(defproject ...
    :dependencies [[examples "0.1.1"]]}
```
- And require it:
```clojure
(:require [examples.core :refer [examples clear-examples-cache])
```

### Printing examples 

`(examples [fun])`

E.g. `(examples map)` will print all clojuredocs.org examples of `map`:

```clojure
-----------------------------------------------
clojure.core/map  
-----------------------------------------------
(map inc [1 2 3 4 5])
;;=> (2 3 4 5 6)


;; map can be used with multiple collections. Collections will be consumed
;; and passed to the mapping function in parallel:
(map + [1 2 3] [4 5 6])
;;=> (5 7 9)


;; When map is passed more than one collection, the mapping function will
;; be applied until one of the collections runs out:
(map + [1 2 3] (iterate inc 1))
;;=> (2 4 6)



;; map is often used in conjunction with the # reader macro:
(map #(str "Hello " % "!" ) ["Ford" "Arthur" "Tricia"])
;;=> ("Hello Ford!" "Hello Arthur!" "Hello Tricia!")

;; A useful idiom to pull "columns" out of a collection of collections. 
;; Note, it is equivalent to:
;; user=> (map vector [:a :b :c] [:d :e :f] [:g :h :i])

(apply map vector [[:a :b :c]
                   [:d :e :f]
                   [:g :h :i]])

;;=> ([:a :d :g] [:b :e :h] [:c :f :i])

;; From http://clojure-examples.appspot.com/clojure.core/mapuser=> (map #(vector (first %) (* 2 (second %)))
            {:a 1 :b 2 :c 3})
([:a 2] [:b 4] [:c 6])

user=> (into {} *1)  
{:a 2, :b 4, :c 6}
;; Use a hash-map as a function to translate values in a collection from the 
;; given key to the associated value

user=> (map {2 "two" 3 "three"} [5 3 2])  
(nil "three" "two")

;; then use (filter identity... to remove the nils
user=> (filter identity (map {2 "two" 3 "three"} [5 3 2]))  
("three" "two");; mapping over a hash-map applies (into) first. 
;; need to use functions that deal with arrays (fn [[key val]] ...)
(map pprint {:key :val :key1 :val1})
([:key :val]
[:key1 :val1]
nil nil)

;;above, the pprint output appears to be part of the return value but it's not:
(hash-set (map pprint {:key :val :key1 :val1}))
[:key :val]
[:key1 :val1]
#{(nil nil)}

(map second {:key :val :key1 :val1})
(:val :val1)(map fn [a 4 x]
        [b 5 y]
        [c 6])    
;        ^ ^
; applies fn to a b c as (fn a b c)
; applies fn to 4 5 6 as (fn 4 5 6)
; ignores (x y)
; returns a list of results
; equivalent to (list (fn a b c) (fn 4 5 6))

;example
(map list [1 2 3]
         '(a b c)
         '(4 5))

user=> (map list  [1 2 3] '(a b c) '(4 5))  
((1 a 4) (2 b 5))
;same as
user=> (list (list 1 'a 4) (list 2 'b 5))  
((1 a 4) (2 b 5)); map passed two collection arguments. From 4Clojure Problem #157

(def d1 [:a :b :c])
(#(map list % (range)) d1)
;;=> ((:a 0) (:b 1) (:c 2));; Used without a collection, map will create a transducer:
(def xf (map inc))

;; We can now apply this transducer to a sequence:
(transduce xf conj (range 5))
;; => [1 2 3 4 5]
;; Extract keyword from a collection of obj
(map :a '({:a 1 :b 0} {:a 2 :b 0} {:a 3 :b 1} {:a 3 :b 0}))
;; =>(1 2 3 3)(= {:a 1 :b 2 :c 3} (hash-map :a 1 :b 2 :c 3))
```

### Clearing cache

Cache can be cleared with `(clear-examples-cache [fun])`.
If `name` is omitted, the entire cache will be cleared.

## License

Copyright © 2016 Dario Oddenino

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
