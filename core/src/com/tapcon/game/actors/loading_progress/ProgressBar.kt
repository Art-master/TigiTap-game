package com.tapcon.game.actors.loading_progress

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class ProgressBar(manager: AssetManager) : GameActor() {
    private val progressAtlas = manager.get(Descriptors.progressBar)
    private val progressSquare = progressAtlas.findRegion(Assets.ProgressAtlas.SQUARE)
    private val font = manager.get(Descriptors.juraFont)

    private var progressStep = 20
    private val progressWidth = (progressSquare.originalWidth * 20) + progressStep
    var progress = 0

    init {

    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color

        batch.draw(progressSquare, x, y, width, height)

        font.color = color
        font.draw(batch, "[", x, y)
        font.draw(batch, "]", x + progressWidth, y)
        font.draw(batch, "=", x + progressWidth + 30, y)
        font.draw(batch, "$progress%", x + progressWidth + 80, y)
        drawProgress(batch)
    }

    private fun drawProgress(batch: Batch) {
        var x = x + progressStep / 2
        for (i in 0 until progress step 5) {
            batch.draw(progressSquare, x, y - progressSquare.originalHeight.toFloat(), progressSquare.originalWidth.toFloat(), progressSquare.originalHeight.toFloat())
            x += progressSquare.originalWidth.toFloat()
        }
    }
}