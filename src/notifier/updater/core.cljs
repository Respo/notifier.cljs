
(ns notifier.updater.core (:require [clojure.string :as string]))

(defn add-one [store op-data op-id]
  (assoc
   store
   op-id
   {:id op-id, :title "demo title", :body "demo body", :time op-id, :icon nil}))

(defn default-handler [store op-data op-id] store)

(defn remove-one [store op-data op-id] (dissoc store op-data))

(defn updater [store op op-data op-id]
  (let [handler (case op :add-one add-one :remove-one remove-one default-handler)]
    (handler store op-data op-id)))
