(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set :as set]
            [astar.core :as astar]))


(def start [#{ :fox :goose :corn :you }  #{ :boat }  #{}                        ])
(def goal  [#{}                          #{ :boat }  #{ :fox :goose :corn :you }])


(defn not-bad? [[pair _]]
  (let [bad-pairs [#{ :goose :fox  }
                   #{ :goose :corn }]]
    (not-any? #(set/subset? % pair) bad-pairs)))


(defn to-boat [side]
  (let [side (disj side :you)
        boat #{ :boat :you }
        others (for [item side] [(disj side item) (conj boat item)])]
    (filter not-bad? (concat [[side boat]] others))))


(defn leave-boat [[left boat right]]
  (let [move-to (fn [side] (disj (into side boat) :boat))]
    [[(move-to left) #{ :boat } right          ]
     [left           #{ :boat } (move-to right)]]))


(defn leave-left-side [[left _ right]]
  (map #(vector (first %) (second %) right) (to-boat left)))


(defn leave-right-side [[left _ right]]
  (map #(vector left (second %) (first %)) (to-boat right)))


(defn graph [[left boat right :as step]]
  (cond
    (some #{ :you } left)  (leave-left-side step)
    (some #{ :you } boat)  (leave-boat step)
    (some #{ :you } right) (leave-right-side step)))


(defn dist [from to]
  (count (set/difference (last to) (last from))))


(defn get-h [goal]
  (fn [node] (dist node goal)))


(defn river-crossing-plan []
  (map (partial map vec)
       (cons start (astar/route graph dist (get-h goal) start goal))))
