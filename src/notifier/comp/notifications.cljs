
(ns notifier.comp.notifications
  (:require [clojure.string :as string] [clojure.set :as set]))

(defn show-it! [notification on-close!]
  (let [instance (js/Notification.
                   (:title notification)
                   (clj->js
                     (merge
                       {}
                       (if (some? (:body notification))
                         {"body" (:body notification)})
                       (if (some? (:icon notification))
                         {"icon" (:icon notification)}))))]
    (set!
      (.-onclose instance)
      (fn [event] (on-close! (:id notification))))
    (set!
      (.-onclick instance)
      (fn [event] (.focus js/window) (.close instance)))))

(defonce shown-ids (atom (hash-set)))

(defn pop-notification! [notification on-close!]
  (if (= "granted" (.-permission js/Notification))
    (show-it! notification on-close!)
    (.requestPermission
      js/Notification
      (fn [permission]
        (if (= "granted" permission)
          (show-it! notification on-close!))))))

(defn notify! [notifications on-close!]
  (let [ids (into (hash-set) (keys notifications))
        new-ids (set/difference ids @shown-ids)]
    (doseq [new-id new-ids]
      (pop-notification! (get notifications new-id) on-close!))
    (reset! shown-ids ids)))
