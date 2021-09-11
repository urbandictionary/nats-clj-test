(ns nats-clj.core
  (:gen-class)
  (:import (io.nats.client Nats)
           (java.nio.charset StandardCharsets)))

(defn -main
  [& args]
  (with-open [connection (Nats/connect)]
    (.publish connection "subject" (.getBytes "hello world" StandardCharsets/UTF_8))))
