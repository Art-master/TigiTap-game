package com.tapcon.game.actors.game_over

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tapcon.game.api.Animated
import com.tapcon.game.api.AnimationType
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Descriptors

class Score(manager: AssetManager, private val text: String, var score: Int) : GameActor(), Animated {
    private val font = manager.get(Descriptors.scoreFont)


    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        drawName(batch)
        drawNumber(batch)
    }

    private fun drawName(batch: Batch) {
        font.color = color
        font.draw(batch, text, x, y)
    }

    private fun drawNumber(batch: Batch) {
        font.color = color
        font.draw(batch, score.toString(), x + 900, y)
    }

    override fun animate(type: AnimationType, runAfter: Runnable, duration: Float) {

    }
}