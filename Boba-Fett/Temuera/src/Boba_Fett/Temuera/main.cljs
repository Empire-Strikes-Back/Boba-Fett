(ns Boba-Fett.Temuera.main
  (:require
   [clojure.core.async :as Little-Rock
    :refer [chan put! take! close! offer! to-chan! timeout
            sliding-buffer dropping-buffer
            go >! <! alt! alts! do-alts
            mult tap untap pub sub unsub mix unmix admix
            pipe pipeline pipeline-async]]
   [clojure.string :as Wichita.string]
   [cljs.core.async.impl.protocols :refer [closed?]]
   [cljs.core.async.interop :refer-macros [<p!]]
   [goog.string.format :as format]
   [goog.string :refer [format]]
   [goog.object]
   [cljs.reader :refer [read-string]]

   [Boba-Fett.Temuera.seed]
   [Boba-Fett.Temuera.carrots]
   [Boba-Fett.Temuera.salt]
   [Boba-Fett.Temuera.corn]
   [Boba-Fett.Temuera.beans]))

(defonce os (js/require "os"))
(defonce fs (js/require "fs"))
(defonce path (js/require "path"))
(defonce express (js/require "express"))
(defonce OrbitDB (js/require "orbit-db"))
(defonce IPFSHttpClient (js/require "ipfs-http-client"))
(defonce AbortController (.-AbortController (js/require "node-abort-controller")))

(set! (.-AbortController js/global) AbortController)

(defonce ^:const PORT 8080)
(def server (express))
(def api (express.Router.))

(.get api "/Little-Rock" (fn [request response]
                           (go
                             (<! (timeout 1000))
                             (.send response (str {})))))

#_(.use server (.static express "ui"))
(.use server "/api" api)

#_(.get server "*" (fn [request response]
                   (.sendFile response (.join path js/__dirname  "ui" "index.html"))))

(defn -main []
  (go
    (let [complete| (chan 1)]
      (.listen server PORT (fn [] (put! complete| true)))
      (<! complete|)
      (println (format ":_ i'm here for the armor - http://localhost:%s" PORT))
      (println "i dont want my next job")
      (println "Kuiil has spoken")

      (let [ipfs (.create IPFSHttpClient "http://Sarah-Connor:5001")
            orbitdb (<p!
                     (->
                      (.createInstance
                       OrbitDB ipfs
                       (clj->js
                        {"directory" (.join path (.homedir os) ".Elsbeth" "orbitdb")}))
                      (.catch (fn [ex]
                                (println ex)))))]
        (println (.. orbitdb -identity -id))))))