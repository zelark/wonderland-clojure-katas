(ns alphabet-cipher.coder)

(def ^:private codes (zipmap (map char (range (int \a) (inc (int \z))))
                             (range 26)))

(defn encode [keyword message]
  (->> (cycle keyword)
       (map #(char (+ 97 (mod (+ (codes %1) (codes %2)) 26))) message)
       (apply str)))

(defn decode [keyword message]
  (->> (cycle keyword)
       (map #(char (+ 97 (mod (- (codes %1) (codes %2)) 26))) message)
       (apply str)))

(defn- extract-keyword [string]
  (loop [n 1]
    (if (not-every? true? (map = string (drop n string)))
      (recur (inc n))
      (.substring string 0 n))))

(defn decipher [cipher message]
  (->> cipher
       (map #(char (+ 97 (mod (- (codes %2) (codes %1)) 26))) message)
       (apply str)
       extract-keyword))
