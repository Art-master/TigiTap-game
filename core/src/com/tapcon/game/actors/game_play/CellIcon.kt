package com.tapcon.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class CellIcon(manager: AssetManager) : GameActor() {
    private val atlas = manager.get(Descriptors.gameInterface)
    private val region = atlas.findRegion(Assets.InterfaceAtlas.PLAY)

    init {
        width = region.originalWidth.toFloat()
        height = region.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}