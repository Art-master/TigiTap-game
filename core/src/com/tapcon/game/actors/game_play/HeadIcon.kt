package com.tapcon.game.actors.game_play

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.tapcon.game.Config
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors

class HeadIcon(manager: AssetManager,
               private val bracketLeft: Bracket,
               private val bracketRight: Bracket) : GameActor() {

    private val atlas = manager.get(Descriptors.icons)
    private val allRegions = atlas.findRegions(Assets.IconsAtlas.ICON)
    private val regionWidth = allRegions.first().regionWidth.toFloat()
    private var regions = HashMap<Int, TextureAtlas.AtlasRegion>()
    private var checkedIndexes = ArrayList<Int>()
    private val padding = 100f

    private fun changePositions() {
        var width = 0f
        regions.values.forEach{ width += it.regionWidth + padding }

        setSize(width, bracketLeft.height)

        val headX = (Config.WIDTH_GAME - width) / 2
        val headY = Config.HEIGHT_GAME - bracketLeft.height - 50f

        bracketLeft.setPosition(headX - padding, headY)
        setPosition(headX, headY)

        bracketRight.scaleX = -1f
        bracketRight.setPosition(tailX, headY)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (parentAlpha < 1) color.a = parentAlpha

        var x = this.x

        for ((i, it) in regions) {
            val width = it.originalWidth.toFloat()
            val height = it.originalHeight.toFloat()

            val y = this.y + (this.height - height) / 2

            if (checkedIndexes.contains(i) && parentAlpha == 1f) color.a = 0.3f
            else color.a = 1f

            batch!!.color = color
            batch.draw(it, x, y, originX, originY, width, height, scaleX, scaleY, rotation)

            x += width + padding
        }
    }

    fun setIconNumbers(numbers: Array<Int>) {
        regions.clear()
        for (number in numbers) regions[number] = allRegions[number]
        changePositions()

    }

    fun addIconNumber(number: Int) {
        regions[number] = allRegions[number]
        changePositions()
    }

    fun clearAll() {
        regions.clear()
        checkedIndexes.clear()
    }

    fun clickByIcon(region: TextureAtlas.AtlasRegion?) {
        val index = regions.values.indexOf(region)
        if (index != -1) checkedIndexes.add(index)
    }

    fun clickByIcon(iconNumber: Int) {
        checkedIndexes.add(iconNumber)
    }

    fun isAllChecked() = checkedIndexes.size == regions.size

    fun contains(iconNum: Int) = regions.containsKey(iconNum)
}