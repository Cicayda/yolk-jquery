(defproject yolk-jquery "0.6.0"
  :description "Bacon and Eggs with jQuery"
  :url "https://github.com/cicayda/yolk-jquery"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [jayq "2.3.0"]
                 [yolk "0.7.0"]]
  :plugins [[lein-cljsbuild "0.3.0"]]
  :cljsbuild {:builds
              {:main
               {:source-paths ["src"]
                :compiler {:externs ["externs/jquery-1.8.js"
                                     "externs/bootstrap.js"
                                     "externs/bacon.js"
                                     "externs/bacon.ui.js"]
                           :optimizations :advanced
                           :pretty-print false}}
               :unit
               {:source-paths ["src" "test"]
                :compiler {:output-to "resources/js/unit-test.js"
                           :optimizations :whitespace
                           :pretty-print true}}
               :demo
               {:source-paths ["src" "demo"]
                :compiler {:output-to "resources/js/demo.js"
                           :optimizations :whitespace
                           :pretty-print true}}}
              :test-commands
              {"unit" ["phantomjs"
                       "resources/js/runner.js"
                       "resources/test.html"]}})
