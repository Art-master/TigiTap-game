package com.tigitap.game.actors

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.tigitap.game.Config
import com.tigitap.game.api.GameActor
import com.tigitap.game.data.Assets
import com.tigitap.game.data.Descriptors
import com.tigitap.game.Config.SHADOW_ANIMATION_TIME_S
import com.tigitap.game.api.Animated
import com.tigitap.game.api.AnimationType

class Shadow(val manager: AssetManager) : GameActor(), Animated {
    private val texture = manager.get(Descriptors.progressBar)
    private var region = texture.findRegion(Assets.ProgressAtlas.SQUARE)

    init {
        x = 0f
        y = 0f
        width = Config.WIDTH_GAME
        height = Config.HEIGHT_GAME

        setColor(color.r, color.g, color.b, 0f)
        touchable = Touchable.disabled
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.color = Color.WHITE

        batch.color = color
        batch.draw(region, x, y, width, height)
        batch.setColor(color.r, color.g, color.b, 1f)
    }

    override fun animate(type: AnimationType, runAfter: Runnable, duration: Float) {
        val animDuration = SHADOW_ANIMATION_TIME_S
        val action = when (type) {
            AnimationType.HIDE_FROM_SCENE -> Actions.alpha(1f, animDuration)
            AnimationType.SHOW_ON_SCENE -> {
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