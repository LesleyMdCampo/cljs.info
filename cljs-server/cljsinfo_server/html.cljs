(ns cljsinfo-server.html
  (:require-macros [hiccups.core :as hiccups])
  (:require
    hiccups.runtime
    [cljsinfo-server.config :refer [config]]
    [cljsinfo-server.util :as util]))

(def fs (js/require "fs"))

;;------------------------------------------------------------------------------
;; Hashed Assets
;;------------------------------------------------------------------------------

(def assets
  (if (.existsSync fs "assets.json")
    (js->clj (js/require "./assets.json"))
    {}))

(defn- asset [f]
  (get assets f f))

;;------------------------------------------------------------------------------
;; Site Head / Footer
;;------------------------------------------------------------------------------

(hiccups/defhtml site-head [page-title]
  "<!doctype html>"
  "<html lang='en-us'>"
  [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
    [:title page-title]
    [:meta {:name "viewport" :content "width=device-width"}]
    [:link {:rel "shortcut icon" :href "favicon.png" :type "image/png"}]
    [:link {:rel "stylesheet" :href (asset "css/main.min.css")}]]
  "<body>")

(hiccups/defhtml site-footer
  ([] (site-footer nil))
  ([init-page]
    [:script {:src "js/libs/jquery-2.1.1.min.js"}]
    (if (:minified-client config)
      [:script {:src (asset "js/client.min.js")}]
      [:script {:src (asset "js/client.js")}])
    (if init-page
      [:script "CLJSINFO.init('" init-page "');"])
    "</body>"
    "</html>"))

;;------------------------------------------------------------------------------
;; Homepage
;;------------------------------------------------------------------------------

(hiccups/defhtml homepage []
  (site-head "Home")
  [:div.wrapper-cc101
    [:div.js-48d1f "JavaScript"]
    [:div.made-6bccb "made"]

    [:div.simple-ef853 "Simple"]
    ;;[:h1.title-0b151 [:span "JavaScript"] [:span.made-6bccb "made"] "Simple"]
    ]
  ;;[:h1 "Welcome to ClojureScript.info!"]
  (site-footer "homepage"))

;;------------------------------------------------------------------------------
;; Doc Pages
;;------------------------------------------------------------------------------

(hiccups/defhtml doc-body [d]
  [:div "docs body"]
  )

(hiccups/defhtml doc-page [d]
  (site-head "cljs.core/foo")
  (doc-body d)
  (site-footer))
