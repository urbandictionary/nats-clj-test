(ns nats-clj.core
  (:gen-class)
  (:import (io.nats.client Nats MessageHandler)
           (java.nio.charset StandardCharsets)
           (java.time Duration)))

(defn -main
  [& args]
  (with-open [connection (Nats/connect)]
    (.publish connection "subject" (.getBytes "hello world" StandardCharsets/UTF_8))))

(defn incoming
  [& args]
  (with-open [connection (Nats/connect)]
    (let [subscription (.subscribe connection "subject")]
      (prn (String. (.getData (.nextMessage subscription (Duration/ofMillis 5000))))))))

(defn dispatcher
  [& args]
  (with-open [connection (Nats/connect)]
    (let [dispatcher (.createDispatcher connection
                                        (reify MessageHandler
                                          (onMessage [_ message]
                                            (prn (String. (.getData message))))))]
      (.subscribe dispatcher "subject")
      (Thread/sleep Long/MAX_VALUE))))