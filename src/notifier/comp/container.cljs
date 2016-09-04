
(ns notifier.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]
            [respo.comp.debug :refer [comp-debug]]
            [notifier.style.widget :as widget]))

(defn on-add [e dispatch!] (dispatch! :add-one nil))

(defn render [store]
  (fn [state mutate!]
    (div
      {:style (merge ui/global ui/card)}
      (div
        {:style ui/button, :event {:click on-add}}
        (comp-text "Add" nil))
      (comp-debug store nil))))

(def comp-container (create-comp :container render))
