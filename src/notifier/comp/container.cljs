
(ns notifier.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core
             :refer
             [defcomp cursor-> action-> mutation-> <> div button textarea span input]]
            [respo.comp.space :refer [=<]]
            [reel.comp.reel :refer [comp-reel]]
            [respo-md.comp.md :refer [comp-md]]
            [notifier.config :refer [dev?]]
            [notifier.core :refer [effect-notify]]))

(defcomp
 comp-container
 (reel)
 (let [store (:store reel), states (:states store)]
   [(effect-notify
     (:notifications store)
     {:when-inactive? true, :on-close (fn [noti] (println noti))})
    (div
     {:style (merge ui/global ui/row)}
     (button
      {:style ui/button,
       :inner-text "Display message",
       :on-click (fn [e d! m!] (d! :notify {:title "a", :body "content", :icon nil}))})
     (when dev? (cursor-> :reel comp-reel states reel {})))]))
