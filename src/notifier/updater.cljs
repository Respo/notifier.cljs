
(ns notifier.updater
  (:require [respo.cursor :refer [update-states]] [notifier.schema :as schema]))

(defn updater [store op op-data op-id op-time]
  (case op
    :states (update-states store op-data)
    :hydrate-storage op-data
    :notify
      (assoc-in store [:notifications op-id] (merge schema/notification op-data {:id op-id}))
    store))
