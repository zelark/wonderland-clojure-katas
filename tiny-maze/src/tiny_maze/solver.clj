(ns tiny-maze.solver
  (:require [clojure.zip :as zip]))

(defn not-wall? [maze pos]
  (contains? #{0 :E :S} (get-in maze pos)))

(defn moves [maze loc]
  (let [directions [[1 0] [0 1] [-1 0] [0 -1]]]
    (->> directions
         (map #(mapv + loc %))
         (filter #(not-wall? maze %))
         (map (fn [dir] #{loc dir})))))

(defn transform [maze]
  (let [indexes (for [x (range (count maze))
                      y (range (count (first maze)))]
                  [x y])]
    (reduce #(if (not-wall? maze %2) (into %1 (moves maze %2)) %1)
            #{} indexes)))

(defn maze-zip [maze loc]
  (let [paths (reduce (fn [index [a b]]
                        (merge-with into index {a [b] b [a]}))
                      {}
                      (map seq maze))
        children (fn [[from to]]
                   (seq (for [loc (paths to) :when (not= loc from)]
                          [to loc])))]
    (zip/zipper (constantly true)
                children
                nil
                [nil loc])))

(defn find-position [xs x]
  (-> (for [[i row] (map-indexed vector xs)
            [j val] (map-indexed vector row)
            :when (= val x)]
        [i j])
      first))

(defn mark-as-visited [maze pos]
  (assoc-in maze pos :x))

(defn solve-maze [maze]
  (let [entrance (find-position maze :S)
        exit (find-position maze :E)
        path (->> entrance
                  (maze-zip (transform maze))
                  (iterate zip/next)
                  (filter #(= exit (second (zip/node %))))
                  first
                  zip/path
                  (map second))]
    (reduce #(mark-as-visited %1 %2) maze (concat path [exit]))))
