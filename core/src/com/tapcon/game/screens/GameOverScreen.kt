package com.tapcon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.tapcon.game.actors.Background
import com.tapcon.game.actors.game_over.GameOverTitle
import com.tapcon.game.actors.game_over.Button
import com.tapcon.game.api.AnimationType
import com.tapcon.game.data.Assets
import com.tapcon.game.managers.AudioManager
import com.tapcon.game.managers.ScreenManager
import com.tapcon.game.managers.VibrationManager
import com.tapcon.game.managers.VibrationManager.VibrationType.CLICK

class GameOverScreen(params: Map<ScreenManager.Param, Any>) : GameScreen(params) {
    private val replayButton = Button(manager, Assets.InterfaceAtlas.RELOAD_ICON)
    private val mainMenuButton = Button(manager, Assets.InterfaceAtlas.MENU_ICON)
    private val shareButton = Button(manager, Assets.InterfaceAtlas.SHARE_ICON)
    private val awardsButton = Button(manager, Assets.InterfaceAtlas.AWARDS_ICON)
    private val scoresButton = Button(manager, Assets.InterfaceAtlas.SCORES_ICON)
    private val gameOverTitle = GameOverTitle(manager)

    init {
        stageBackground.addActor(Background(manager))

        //stage.addActor(gameOverTitle)

        setActorsPosition()
        buildButtonsLayout()

        Gdx.input.inputProcessor = stage

        addListenersToButtons(replayButton) {
            animate(AnimationType.SCENE_TRANSFER)
            ScreenManager.setScreen(ScreenManager.Screens.GAME_SCREEN)
        }
    }

    private fun addListenersToButtons(actor: Actor, function: () -> Unit) {
        actor.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                function.invoke()
                if (AudioManager.isSoundEnable) AudioManager.play(AudioManager.SoundApp.CLICK_SOUND)
                if (VibrationManager.isVibrationEnable) VibrationManager.vibrate(CLICK)
                return super.touchDown(event, x, y, pointer, button)
            }
        })
    }

    private fun setActorsPosition(){

    }

    private fun buildButtonsLayout() {
        val tableTitle = Table().apply {
            setFillParent(true)
            setDebug(true)
            row()
            add(gameOverTitle).width(600f).padTop(100f)
            align(Align.center)
            align(Align.top)
        }

        val table = Table().apply {
            setFillParent(true)
            row().padBottom(50f).center()
            add(replayButton).padRight(100f)
            add(mainMenuButton).padRight(100f)
            add(shareButton).padRight(100f)
            add(awardsButton).padRight(100f)
            add(scoresButton)
            align(Align.center).align(Align.bottom)
        }
        stage.addActor(tableTitle)
        stage.addActor(table)
    }

    override fun hide() {

    }

    override fun show() {

    }

    override fun render(delta: Float) {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        val bufferBitMv = if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT or bufferBitMv)
        applyStages(delta)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

}