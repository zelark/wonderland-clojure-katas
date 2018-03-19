(ns wonderland-number.finder)

(defn have-same-digits? [& coll]
  "Returns true if all numbers in coll have the same digits."
  (->> (map (comp sort str) coll)
       (apply =)))

(defn wonderland-number? [n]
  (every? #(have-same-digits? n (* n %)) (range 2 7)))

(defn wonderland-number []
  (some #(when (wonderland-number? %) %) (range 100000 (/ 1000000 6))))
