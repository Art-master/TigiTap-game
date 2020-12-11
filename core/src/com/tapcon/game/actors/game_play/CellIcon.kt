package com.tapcon.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.tapcon.game.api.Animated
import com.tapcon.game.api.AnimationType
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class CellIcon(manager: AssetManager, private val scoreActor: Actor) : GameActor(), Animated {
    private val atlas = manager.get(Descriptors.gameInterface)
    private val borderRegion = atlas.findRegion(Assets.InterfaceAtlas.CELL)
    private val regions = manager.get(Descriptors.icons).findRegions(Assets.IconsAtlas.ICON)
    private var region: TextureAtlas.AtlasRegion? = null

    private var startPosition: Vector2 = Vector2()

    var isMain = false

    init {
        width = borderRegion.originalWidth.toFloat()
        height = borderRegion.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (parentAlpha > 0) color.a = parentAlpha
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
        val x = ((this.width - width) / 2) * scaleX
        val y = ((this.height - height) / 2) * scaleY
        batch.draw(region, this.x + x, this.y + y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

    fun setIconByNumber(number: Int) {
        region = regions[number]
    }

    fun resetAll() {
        region = null
        isMain = false
        color.a = 1f
        setPosition(startPosition.x, startPosition.y)
        setSize(borderRegion.originalWidth.toFloat(), borderRegion.originalHeight.toFloat())
        setScale(1f)
    }

    fun isActive() = region != null

    override fun animate(type: AnimationType, runAfter: Runnable) {
        val animDuration = 0.3f

        val action = when (type) {
            AnimationType.HIDE_FROM_SCENE -> {
                val color = color
                setColor(color.r, color.g, color.b, 1f)
                Actions.alpha(0f, 0.1f)

            }
            AnimationType.PULSE -> {
                val color = color
                setColor(color.r, color.g, color.b, 1f)
                Actions.sequence(
                        Actions.scaleTo(0.9f, 0.9f, 0.1f),
                        Actions.scaleTo(1f, 1f, 0.1f))

            }
            AnimationType.SCORE_INCREASE -> {
                val color = color
                setColor(color.r, color.g, color.b, 1f)
                Actions.parallel(
                        Actions.scaleTo(0f, 0f, animDuration),
                        Actions.moveTo(scoreActor.x, scoreActor.y, animDuration),
                        Actions.alpha(0f, animDuration))

            }
            else -> return
        }
        val run = Actions.run(runAfter)
        val sequence = Actions.sequence(action, run)
        addAction(sequence)
    }

    override fun setPosition(x: Float, y: Float) {
        super.setPosition(x, y)
        startPosition.set(x, y)

    }
}