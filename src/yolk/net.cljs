(ns yolk.net
  (:require [yolk.bacon :as b]
            [jayq.core :refer [$] :as j]))

(defn ajax [params]
  (js/Bacon.UI.ajax (clj->js params)))

(defn remote [name & params]
  (ajax {:url "/_fetch"
         :type "POST"
         :data {:remote name
                :params (pr-str (vec params))}}))

(defn remote-poll [bus timeout name params]
  (->  (j/ajax {:url "/_fetch"
                :type "POST"
                :data {:remote name
                       :params (pr-str (vec params))}})
       (.always (fn [e]
                  (b/push bus e)
                  (js/setTimeout #(remote-poll bus timeout name params) timeout)))))

(defn remote-long-poll [timeout remote & args]
  (let [read-bus (b/bus)]
    (js/setTimeout #(remote-poll read-bus timeout remote args) 1)
    read-bus))

(defn on-remote [name params f]
  (-> (apply remote name params)
      (b/on-value f)))
