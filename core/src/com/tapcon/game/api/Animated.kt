package com.tapcon.game.api

interface Animated {
    fun animate(type: AnimationType, runAfter: Runnable = Runnable {  })
}