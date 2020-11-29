package com.tapcon.game.actors

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.Color
import com.tapcon.game.Config
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Descriptors

class Background(manager : AssetManager) : GameActor() {
    private val background = manager.get(Descriptors.background)

    init {
        width = Config.WIDTH_GAME
        height = Config.HEIGHT_GAME
        x = 0f
        y = 0f
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.color = Color.WHITE
        drawBackground(batch)
    }

    private fun drawBackground(batch: Batch){
        batch.draw(background, x, y, width, height)
    }
}