package com.tapcon.game.data

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader

enum class FontParam(val fontName: String, private val apply: FreetypeFontLoader.FreeTypeFontLoaderParameter) {
    MAIN("mainFont.ttf", FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
        fontFileName = Assets.Fonts.MAIN
        fontParameters.color = Color.YELLOW
        fontParameters.shadowOffsetX = -5
        fontParameters.shadowOffsetY = 5
        fontParameters.spaceX = 15
        fontParameters.size = 100
    }),
    SCORE("scoreFont.ttf", FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
        fontFileName = Assets.Fonts.FONT
        fontParameters.color = Color.YELLOW
        fontParameters.shadowOffsetX = -5
        fontParameters.shadowOffsetY = 5
        fontParameters.spaceX = 15
        fontParameters.size = 100
    });

    fun get(): FreetypeFontLoader.FreeTypeFontLoaderParameter {
        return apply
    }
}