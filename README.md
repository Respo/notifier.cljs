
Notifications Layer for Respo
----

Since Notifications API does not fit into Respo's declarative pattern, I have to use mutable states to pop notifications. So, be special...

### Usage

[![Clojars Project](https://img.shields.io/clojars/v/respo/notifier.svg)](https://clojars.org/respo/notifier)

```clojure
[respo/notifier "0.1.1"]
```

```clojure
(require '[notifier.comp.notifications :refer [nofity!]])

(def notifications {
  "q42342" {:title "title demo"
            :body "body demo"
            :icon "icon url demo"
            :id "q42342"}
})

; rerender on every change to detect new ids
(notify! notifications)
```

### Develop

https://github.com/mvc-works/stack-workflow

### License

MIT
