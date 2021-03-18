package com.tigitap.game.actors.game_over

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tigitap.game.api.Animated
import com.tigitap.game.api.AnimationType
import com.tigitap.game.api.GameActor
import com.tigitap.game.data.Descriptors

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