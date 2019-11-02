
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
            [notifier.core :refer [effect-notify]]
            [cumulo-util.core :refer [delay!]]))

(defcomp
 comp-container
 (reel)
 (let [store (:store reel)
       states (:states store)
       state (or (:data states) {:when-inactive? false})]
   [(effect-notify
     (:notifications store)
     {:when-inactive? (:when-inactive? state), :on-close (fn [noti] (println noti))})
    (div
     {:style (merge ui/global ui/column {:padding 16})}
     (div
      {}
      (input
       {:type "checkbox",
        :checked (:when-inactive? state),
        :on-change (fn [e d! m!]
          (m! (assoc state :when-inactive? (.-checked (.-target (:event e))))))})
      (<> "Only send message when page in active?"))
     (div
      {}
      (button
       {:style ui/button,
        :inner-text "Display message",
        :on-click (fn [e d! m!] (d! :notify {:title "a", :body "content", :icon nil}))})
      (=< 8 nil)
      (button
       {:style ui/button,
        :inner-text "Display message after 1s",
        :on-click (fn [e d! m!]
          (delay! 1 (fn [] (d! :notify {:title "a", :body "content", :icon nil}))))}))
     (when dev? (cursor-> :reel comp-reel states reel {})))]))
