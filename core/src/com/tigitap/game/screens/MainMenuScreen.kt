package com.tigitap.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.tigitap.game.actors.Background
import com.tigitap.game.actors.DigitalMatrix
import com.tigitap.game.actors.main_menu.GameName
import com.tigitap.game.actors.main_menu.MenuButton
import com.tigitap.game.actors.main_menu.PlayButton
import com.tigitap.game.api.AnimationType
import com.tigitap.game.data.Assets.InterfaceAtlas.Companion.MUSIC_ICON
import com.tigitap.game.data.Assets.InterfaceAtlas.Companion.SOUND_ICON
import com.tigitap.game.data.Assets.InterfaceAtlas.Companion.VIBRATION_ICON
import com.tigitap.game.managers.AudioManager
import com.tigitap.game.managers.ScreenManager
import com.tigitap.game.managers.VibrationManager
import com.tigitap.game.managers.VibrationManager.VibrationType.CLICK

class MainMenuScreen(params: Map<ScreenManager.Param, Any>) : GameScreen(params) {

    private val playButton = PlayButton(manager)
    private val musicButton = MenuButton(manager, MUSIC_ICON, AudioManager.isMusicEnable)
    private val soundButton = MenuButton(manager, SOUND_ICON, AudioManager.isSoundEnable)
    private val vibrationButton = MenuButton(manager, VIBRATION_ICON, VibrationManager.isVibrationEnable)
    private val gameName = GameName(manager)
    private val digitalMatrix = DigitalMatrix(manager)

    init {
        stageBackground.addActor(Background(manager))
        stage.addActor(digitalMatrix)
        buildLayout()
        digitalMatrix.animate()

        Gdx.input.inputProcessor = stage

        addListenersToButtons(musicButton) {
            AudioManager.switchMusicSetting()
            return@addListenersToButtons true
            //if(AudioManager.isMusicEnable) AudioManager.play(AudioManager.MusicApp.GAME_MUSIC) //TODO Game Music
        }

        addListenersToButtons(soundButton) {
            AudioManager.switchSoundSetting()
            return@addListenersToButtons true
        }

        addListenersToButtons(vibrationButton) {
            VibrationManager.switchVibrationSetting()
            return@addListenersToButtons true
        }

        var isPlayButtonPressed = false

        addListenersToButtons(playButton) {
            if (isPlayButtonPressed) return@addListenersToButtons false
            isPlayButtonPressed = true


            playButton.animate(AnimationType.HIDE_FROM_SCENE, Runnable {
                ScreenManager.setScreen(ScreenManager.Screens.GAME_SCREEN)
            })
            return@addListenersToButtons true
        }
    }

    private fun addListenersToButtons(actor: Actor, function: () -> Boolean) {
        actor.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                function.invoke()
                if (actor is MenuButton) actor.switch()
                if (AudioManager.isSoundEnable) AudioManager.play(AudioManager.SoundApp.CLICK_SOUND)
                if (VibrationManager.isVibrationEnable) VibrationManager.vibrate(CLICK)
                return super.touchDown(event, x, y, pointer, button)
            }
        })
    }

    private fun buildLayout() {
        val table = Table().apply {
            setFillParent(true)
            row().expand()
            add(gameName).center().colspan(30)
            row().expandX()
            add(playButton).padBottom(100f).center().colspan(30)
            row().expandX().padBottom(100f)
            add(musicButton).padLeft(100f).align(Align.left)
            add(soundButton).padLeft(50f).align(Align.left).colspan(20)
            add(vibrationButton).padRight(100f).align(Align.right)
            align(Align.center)
        }
        stage.addActor(table)
    }

    override fun hide() {

    }

    override fun show() {

    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
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