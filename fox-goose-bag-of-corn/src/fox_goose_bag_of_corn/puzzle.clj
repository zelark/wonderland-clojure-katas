(ns fox-goose-bag-of-corn.puzzle)

(def start-pos [[:fox :goose :corn :you] [:boat] []])

(def end-pos   [[] [:boat] [:fox :goose :corn :you]])

(defn river-crossing-plan []
  [(first start-pos)

   [[:fox :corn] [:boat :goose :you] []]
   [[:fox :corn] [:boat] [:you :goose]]
  
   [[:fox :corn] [:boat :you] [:goose]]
   [[:fox :corn :you] [:boat] [:goose]]
  
   [[:corn] [:boat :you :fox] [:goose]]
   [[:corn] [:boat] [:you :fox :goose]]
  
   [[:corn] [:boat :you :goose] [:fox]]
   [[:corn :you :goose] [:boat] [:fox]]
  
   [[:goose] [:boat :corn :you] [:fox]]
   [[:goose] [:boat] [:fox :corn :you]]
  
   [[:goose] [:boat :you] [:fox :corn]]
   [[:goose :you] [:boat] [:fox :corn]]
  
   [[] [:boat :goose :you] [:fox :corn]]
   [[] [:boat] [:fox :corn :goose :you]]])


 :corn :you

;; not-valid! :fox :goose without :you
;; not-valid! :goose :corn without :you

