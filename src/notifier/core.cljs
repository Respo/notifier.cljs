
(ns notifier.core
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core
             :refer
             [defcomp cursor-> action-> mutation-> <> div button textarea span input]]
            [respo.comp.space :refer [=<]]
            [reel.comp.reel :refer [comp-reel]]
            [notifier.config :refer [dev?]]
            [clojure.set :refer [difference]]))

(defcomp comp-notifier () (div {} (<> "Notifier")))

(defn show-it! [notification on-close!]
  (let [instance (js/Notification.
                  (:title notification)
                  (clj->js
                   (merge
                    {}
                    (if (some? (:body notification)) {"body" (:body notification)})
                    (if (some? (:icon notification)) {"icon" (:icon notification)}))))]
    (set! (.-onclose instance) (fn [event] (on-close! (:id notification))))
    (set! (.-onclick instance) (fn [event] (.focus js/window) (.close instance)))))

(defn pop-notification! [notification on-close!]
  (if (not= "visible" (.-visibilityState js/document))
    (if (= "granted" (.-permission js/Notification))
      (show-it! notification on-close!)
      (.requestPermission
       js/Notification
       (fn [permission] (if (= "granted" permission) (show-it! notification on-close!)))))))

(defonce shown-ids (atom (hash-set)))

(defn notify! [notifications on-close!]
  (let [ids (into (hash-set) (keys notifications)), new-ids (difference ids @shown-ids)]
    (doseq [new-id new-ids] (pop-notification! (get notifications new-id) on-close!))
    (reset! shown-ids ids)))
