package com.digitap.game.api

interface Scrollable {
    fun stopMove()
    fun runMove()
    fun updateSpeed()
    fun isScrolled() = false
}