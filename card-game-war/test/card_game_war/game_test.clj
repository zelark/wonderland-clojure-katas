(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= (play-round [:heart 2] [:heart 3]) [:heart 3])))
  (testing "queens are higher rank than jacks"
    (is (= (play-round [:heart :queen] [:heart :jack]) [:heart :queen])))
  (testing "kings are higher rank than queens"
    (is (= (play-round [:heart :king] [:heart :queen]) [:heart :king])))
  (testing "aces are higher rank than kings"
    (is (= (play-round [:heart :ace] [:heart :king]) [:heart :ace])))
  (testing "if the ranks are equal, clubs beat spades"
    (is (= (play-round [:club 9] [:spade 9]) [:club 9])))
  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= (play-round [:diamond 9] [:club 9]) [:diamond 9])))
  (testing "if the ranks are equal, hearts beat diamonds")
    (is (= (play-round [:heart 9] [:diamond 9]) [:heart 9])))

(deftest test-play-game
  (testing "the player loses when they run out of cards")
    (is (= :player1 (play-game [[:diamond 2]] [])))
    (is (= :player2 (play-game [] [[:diamond 2]])))
    (is (= :player1 (play-game [[:diamond 9]] [[:spade 5]])))
    (is (= :player2 (play-game [[:diamond 9] [:spade 7] [:club :jack]]
                               [[:club 10] [:spade 5] [:heart :king]]))))

