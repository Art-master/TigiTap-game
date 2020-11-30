package com.tapcon.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.tapcon.game.services.ServicesController
import com.tapcon.game.managers.ScreenManager

class GdxGame(private val controller: ServicesController) : Game() {

    override fun create() {
        ScreenManager.game = this
        ScreenManager.globalParameters[ScreenManager.Param.SERVICES_CONTROLLER] = controller

        val manager = AssetManager()
        ScreenManager.setGlobalParameter(ScreenManager.Param.ASSET_MANAGER, manager)
        ScreenManager.setScreen(ScreenManager.Screens.LOADING_SCREEN)
        //controller.signIn() TODO time fir sign in
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {}
}
