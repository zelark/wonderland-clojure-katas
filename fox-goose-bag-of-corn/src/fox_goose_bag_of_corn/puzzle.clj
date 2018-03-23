(ns fox-goose-bag-of-corn.puzzle)

(defonce ^:private world (atom {}))

(def ^:private loc-id
  { :left-side 0
    :boat 1
    :right-side 2 })

(def ^:private blank-step [[] [:boat] []])

(defn update-step [step [name pos]]
  (update step (get loc-id pos) conj name))

(defn render-step [state]
  (reduce update-step blank-step @state))

(defn- init []
  (reset! world { :you   :left-side
                  :fox   :left-side
                  :goose :left-side
                  :corn  :left-side })
  (list (render-step world)))

(defn update-position
  [objects new-pos names]
  (reduce #(assoc %1 %2 new-pos) objects names))

(defn move-to [new-pos names]
  (swap! world update-position new-pos names)
  (render-step world))

(defn to-left-side [names]
  (map #(move-to % names) [:boat :left-side]))

(defn to-right-side [names]
  (map #(move-to % names) [:boat :right-side]))

(def steps [
  [#'init]
  [#'to-right-side [:you :goose]]
  [#'to-left-side  [:you]]
  [#'to-right-side [:you :fox]]
  [#'to-left-side  [:you :goose]]
  [#'to-right-side [:you :corn]]
  [#'to-left-side  [:you]]
  [#'to-right-side [:you :goose]]])

(defn river-crossing-plan []
  (mapcat (fn [[f arg]] (if (some? arg) (f arg) (f))) steps))
