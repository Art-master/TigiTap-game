package com.tapcon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.tapcon.game.Config
import com.tapcon.game.actors.Background
import com.tapcon.game.actors.DigitalBlow
import com.tapcon.game.actors.game_play.*
import com.tapcon.game.api.AnimationType
import com.tapcon.game.data.Assets
import com.tapcon.game.data.Descriptors
import com.tapcon.game.managers.AudioManager
import com.tapcon.game.managers.ScreenManager
import com.tapcon.game.managers.VibrationManager
import com.tapcon.game.managers.VibrationManager.VibrationType.CLICK
import kotlin.random.Random

class GamePlayScreen(params: Map<ScreenManager.Param, Any>) : GameScreen(params) {

    private var isFirstAppRunning = true //params[ScreenManager.Param.FIRST_APP_RUN] as Boolean
    //TODO

    private val atlas = manager.get(Descriptors.icons)
    private val iconsRegions = atlas.findRegions(Assets.IconsAtlas.ICON)

    private val bracketLeft = Bracket(manager)
    private val bracketRight = Bracket(manager)
    private val headIcon = HeadIcon(manager)
    private val timer = GameTimer(manager)
    private val scoreActor = Score(manager)
    private val helper = Helper(manager)
    private val iconsGroup = CellsGroup()

    private val digitalBlow = DigitalBlow(manager, scoreActor)

    private val rowCount = 3
    private val collCount = 7

    private var numIconsOnScene = 1
    private var numMainIcon = -1

    private var score = 0

    private val iconsActors = Array(rowCount * collCount) { CellIcon(manager, scoreActor) }

    private val activeMap = HashMap<Int, CellIcon>(iconsRegions.size)

    private inline fun <T> Array<out T>.forEach(action: (T, Int) -> Unit) {
        for ((index, element) in this.withIndex()) action(element, index)
    }

    init {
        stageBackground.addActor(Background(manager))
        initActorsPositions()

        stage.apply {
            addActor(bracketLeft)
            addActor(bracketRight)
            addActor(timer)
            addActor(headIcon)
            addActor(iconsGroup)
            addActor(digitalBlow)
            addActor(scoreActor)
            if (isFirstAppRunning) addActor(helper)
        }

        initIconsMatrix()
        setIconsGroupPosition()

        numMainIcon = setNewActiveIcon()// Test
        setMainIcon(numMainIcon)

        if (isFirstAppRunning) helper.watchByActor(activeMap[numMainIcon])

        startControlGameOver()

        Gdx.input.inputProcessor = stage
    }

    private fun initIconsMatrix() {
        var x = 0f
        var y = 0f

        iconsActors.forEach { element, index ->
            x = if (index % collCount == 0) {
                if (index != 0) y += element.height
                0f
            } else x + element.width

            element.setPosition(x, y)
            addActorListener(element)

            if (isFirstAppRunning && element.isMain.not()) {
                element.setDarkerView()
            }
            iconsGroup.addActor(element)
        }
    }

    private fun addActorListener(actor: CellIcon) {
        addClickListener(actor) {
            if (actor.isActive().not()) return@addClickListener false
            if (numIconsOnScene < iconsActors.size) numIconsOnScene += 1

            when {
                actor.isMain -> {
                    if (isFirstAppRunning && numIconsOnScene > Config.NUMBER_STEPS_FOR_HELPERS) {
                        isFirstAppRunning = false
                    }
                    animateMainActorAndReInit(actor)
                }
                isFirstAppRunning.not() -> {
                    if (score > 0) scoreActor.score = --score
                    timer.decreaseTimer()
                    actor.animate(AnimationType.PULSE)

                    digitalBlow.revert = true
                    digitalBlow.animate(AnimationType.BLOW, duration = 0.5f)
                    iconsGroup.animate(AnimationType.PULSE, duration = 0.2f)
                }
                else -> return@addClickListener false
            }
            return@addClickListener true
        }
    }

