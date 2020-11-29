package com.tapcon.game.actors.main_menu

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class GameName(manager : AssetManager) : GameActor() {
    private val texture = manager.get(Descriptors.gameInterface)
    private var region = texture.findRegion(Assets.InterfaceAtlas.GAME_NAME)

    init {
        width = region.originalWidth.toFloat()
        height = region.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        drawBorder(batch)
    }

    private fun drawBorder(batch: Batch){
        batch.draw(region, x, y, width, height)
    }
}