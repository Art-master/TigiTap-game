package com.tapcon.game.actors.game_over

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.tapcon.game.api.Animated
import com.tapcon.game.api.AnimationType
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Descriptors

class GameOverTitle(manager : AssetManager) : GameActor(), Animated {
    private val font = manager.get(Descriptors.juraFont)

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        drawText(batch)
    }

    private fun drawText(batch: Batch){
        font.color = color
        font.draw(batch, "GAME OVER",  x, y)
    }

    override fun animate(type: AnimationType, runAfter: Runnable) {
        val animDuration = 100f
        val action = when (type) {
            AnimationType.HIDE_FROM_SCENE -> Actions.alpha(1f, animDuration)
            AnimationType.SHOW_ON_SCENE -> {
                val color = color
                setColor(color.r, color.g, color.b, 1f)
                Actions.alpha(0f, animDuration)
            }
            else -> return
        }
        val run = Actions.run(runAfter)
        val sequence = Actions.sequence(action, run)
        addAction(sequence)
    }
}