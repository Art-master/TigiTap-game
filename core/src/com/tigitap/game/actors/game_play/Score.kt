package com.tigitap.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.tigitap.game.Config
import com.tigitap.game.api.Animated
import com.tigitap.game.api.AnimationType
import com.tigitap.game.api.GameActor
import com.tigitap.game.data.Descriptors

class Score(manager: AssetManager, var score: Int = 0) : GameActor(), Animated {
    private val font = manager.get(Descriptors.scoreFont)

    init {
        x = Config.WIDTH_GAME - 200
        y = Config.HEIGHT_GAME - 100
    }


    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        drawBorder(batch)
        drawNumber(batch)
    }

    private fun drawBorder(batch: Batch) {
        font.color = color
    }

    private fun drawNumber(batch: Batch) {
        font.color = color
        font.draw(batch, score.toString(), x, y)
    }

    override fun animate(type: AnimationType, runAfter: Runnable, duration: Float) {
        val animDuration = 0.1f

        val action = when (type) {
            AnimationType.PULSE -> {
                val color = color
                setColor(color.r, color.g, color.b, 1f)
                Actions.sequence(
                        Actions.scaleTo(0.9f, 0.9f, animDuration),
                        Actions.scaleTo(1f, 1f, animDuration))

            }
            else -> return
        }
        val run = Actions.run(runAfter)
        val sequence = Actions.sequence(action, run)
        addAction(sequence)
    }
}