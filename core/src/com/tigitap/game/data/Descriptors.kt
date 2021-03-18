package com.tigitap.game.data

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class Descriptors {
    companion object {
        private val textureParams = TextureLoader.TextureParameter().apply {
            genMipMaps = true
            loadedCallback = AssetLoaderParameters.LoadedCallback { assetManager, fileName, _ ->
                run {
                    assetManager.get<Texture>(fileName).setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest)
                }
            }
        }

        val background = AssetDescriptor(Assets.BackgroundTexture.NAME, Texture::class.java, textureParams)
        val progressBar = AssetDescriptor(Assets.ProgressAtlas.NAME, TextureAtlas::class.java)
        val icons = AssetDescriptor(Assets.IconsAtlas.NAME, TextureAtlas::class.java)
        val gameInterface = AssetDescriptor(Assets.InterfaceAtlas.NAME, TextureAtlas::class.java)

        val mainFont = AssetDescriptor(FontParam.MAIN.fontName, BitmapFont::class.java, FontParam.MAIN.get())
        val juraFont = AssetDescriptor(FontParam.JURA.fontName, BitmapFont::class.java, FontParam.JURA.get())
        val scoreFont = AssetDescriptor(FontParam.SCORE.fontName, BitmapFont::class.java, FontParam.SCORE.get())
    }
}