package com.tapcon.game.actors.main_menu

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class PlayButton(manager : AssetManager) : GameActor() {
    private val texture = manager.get(Descriptors.gameInterface)
    private var border = texture.findRegion(Assets.InterfaceAtlas.BORDER)
    private var playIcon = texture.findRegion(Assets.InterfaceAtlas.PLAY)

    init {
        width = border.originalWidth.toFloat()
        height = border.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        drawBorder(batch)
        drawPlayIcon(batch)
    }

    private fun drawBorder(batch: Batch){
        batch.draw(border, x, y, width, height)
    }

    private fun drawPlayIcon(batch: Batch){
        batch.draw(playIcon, x + 60, y + 60, playIcon.originalWidth.toFloat(), playIcon.originalHeight.toFloat())
    }
}