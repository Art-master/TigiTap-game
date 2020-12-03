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

    init {
        width = region.originalWidth.toFloat()
        height = region.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

    fun setIconNumber(num: Int){
        region = regions[num]
    }
}