(ns wonderland-number.finder)

(defn have-same-digits?
  "Returns true if all numbers in coll have the same digits."
  [& coll]
  (->> (map (comp sort str) coll)
       (apply =)))

(defn wonderland-number? [n]
  (every? #(have-same-digits? n (* n %)) (range 2 7)))

(defn wonderland-number []
  (some #(when (wonderland-number? %) %) (range 100000 (/ 1000000 6))))

(defn num->digits
  "Splits a number at its digits."
  [n]
  (map #(- (int %) 48) (str n)))

(defn narcissistic-numbers
  "Returns narcissistic numbers under 1,000.
  A narcissistic number is a number that is the sum of its own digits
  each raised to the power of the number of digits."
  []
  (filter (fn [n]
            (->> (reduce #(+ %1 (* %2 %2 %2)) 0 (num->digits n))
                 (= n)))
          (range 100 1000)))
