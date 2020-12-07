package com.tapcon.game.actors.game_over

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class Button(manager : AssetManager, assetName: String) : GameActor() {
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

    private fun drawBorder(batch: Batch){
        batch.draw(border, x, y, width, height)
    }

    private fun drawIcon(batch: Batch, parentAlpha: Float){
        color.a = parentAlpha
        batch.color = color
        batch.draw(icon, x + iconPadX, y + iconPadY, icon.originalWidth.toFloat(), icon.originalHeight.toFloat())
    }
}