package com.tapcon.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tapcon.game.Config
import com.tapcon.game.api.Animated
import com.tapcon.game.api.AnimationType
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Descriptors

class Score(manager: AssetManager, var score: Int = 0) : GameActor(), Animated {
    private val font = manager.get(Descriptors.scoreFont)


    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        drawBorder(batch)
        drawNumber(batch)
    }

    private fun drawBorder(batch: Batch) {
        font.color = color
        //font.draw(batch, text, x, y)
    }

    private fun drawNumber(batch: Batch) {
        font.color = color
        font.draw(batch, score.toString(), Config.WIDTH_GAME - 200, Config.HEIGHT_GAME - 100)
    }

    override fun animate(type: AnimationType, runAfter: Runnable) {

    }
}