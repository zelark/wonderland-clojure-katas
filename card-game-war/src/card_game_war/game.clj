(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(defn- lower? [[suit-a rank-a] [suit-b rank-b]]
  (if (= rank-a rank-b)
    (< (.indexOf suits suit-a) (.indexOf suits suit-b))
    (< (.indexOf ranks rank-a) (.indexOf ranks rank-b))))

(defn play-round [card1 card2]
  (if (lower? card1 card2) card2 card1))

(defn play-game [[card1 & deck1 :as pl1-cards]
                 [card2 & deck2 :as pl2-cards]]
  (cond
    (empty? pl1-cards) :player2
    (empty? pl2-cards) :player1
    :else
      (if (= (play-round card1 card2) card1)
        (recur (conj (vec deck1) card1 card2) deck2)
        (recur deck1 (conj (vec deck2) card1 card2)))))
