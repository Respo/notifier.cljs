
(ns notifier.updater (:require [respo.cursor :refer [mutate]] [notifier.schema :as schema]))

(defn updater [store op op-data op-id op-time]
  (case op
    :states (update store :states (mutate op-data))
    :hydrate-storage op-data
    :notify
      (assoc-in store [:notifications op-id] (merge schema/notification op-data {:id op-id}))
    store))