    private fun animateMainActorAndReInit(element: CellIcon) {
        bracketLeft.animate(AnimationType.PULSE)
        bracketRight.animate(AnimationType.PULSE)
        element.animate(AnimationType.SCORE_INCREASE, Runnable {
            scoreActor.score = ++score
            timer.increaseTimer()
            reInitActors()
            setMainIcon()

            if (isFirstAppRunning) helper.watchByActor(activeMap[numMainIcon])
            else if (helper.isVisible) helper.isVisible = false
        })

        digitalBlow.revert = false
        digitalBlow.animate(AnimationType.BLOW, duration = 0.5f)
    }

    private fun setMainIcon(value: Int = -1) {
        numMainIcon = if (value == -1) {
            val randomValue = Random.nextInt(0, numIconsOnScene - 1)
            activeMap.keys.elementAt(randomValue)
        } else value
        activeMap[numMainIcon]?.isMain = true
        activeMap[numMainIcon]?.setNormalView()
        headIcon.setIconNumber(numMainIcon)
    }

    private fun reInitActors() {
        activeMap.clear()
        iconsActors.forEach { element, _ -> element.resetAll() }

        for (num in 1..numIconsOnScene) {
            val newActorNum = setNewActiveIcon()

            val newActor = activeMap[newActorNum] ?: continue
            if (isFirstAppRunning && newActor.isMain.not()) {
                newActor.setDarkerView()
            } else newActor.setNormalView()
        }
    }

    private fun setNewActiveIcon(): Int {
        val iconNumber = getUniqueRandomNumber({ activeMap.contains(it) }, iconsRegions.size)
        val actorNumber = getUniqueRandomNumber({ iconsActors[it].isActive() }, iconsActors.size)
        val icon = iconsActors[actorNumber]
        activeMap[iconNumber] = icon
        icon.setIconByNumber(iconNumber)
        return iconNumber
    }

    private fun getUniqueRandomNumber(condition: (num: Int) -> Boolean, size: Int): Int {
        var number = Random.nextInt(size)
        var wasReset = false
        while (condition.invoke(number++)) {
            if (number >= size) {
                number = 0
                if (wasReset.not()) wasReset = true
                else throw IllegalStateException("all slots busy")
            }
        }
        return number - 1
    }

    private fun setIconsGroupPosition() {
        val width = collCount * iconsActors.first().width
        val height = rowCount * iconsActors.first().height
        val x = (Config.WIDTH_GAME - width) / 2
        val y = 50f
        iconsGroup.setSize(width, height)
        iconsGroup.setPosition(x, y)
    }

    private fun initActorsPositions() {
        headIcon.setSize(bracketLeft.height, bracketLeft.height)

        val headX = (Config.WIDTH_GAME - headIcon.width) / 2
        val headY = Config.HEIGHT_GAME - bracketLeft.height - 50f
        val padding = 100f

        bracketLeft.setPosition(headX - padding, headY)
        headIcon.setPosition(headX, headY)


        bracketRight.scaleX = -1f
        bracketRight.setPosition(headIcon.tailX + padding, headY)

        timer.setPosition((Config.WIDTH_GAME - timer.width) / 2, headY - timer.height - 50)
    }

    private fun addClickListener(actor: Actor, function: () -> Boolean) {
        actor.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                if (function.invoke().not()) return false
                if (AudioManager.isSoundEnable) AudioManager.play(AudioManager.SoundApp.CLICK_SOUND)
                if (VibrationManager.isVibrationEnable) VibrationManager.vibrate(CLICK)
                return super.touchDown(event, x, y, pointer, button)
            }
        })
    }

    private fun startControlGameOver() {
        timer.onTimerExpired {
            AudioManager.stopAll()
            iconsActors.forEach { icon -> icon.clear() }
            ScreenManager.setScreen(ScreenManager.Screens.GAME_OVER,
                    Pair(ScreenManager.Param.SCORE, score))
        }
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