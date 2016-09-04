
(ns notifier.core
  (:require [respo.core :refer [render! clear-cache!]]
            [notifier.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [notifier.updater.core :refer [updater]]
            [notifier.comp.notifications :refer [notify!]]))

(defonce store-ref (atom {}))

(defn dispatch! [op op-data]
  (let [id (.valueOf (js/Date.))
        new-store (updater @store-ref op op-data id)]
    (reset! store-ref new-store)))

(defn on-close! [id] (dispatch! :remove-one id))

(defonce states-ref (atom {}))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)
    (notify! @store-ref on-close!)))

(defn on-jsload []
  (clear-cache!)
  (render-app!)
  (println "code update."))

(defn -main []
  (enable-console-print!)
  (render-app!)
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (println "app started!"))

(set! (.-onload js/window) -main)
