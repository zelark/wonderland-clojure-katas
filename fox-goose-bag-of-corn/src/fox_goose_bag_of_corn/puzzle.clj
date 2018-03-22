(ns fox-goose-bag-of-corn.puzzle)

(defonce world (atom {}))

(def loc-id { :left-side 0
              :boat 1
              :right-side 2 })

(def blank-step [[] [:boat] []])

(defn render [state]
  (reduce (fn [step {:keys [name loc]}]
            (update step (get loc-id loc) conj name))
          blank-step
          (vals state)))

(defn init [state]
  (reset! state
          { :you   { :name :you   :loc :left-side }
            :fox   { :name :fox   :loc :left-side }
            :goose { :name :goose :loc :left-side }
            :corn  { :name :corn  :loc :left-side }})
  (list (render @state)))

(defn move-to [new-loc names]
  (let [new-world (reduce #(assoc %1 %2 {:loc new-loc}) {} names)]
    (swap! world #(merge-with merge % new-world)))
  (render @world))

(defn to-left-side [names]
  (map #(move-to % names) [:boat :left-side]))

(defn to-right-side [names]
  (map #(move-to % names) [:boat :right-side]))

(def steps [
  [#'init          world]
  [#'to-right-side [:you :goose]]
  [#'to-left-side  [:you]]
  [#'to-right-side [:you :fox]]
  [#'to-left-side  [:you :goose]]
  [#'to-right-side [:you :corn]]
  [#'to-left-side  [:you]]
  [#'to-right-side [:you :goose]]])

(defn river-crossing-plan []
  (mapcat (fn [[f arg]] (if (some? arg) (f arg) (f))) steps))

