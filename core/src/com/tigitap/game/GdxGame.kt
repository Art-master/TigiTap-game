package com.tigitap.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.tigitap.game.managers.AudioManager
import com.tigitap.game.managers.ScreenManager
import com.tigitap.game.managers.VibrationManager
import com.tigitap.game.services.ServicesController

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
