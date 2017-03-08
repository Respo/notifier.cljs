
(set-env!
  :asset-paths #{"assets"}
  :resource-paths #{"src"}
  :dependencies '[[org.clojure/clojure       "1.8.0"       :scope "provided"]
                  [org.clojure/clojurescript "1.9.216"     :scope "provided"]
                  [adzerk/boot-cljs          "1.7.228-1"   :scope "provided"]
                  [adzerk/boot-reload        "0.4.12"      :scope "provided"]
                  [cirru/boot-stack-server   "0.1.30"      :scope "provided"]
                  [adzerk/boot-test          "1.1.2"       :scope "provided"]
                  [andare                    "0.5.0"       :scope "provided"]
                  [cumulo/shallow-diff       "0.1.3"       :scope "provided"]
                  [fipp                      "0.6.9"       :scope "provided"]
                  [respo/ui                  "0.1.9"       :scope "provided"]
                  [respo                     "0.3.39"      :scope "provided"]
                  [mvc-works/hsl             "0.1.2"]])

(require '[adzerk.boot-cljs   :refer [cljs]]
         '[adzerk.boot-reload :refer [reload]])

(def +version+ "0.1.1")

(task-options!
  pom {:project     'respo/notifier
       :version     +version+
       :description "Notifications Layer for Respo apps"
       :url         "https://github.com/Respo/notifier"
       :scm         {:url "https://github.com/Respo/notifier"}
       :license     {"MIT" "http://opensource.org/licenses/mit-license.php"}})

(deftask dev []
  (comp
    (watch)
    (reload :on-jsload 'notifier.main/on-jsload
            :cljs-asset-path ".")
    (cljs :compiler-options {:language-in :ecmascript5})
    (target :no-clean true)))

(deftask build-advanced []
  (comp
    (cljs :optimizations :advanced
          :compiler-options {:language-in :ecmascript5})
    (target :no-clean true)))

(deftask build []
  (comp
    (pom)
    (jar)
    (install)
    (target)))

(deftask deploy []
  (set-env!
    :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"}]))
  (comp
    (build)
    (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))
