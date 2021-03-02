package com.tapcon.game.actors.game_over

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.tapcon.game.api.Animated
import com.tapcon.game.api.AnimationType
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class Button(manager: AssetManager, assetName: String) : GameActor(), Animated {
    private val texture = manager.get(Descriptors.gameInterface)
    private var border = texture.findRegion(Assets.InterfaceAtlas.CELL)
    private var icon = texture.findRegion(assetName)
    private val iconPadX = (border.originalWidth.toFloat() - icon.originalWidth.toFloat()) / 2
    private val iconPadY = (border.originalHeight.toFloat() - icon.originalHeight.toFloat()) / 2

    init {
        width = border.originalWidth.toFloat()
        height = border.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        drawBorder(batch)
        drawIcon(batch, parentAlpha)
    }

    private fun drawBorder(batch: Batch) {
        batch.draw(border, x, y, originX, originY,  width, height, scaleX, scaleY, rotation)
    }

    private fun drawIcon(batch: Batch, parentAlpha: Float) {
        color.a = parentAlpha
        batch.color = color
        val width = icon.originalWidth.toFloat()
        val height = icon.originalHeight.toFloat()
        batch.draw(icon, x + iconPadX, y + iconPadY, originX, originY,  width, height, scaleX, scaleY, rotation)
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