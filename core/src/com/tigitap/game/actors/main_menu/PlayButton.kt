package com.tigitap.game.actors.main_menu

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.tigitap.game.api.Animated
import com.tigitap.game.api.AnimationType
import com.tigitap.game.api.GameActor
import com.tigitap.game.data.Assets
import com.tigitap.game.data.Descriptors

class PlayButton(manager : AssetManager) : GameActor(), Animated {
    private val texture = manager.get(Descriptors.gameInterface)
    private var border = texture.findRegion(Assets.InterfaceAtlas.BORDER)
    private var playIcon = texture.findRegion(Assets.InterfaceAtlas.PLAY)

    init {
        width = border.originalWidth.toFloat()
        height = border.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = if(parentAlpha < 1f) parentAlpha else color.a
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

    override fun animate(type: AnimationType, runAfter: Runnable, duration: Float) {
        val animDuration = 0.3f

        val action = when (type) {
            AnimationType.HIDE_FROM_SCENE -> {

                val color = color
                setColor(color.r, color.g, color.b, 1f)
                Actions.alpha(0f, animDuration)
            }
            else -> return
        }
        val run = Actions.run(runAfter)
        val sequence = Actions.sequence(action, run)
        addAction(sequence)
    }
}