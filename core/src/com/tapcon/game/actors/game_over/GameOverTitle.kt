package com.tapcon.game.actors.game_over

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tapcon.game.api.Animated
import com.tapcon.game.api.AnimationType
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Descriptors
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.scheduleAtFixedRate

class GameOverTitle(manager: AssetManager) : GameActor(), Animated {
    private val font = manager.get(Descriptors.mainFont)

    private var timer = Timer()
    private var timerCounter = 0
    private var maxTimerCount = 30

    private val alphabet = "ABCDEFGHIJKLMNOPPQRSTUVWXYZ"
    private val text = "GAME OVER"
    private val stringMap = HashMap<Int, Char>(text.length)

    init {
        initRandomString()
    }

    private fun initRandomString() {
        var randomNumber: Int

        for ((index, char) in text.withIndex()) {
            if (char == ' ') {
                stringMap[index] = ' '
            } else {
                randomNumber = Random().nextInt(alphabet.length)
                stringMap[index] = alphabet[randomNumber]
            }
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        drawText(batch)
    }

    private fun drawText(batch: Batch) {
        font.color = color
        val padding = 120f
        for ((index, char) in stringMap) {
            font.draw(batch, char.toString(), x + (index * padding), y)
        }
    }

    override fun animate(type: AnimationType, runAfter: Runnable, duration: Float) {
        val period = 50L
        val delay = 100L

        when (type) {
            AnimationType.SHOW_ON_SCENE -> {
                timer.scheduleAtFixedRate(delay, period) {
                    timerCounter++
                    initRandomString()
                    if (timerCounter == maxTimerCount) {
                        timer.cancel()
                        setFinalString()
                    }
                }
            }
            else -> return
        }
    }

    private fun setFinalString() {
        for ((index, char) in text.withIndex()) {
            stringMap[index] = char
        }
    }
}