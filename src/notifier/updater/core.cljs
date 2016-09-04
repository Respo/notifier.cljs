
(ns notifier.updater.core
  (:require [clojure.string :as string]))

(defn remove-one [store op-data op-id] (dissoc store op-data))

(defn add-one [store op-data op-id]
  (assoc
    store
    op-id
    {:time op-id,
     :icon nil,
     :title "demo title",
     :id op-id,
     :body "demo body"}))

(defn default-handler [store op-data op-id] store)

(defn updater [store op op-data op-id]
  (let [handler (case
                  op
                  :add-one
                  add-one
                  :remove-one
                  remove-one
                  default-handler)]
    (handler store op-data op-id)))
