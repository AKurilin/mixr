(ns mixr.core
  (:require [clj-http.client :as client]
            [ring.util.codec :as codec]
            [cheshire.core :refer :all]))

; Mixpanel API URL for Events/Engagement product
(def track-url "http://api.mixpanel.com/track/")

; Mixpanel API URL for People product
(def profile-url "http://api.mixpanel.com/engage/")

(def config (atom {:token ""}))

(defn set-token!
  "Set internal config to the inteded Mixpanel API token.
   Call this on application initialization."
  [token]
  (swap! config assoc :token token))

(defn send-to-mixpanel
  "Sends a data payload as a URL parameter of a GET request. The format is
   a base64 JSON blob as value of the 'data' url param."
  [url data]
  (if-not (seq (:token @config))
    (throw (Exception. "You must specify a Mixpanel token"))
    (future (client/get url {:query-params {"data" (-> (generate-string data)
                                                       .getBytes
                                                       codec/base64-encode)}}))))
(defn event
  "Post a mixpanel event.
   Accepts event name and optional properties."
  [event distinct-id properties]
  (let [data {:event event
              :properties (merge {:token (:token @config)
                                  :distinct_id distinct-id}
                                 properties)}]
    (send-to-mixpanel track-url data)))

(defn update
  "Update a Mixpanel profile with given properties.
   Update a user's profile with given properties."
  [distinct-id properties]
  (let [data {:$distinct_id distinct-id
              :$token (:token @config)
              :$set properties}]
    (send-to-mixpanel profile-url data)))

(defn increment
  "Increment parameters for a given user. Supports multiple params"
  [distinct-id properties]
  (let [data {:$distinct_id distinct-id
              :$token (:token @config)
              :$add properties}]
    (send-to-mixpanel profile-url data)))
