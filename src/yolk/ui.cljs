(ns yolk.ui
  (:require [yolk.bacon :as b]
            [jayq.core :refer [$] :as j]))

(defn ->stream [$elem event & [selector event-transformer]]
  (.asEventStream $elem event selector event-transformer))

(defn mousemove [$elem]
  (.asEventStream $elem "mousemove"))

(defn click [$elem & [selector event-transformer]]
  (-> $elem
      (->stream "click" selector event-transformer)
      (.doAction ".preventDefault")))

(defn change [$elem & [selector event-transformer]]
  (-> $elem
      (->stream "change" selector event-transformer)))

(defn inner [$elem property]
  (b/on-value property (partial j/inner $elem)))

(defn textfield-value [$elem & [init]]
  (js/Bacon.UI.textFieldValue $elem init))

(defn option-value [$elem & [init]]
  (js/Bacon.UI.optionValue $elem init))

(defn checkbox-group-value [$checkboxes & [init]]
  (js/Bacon.UI.checkBoxGroupValue $checkboxes init))

(defn radio-group-value [$elem & [init]]
  (js/Bacon.UI.radioGroupValue $elem init))

(defn checkbox-value [$elem & [selector]]
  (-> (->stream $elem "change" selector)
      (b/map #(-> % .-target $ (.prop "checked")))
      b/to-property
      b/skip-duplicates))

(defn class-if [$elem klass property]
  (b/on-value property
              (fn [x?]
                ((if x?  j/add-class j/remove-class)
                 $elem klass))))

(defn show
  ([$elem property]
     (show $elem property nil))
  ([$elem options property]
     (b/on-value (b/filter property) #(.show $elem options))))

(defn hide
  ([$elem property]
     (hide $elem property nil))
  ([$elem options property]
     (b/on-value (b/filter property) #(.hide $elem options))))

(defn show-when
  ([$elem property]
     (show-when $elem property))
  ([$elem options property]
     (show $elem options property)
     (hide $elem options (b/not property))))

(defn hide-when
  ([$elem property]
       (hide-when $elem property))
  ([$elem options property]
     (hide $elem options property)
     (show $elem options (b/not property))))

(defn switch-when
  ([$elem1 $elem2 property]
     (switch-when $elem1 $elem2 nil property))
  ([$elem1 $elem2 options property]
     (show-when $elem1 options property)
     (hide-when $elem2 options property)))