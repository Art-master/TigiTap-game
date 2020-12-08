package com.tapcon.game.data

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader

enum class FontParam(val fontName: String, private val apply: FreetypeFontLoader.FreeTypeFontLoaderParameter) {
    MAIN("mainFont.ttf", FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
        fontFileName = Assets.Fonts.MAIN
        fontParameters.apply {
            color = Color.valueOf("41e5fe")
            shadowOffsetX = -3
            shadowOffsetY = 3
            shadowColor = Color.valueOf("41e5fe10")
            spaceX = 15
            size = 170
        }
    }),
    JURA(Assets.Fonts.MAIN, FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
        fontFileName = Assets.Fonts.MAIN
        fontParameters.apply {
            color = Color.valueOf("41e5fe")
            shadowOffsetX = -5
            shadowOffsetY = 5
            shadowColor = Color.valueOf("41e5fe01")
            spaceX = 15
            size = 70
        }
    }),
    SCORE("scoreFont.ttf", FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
        fontFileName = Assets.Fonts.MAIN
        fontParameters.apply {
            color = Color.valueOf("41e5fe")
            shadowOffsetX = -1
            shadowOffsetY = 1
            shadowColor = Color.valueOf("b5edf6")
            spaceX = 15
            size = 100
        }
    });

    fun get(): FreetypeFontLoader.FreeTypeFontLoaderParameter {
        return apply
    }
}