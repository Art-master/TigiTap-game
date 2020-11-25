package com.tapcon.game.data

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class Descriptors {
    companion object {
        val background = AssetDescriptor(Assets.BackgroundTexture.NAME, Texture::class.java)
        val progressBar = AssetDescriptor(Assets.ProgressAtlas.NAME, TextureAtlas::class.java)
        val icons = AssetDescriptor(Assets.IconsAtlas.NAME, TextureAtlas::class.java)
        val gameInterface = AssetDescriptor(Assets.InterfaceAtlas.NAME, TextureAtlas::class.java)

        val mainFont = AssetDescriptor(FontParam.MAIN.fontName, BitmapFont::class.java, FontParam.MAIN.get())
        val scoreFont = AssetDescriptor(FontParam.SCORE.fontName, BitmapFont::class.java, FontParam.SCORE.get())
    }
}