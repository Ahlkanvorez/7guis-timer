(ns timer.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(defn time-since [start]
  (/ (- (js/Date.now) start) 1000.0))

(defn progress-bar [progress total]
  [:div {:style {:background-color :white
                 :border "1px solid gray"
                 :flex "1 0"
                 :display :flex}}
   [:div {:style {:background-color "#89cff0"
                  :width (str (* 100 (/ progress total)) "%")}}]])

(defn bar-timer [progress total]
  [:div {:style {:display :flex :justify-content :space-between}}
   [:span {:style {:margin-right "10px"}} "Elapsed Time:"]
   [progress-bar progress total]])

(defn numeric-timer [elapsed] [:span (str (int elapsed) "s")])

(defn duration-slider [duration]
  [:div {:style {:display :flex :justify-content :space-between}}
   [:span {:style {:margin-right "10px"}} "Duration: "]
   [:input {:type :range
            :min 1 :max 100 :step 1
            :value @duration
            :on-change #(reset! duration (.. % -target -value))}]])

(defn timer []
  (let [elapsed (r/atom 0)
        started (r/atom (js/Date.now))
        duration (r/atom 10)]
    (fn []
      (when (< @elapsed @duration)
        (js/setTimeout #(reset! elapsed (time-since @started)) 10))
      [:div {:style {:display :flex :justify-content :center}}
       [:div {:style {:display :flex :flex-direction :column}}
        [bar-timer @elapsed @duration]
        [numeric-timer @elapsed]
        [duration-slider duration]
        [:input {:type :button
                 :value :Reset
                 :on-click #(do (reset! started (js/Date.now))
                                (reset! elapsed 0))}]]])))

(defn mount-root []
  (rd/render [timer]
             (js/document.getElementById "app-root")))
