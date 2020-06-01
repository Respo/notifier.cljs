
(ns notifier.core
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp >> <> div button textarea span input defeffect]]
            [respo.comp.space :refer [=<]]
            [reel.comp.reel :refer [comp-reel]]
            [notifier.config :refer [dev?]]
            [clojure.set :refer [difference union]]))

(defonce *shown-ids (atom #{}))

(defn show-it! [notification options]
  (let [instance (js/Notification.
                  (:title notification)
                  (clj->js
                   (merge
                    {}
                    (if (some? (:body notification)) {"body" (:body notification)})
                    (if (some? (:icon notification)) {"icon" (:icon notification)}))))
        on-close (:on-close options)]
    (set! (.-onclose instance) (fn [event] (on-close notification)))
    (set! (.-onclick instance) (fn [event] (.focus js/window) (.close instance)))))

(defn pop-notification! [notification options]
  (println
   options
   (.-visibilityState js/document)
   (if (:inactive-only? options) (not= "visible" (.-visibilityState js/document)) true))
  (when (if (:when-inactive? options) (not= "visible" (.-visibilityState js/document)) true)
    (when (= "granted" (.-permission js/Notification))
      (show-it! notification options)
      (.requestPermission
       js/Notification
       (fn [permission] (if (= "granted" permission) (show-it! notification options)))))))

(defeffect
 effect-notify
 (notifications options)
 (action el *local)
 (if (or (= action :mount) (= action :update))
   (let [ids (into #{} (keys notifications)), new-ids (difference ids @*shown-ids)]
     (doseq [new-id new-ids] (pop-notification! (get notifications new-id) options))
     (reset! *shown-ids (union ids @*shown-ids)))
   nil))

(defn notify! [notifications on-close!]
  (let [ids (into (hash-set) (keys notifications)), new-ids (difference ids @*shown-ids)]
    (doseq [new-id new-ids] (pop-notification! (get notifications new-id) on-close!))
    (reset! *shown-ids ids)))
