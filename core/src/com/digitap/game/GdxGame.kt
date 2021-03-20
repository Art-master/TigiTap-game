package com.digitap.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.digitap.game.managers.AudioManager
import com.digitap.game.managers.ScreenManager
import com.digitap.game.managers.VibrationManager
import com.digitap.game.services.ServicesController

class GdxGame(private val controller: ServicesController) : Game() {

    override fun create() {
        ScreenManager.game = this
        ScreenManager.globalParameters[ScreenManager.Param.SERVICES_CONTROLLER] = controller

        val manager = AssetManager()
        ScreenManager.setGlobalParameter(ScreenManager.Param.ASSET_MANAGER, manager)
        ScreenManager.setScreen(ScreenManager.Screens.LOADING_SCREEN)
        controller.signIn()
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
        AudioManager.disposeAll()
        VibrationManager.cancel()
    }
}
