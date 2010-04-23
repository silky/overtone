(ns examples.fx
  (:use overtone.live))

(refer-ugens)
; All of these are based off the compander ugen.  Of course you can just use it
; directly in your synths, but it's nice to be able to stick on 

;; This file has some demos to show you what the fx in overtone.studio.fx do.  These
;; are setup so you can experiment with the parameters by moving the mouse around.

; First a fat synth to use as our source sound
(defsynth bizzle [out-bus 10 amp 0.5]
  (out out-bus 
       (* amp 
          (+ (* (decay2 (* (impulse 10 0)
                           (+ (* (lf-saw:kr 0.3 0) -0.3) 0.3))
                        0.001)
                0.3) 
             (apply + (pulse [80 81]))))))

; Give it a try
(def b (bizzle 0))
(kill b)

; All of these are based off the compander ugen.  Of course you can just use it
; directly in your synths, but it's nice to be able to stick on 
(defsynth noise-gate-demo [in-bus 10]
  (let [source (in in-bus)]
    (compander source source (mouse-y:kr 0.0 1) 1 0.1 0.01 0.1)))

(defsynth compressor-demo [in-bus 10]
  (let [source (in in-bus)]
    (compander source source (mouse-y:kr 0.0 1) 1 0.5 0.01 0.01)))

(defsynth limiter-demo [in-bus 10]
  (let [source (in in-bus)]
    (compander source source (mouse-y:kr 0.0 1) 1 0.1 0.01 0.01)))

(defsynth sustainer-demo [in-bus 10]
  (let [source (in in-bus)]
    (compander source source (mouse-y:kr 0.0 1) 0.1 1 0.01 0.01)))


; Here is a different sample synth to try out the reverb and echo effects
(defsynth pling [out-bus 10
                 rate 1 amp 0.5]
  (out out-bus
       (* (decay (impulse rate) 0.25) 
          (* amp (lf-cub 1200 0)))))

(def p (pling 0))
(kill p)

(defsynth reverb-demo [in-bus 10]
  (free-verb (in in-bus) 0.5 (mouse-y:kr 0.0 1) 0.5))

(defsynth echo-demo [in-bus 10]
  (let [source (in in-bus)
        echo (comb-n source 0.5 (mouse-x:kr 0 1) (mouse-y:kr 0 1))]
    (+ echo (in in-bus) 0)))