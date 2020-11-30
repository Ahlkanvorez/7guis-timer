(ns timer.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(defn mount-root []
  (rd/render [:h1 "Timer"]
             (js/document.getElementById "app-root")))
