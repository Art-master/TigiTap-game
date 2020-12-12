package com.tapcon.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction.FOREVER
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class Helper(manager: AssetManager) : GameActor() {
    private val atlas = manager.get(Descriptors.gameInterface)
    private val region = atlas.findRegion(Assets.InterfaceAtlas.HAND)
    private var action: Action? = null

    init {
        width = region.originalWidth.toFloat()
        height = region.originalHeight.toFloat()
    }

    fun watchByActor(actor: GameActor?) {
        if (actor == null) return
        removeAction(action)

        val actorX = actor.x + actor.parent.x
        val actorY = actor.y + actor.parent.y

        if (actorX < width + 30) {
            x = actorX + actor.width + width * 2
            scaleX = 1f
        } else {
            x = actorX
            scaleX = -1f
        }
        y = actorY - 30
        startLoopAnimation()
    }

    private fun startLoopAnimation() {
        val animDuration = 0.5f
        action = Actions.repeat(FOREVER,
                Actions.sequence(
                        Actions.moveBy(30f, 30f, animDuration),
                        Actions.moveTo(x, y, animDuration)))
        addAction(action)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color

        batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}