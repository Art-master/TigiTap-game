package com.tapcon.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.tapcon.game.api.Animated
import com.tapcon.game.api.AnimationType
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class GameTimer(manager: AssetManager) : GameActor(), Animated {
    private val atlas = manager.get(Descriptors.gameInterface)
    private val borderRegion = atlas.findRegion(Assets.InterfaceAtlas.TIME_BORDER)
    private val square = manager.get(Descriptors.progressBar).findRegion(Assets.ProgressAtlas.SQUARE)
    private var progressStep = 20
    private var timePercent = 100
    private var timerPeriod = 100L
    private var timerStep = 10

    private var onTimerExpired: (() -> Any)? = null

    private val timer: Timer = Timer()

    init {
        width = borderRegion.originalWidth.toFloat()
        height = borderRegion.originalHeight.toFloat()

        setTimer()
    }

    private fun setTimer() {
        timer.scheduleAtFixedRate(0L, timerPeriod) {
            if (timePercent > 0) timePercent--
            else {
                timer.cancel()
                onTimerExpired?.invoke()
            }
        }
    }

    fun increaseTimer(percent: Int = timerStep) {
        timePercent = if (timePercent + percent > 100) 100 else timePercent + percent
    }

    fun decreaseTimer(percent: Int = timerStep) {
        timePercent = if (timePercent - percent < 0) 0 else timePercent - percent
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color

        batch.draw(borderRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        drawProgress(batch)
    }

    private fun drawProgress(batch: Batch) {
        var x = x + progressStep / 2
        for (i in 0 until timePercent step 5) {
            val width = square.originalWidth.toFloat()
            val height = square.originalHeight.toFloat()
            batch.draw(square, x, y + 10, originX, originY, width, height, scaleX, scaleY, rotation)
            x += square.originalWidth.toFloat()
        }
    }

    fun onTimerExpired(func: () -> Any){

    }

    override fun animate(type: AnimationType, runAfter: Runnable) {
        val animDuration = 0.5f
        val action = when (type) {
            AnimationType.HIDE_FROM_SCENE -> Actions.alpha(0f, animDuration)
            AnimationType.SHOW_ON_SCENE -> {
                val color = color
                setColor(color.r, color.g, color.b, 0f)
                Actions.alpha(1f, animDuration)
            }
            else -> return
        }
        val run = Actions.run(runAfter)
        val sequence = Actions.sequence(action, run)
        addAction(sequence)
    }
}