package com.tapcon.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class CellIcon(manager: AssetManager) : GameActor() {
    private val atlas = manager.get(Descriptors.gameInterface)
    private val borderRegion = atlas.findRegion(Assets.InterfaceAtlas.CELL)
    private val regions = manager.get(Descriptors.icons).findRegions(Assets.IconsAtlas.ICON)
    private var region: TextureAtlas.AtlasRegion? = null
    var isMain = false

    init {
        width = borderRegion.originalWidth.toFloat()
        height = borderRegion.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color

        region?.let {
            batch.draw(borderRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
            drawCellIcon(batch, region)
        }
    }

    private fun drawCellIcon(batch: Batch, region: TextureAtlas.AtlasRegion?) {
        if (region == null) return
        val width = region.originalWidth.toFloat()
        val height = region.originalHeight.toFloat()
        val x = (this.width - width) / 2
        val y = (this.height - height) / 2
        batch.draw(region, this.x + x, this.y + y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

    fun setIconByNumber(number: Int) {
        region = regions[number]
    }

    fun clearInfo() {
        region = null
        isMain = false
    }

    fun isActive() = region != null
}