
Notifications for Respo
----

> Pop notifications with Respo Effects

### Usage

[![Clojars Project](https://img.shields.io/clojars/v/respo/notifier.svg)](https://clojars.org/respo/notifier)

```clojure
[respo/notifier "0.1.3"]
```

```clojure
(require '[notifier.comp.notifications :refer [nofity!]])

(def notifications {
  "q42342" {:title "title demo"
            :body "body demo"
            :icon "icon url demo"
            :id "q42342"}
})

; pop notifications with effects
(effect-notify notifications {:when-inactive? true, on-close (fn [noti])})
```

### Workflow

Workflow https://github.com/mvc-works/calcit-workflow

### License

MIT
