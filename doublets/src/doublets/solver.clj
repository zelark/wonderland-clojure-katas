(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(defn distace [word1 word2]
  (reduce + (map #(if (= %1 %2) 0 1) word1 word2)))

(defn doublets [word1 word2]
  (if (not= (count word1) (count word2))
    []
    (let [length (count word1)
          dict (->> words (filter #(= (count %) length)) (reduce #(assoc %1 %2 (distace %2 word2)) {}))]
      (loop [path (vector word1) word word1]
        (let [ways (filter #(= (distace word (key %)) 1) dict)
              next-word (->> ways (sort-by val <) (filter #((complement contains?) (set path) (key %))) first)]
          (if (= (val next-word) 0)
            (conj path (key next-word))
            (recur (conj path (key next-word)) (key next-word))))))))

