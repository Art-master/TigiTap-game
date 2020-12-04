package com.tapcon.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class HeadIcon(manager: AssetManager) : GameActor() {
    private val atlas = manager.get(Descriptors.icons)
    private val regions = atlas.findRegions(Assets.IconsAtlas.ICON)
    private var region = regions.first()

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color

        region?.let {
            val width = region.originalWidth.toFloat()
            val height = region.originalHeight.toFloat()
            val x = this.x + (this.width - width) / 2
            val y = this.y + (this.height - height) / 2
            batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        }
    }

    fun setIconNumber(num: Int){
        region = regions[num]
    }
}