package com.tigitap.game.actors.main_menu

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.tigitap.game.api.GameActor
import com.tigitap.game.data.Assets
import com.tigitap.game.data.Descriptors

class MenuButton(manager : AssetManager, assetName: String, private var isEnabled: Boolean) : GameActor() {
    private val texture = manager.get(Descriptors.gameInterface)
    private var border = texture.findRegion(Assets.InterfaceAtlas.BORDER_MINI)
    private var icon = texture.findRegion(assetName)
    private val iconPadX = (border.originalWidth.toFloat() - icon.originalWidth.toFloat()) / 2
    private val iconPadY = (border.originalHeight.toFloat() - icon.originalHeight.toFloat()) / 2

    init {
        width = border.originalWidth.toFloat()
        height = border.originalHeight.toFloat()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = parentAlpha
        batch!!.color = color
        drawBorder(batch)
        drawPlayIcon(batch, parentAlpha)
    }

    private fun drawBorder(batch: Batch){
        batch.draw(border, x, y, width, height)
    }

    private fun drawPlayIcon(batch: Batch, parentAlpha: Float){
        val blendAlpha = parentAlpha / 3f
        color.a = if(isEnabled.not()) blendAlpha else parentAlpha
        batch.color = color
        batch.draw(icon, x + iconPadX, y + iconPadY, icon.originalWidth.toFloat(), icon.originalHeight.toFloat())
    }

    fun switch() {
        isEnabled = !isEnabled
    }
}