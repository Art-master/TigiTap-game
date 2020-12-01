package com.tapcon.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class CellIcon(manager: AssetManager) : GameActor() {
    private val atlas = manager.get(Descriptors.gameInterface)
    private val borderRegion = atlas.findRegion(Assets.InterfaceAtlas.CELL)
    private val region = atlas.findRegion(Assets.InterfaceAtlas.PLAY)

    init {
        width = borderRegion.originalWidth.toFloat()
        height = borderRegion.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        batch.draw(borderRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        //drawCellIcon(batch)
    }

    private fun drawCellIcon(batch: Batch) {
        val width = region.originalWidth.toFloat()
        val height = region.originalHeight.toFloat()
        batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}