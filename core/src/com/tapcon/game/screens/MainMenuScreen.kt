package com.tapcon.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.badlogic.gdx.utils.Align
import com.tapcon.game.Config
import com.tapcon.game.actors.main_menu.MusicButton
import com.tapcon.game.actors.main_menu.PlayButton
import com.tapcon.game.actors.main_menu.SoundButton
import com.tapcon.game.actors.main_menu.VibrationButton
import com.tapcon.game.managers.ScreenManager

class MainMenuScreen(params: Map<ScreenManager.Param, Any>) : GameScreen(params) {

    private val playButton = PlayButton(manager)
    private val musicButton = MusicButton(manager)
    private val soundButton = SoundButton(manager)
    private val vibrationButton = VibrationButton(manager)

    init {
        buildLayout()
    }

    private fun buildLayout(){
        val table = Table().apply {
            setFillParent(true)
            row().expand()
            add(playButton).padBottom(100f).center().colspan(30)
            row().expandX().padBottom(30f)
            add(musicButton).padLeft(50f).align(Align.left)
            add(soundButton).padLeft(50f).align(Align.left).colspan(20)
            add(vibrationButton).padRight(30f).align(Align.right)
            align(Align.center)
        }
        stage.addActor(table)
    }

    override fun hide() {

    }

    override fun show() {

    }

    override fun render(delta: Float) {
        applyStages(delta)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

}