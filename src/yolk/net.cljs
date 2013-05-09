(ns yolk.net
  (:require [yolk.bacon :as b]
            [jayq.core :refer [$] :as j]))

(defn ajax [params]
  (js/Bacon.UI.ajax (clj->js params)))

(defn remote [name & params]
  (ajax {:url "_fetch"
         :type "POST"
         :data {:remote name
                :params (pr-str (vec params))}}))

(defn on-remote [name params f]
  (-> (apply remote name params)
      (b/on-value f)))
