package com.tapcon.game

import com.badlogic.gdx.Game
import com.run.cookie.run.game.services.ServicesController
import com.tapcon.game.managers.ScreenManager

class GdxGame(private val controller: ServicesController) : Game() {

    override fun create() {
        ScreenManager.game = this
        ScreenManager.globalParameters[ScreenManager.Param.SERVICES_CONTROLLER] = controller
        ScreenManager.setScreen(ScreenManager.Screens.LOADING_SCREEN)
        controller.signIn()
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {}
}
