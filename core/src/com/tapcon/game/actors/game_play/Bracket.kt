package com.tapcon.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.tapcon.game.api.Animated
import com.tapcon.game.api.AnimationType
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class Bracket(manager: AssetManager) : GameActor(), Animated {
    private val atlas = manager.get(Descriptors.gameInterface)
    private val bracket = atlas.findRegion(Assets.InterfaceAtlas.BRACKET)

    init {
        width = bracket.originalWidth.toFloat()
        height = bracket.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if(parentAlpha < 1) color.a = parentAlpha
        batch!!.color = color
        batch.draw(bracket, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

    override fun animate(type: AnimationType, runAfter: Runnable) {
        val animDuration = 0.1f

        val action = when (type) {
            AnimationType.PULSE -> {
                Actions.repeat(2,
                        Actions.sequence(
                                Actions.alpha(0f, animDuration),
                                Actions.alpha(1f, animDuration)))

            }
            else -> return
        }
        val run = Actions.run(runAfter)
        val sequence = Actions.sequence(action, run)
        addAction(sequence)
    }
}