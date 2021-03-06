(ns cljs-cheatsheet.util)

(def $ js/jQuery)

;;------------------------------------------------------------------------------
;; Util Functions
;;------------------------------------------------------------------------------

(defn log
  "Log a Clojure thing."
  [thing]
  (js/console.log (pr-str thing)))

(defn js-log
  "Log a JavaScript thing."
  [thing]
  (js/console.log thing))

(defn uuid []
  "Create a UUID."
  []
  (apply
   str
   (map
    (fn [x]
      (if (= x \0)
        (.toString (bit-or (* 16 (.random js/Math)) 0) 16)
        x))
    "00000000-0000-4000-0000-000000000000")))

(defn half [n]
  (/ n 2))

(defn extract-namespace [full-name]
  (let [first-slash-pos (.indexOf full-name "/")]
    (subs full-name 0 first-slash-pos)))

(defn extract-symbol [full-name]
  (let [first-slash-pos (.indexOf full-name "/")]
    (subs full-name (inc first-slash-pos))))

(defn split-full-name [r]
  (let [ns1 (extract-namespace r)
        symbol-name (extract-symbol r)]
    {:full-name r
     :namespace ns1
     :symbol symbol-name}))

(defn point-inside-box? [point box]
  (let [px (:x point)
        py (:y point)]
    (and (>= px (:x1 box))
         (<= px (:x2 box))
         (>= py (:y1 box))
         (<= py (:y2 box)))))
