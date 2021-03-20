package com.tigitap.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.tigitap.game.Config
import com.tigitap.game.Config.Achievement.*
import com.tigitap.game.Config.Debug
import com.tigitap.game.actors.Background
import com.tigitap.game.actors.DigitalBlow
import com.tigitap.game.actors.game_play.*
import com.tigitap.game.api.AnimationType
import com.tigitap.game.data.Assets
import com.tigitap.game.data.Descriptors
import com.tigitap.game.managers.AudioManager
import com.tigitap.game.managers.ScreenManager
import com.tigitap.game.managers.ScreenManager.Param
import com.tigitap.game.managers.VibrationManager
import com.tigitap.game.managers.VibrationManager.VibrationType.CLICK
import kotlin.random.Random

class GamePlayScreen(params: Map<Param, Any>) : GameScreen(params) {

    private var isFirstAppRunning = params[Param.FIRST_APP_RUN] as Boolean
    private var numAppVisit = params[Param.NUM_APP_VISIT] as Int

    private val atlas = manager.get(Descriptors.icons)
    private val iconsRegions = atlas.findRegions(Assets.IconsAtlas.ICON)

    private val bracketLeft = Bracket(manager)
    private val bracketRight = Bracket(manager)
    private val headIcon = HeadIcon(manager, bracketLeft, bracketRight)

    private val timer = GameTimer(manager)
    private val scoreActor = Score(manager)
    private val helper = Helper(manager)
    private val iconsGroup = CellsGroup()

    private val digitalBlow = DigitalBlow(manager, scoreActor)

    private val rowCount = 3
    private val collCount = 7

    private var numIconsOnScene = 1
    private var numMainIconForHelper = -1

    private var score = 0

    private var round = 1
    private var maxRoundCount = 4

    private val iconsActors = Array(rowCount * collCount) { CellIcon(manager, scoreActor) }

    private val activeMap = HashMap<Int, CellIcon>(iconsRegions.size)

    private inline fun <T> Array<out T>.forEach(action: (T, Int) -> Unit) {
        for ((index, element) in this.withIndex()) action(element, index)
    }

    init {
        stageBackground.addActor(Background(manager))
        initActorsPositions()
        if (Debug.ALWAYS_SHOW_HELPER.state) isFirstAppRunning = true

        stage.apply {
            addActor(bracketLeft)
            addActor(bracketRight)
            addActor(timer)
            addActor(headIcon)
            addActor(iconsGroup)
            addActor(digitalBlow)
            addActor(scoreActor)
            addActor(helper)
        }

        initIconsMatrix()
        setIconsGroupPosition()

        if (Debug.NUM_SCREEN_ACTORS.state) {
            numIconsOnScene = Debug.NUM_SCREEN_ACTORS.info as Int
            reInitActors()
        }

        numMainIconForHelper = setNewActiveIcon() // Start icon
        setMainIcon(numMainIconForHelper)

        helper.watchByActor(activeMap[numMainIconForHelper])

        startControlGameOver()

        controlAchievements()

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

            when {
                actor.isMain -> {
                    controlHelper()
                    animateMainActorAndReInit(actor)
                    controlAchievements(true)
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

    private fun controlHelper() {
        if (isFirstAppRunning && numIconsOnScene == Config.NUMBER_STEPS_FOR_HELPERS) {
            isFirstAppRunning = false
        } else if (isFirstAppRunning.not() && numIconsOnScene == 1) {
            isFirstAppRunning = false
        }
    }

    private fun controlAchievements(isRightClick: Boolean = false) {
        if (isRightClick) {
            val roundScoreCount = collCount * rowCount
            when (score) {
                //round 1
                roundScoreCount -> servicesController.unlockAchievement(NOT_A_BAD_START)
                //round 2
                roundScoreCount * 3 -> servicesController.unlockAchievement(WELL_DONE)
                //round 3
                roundScoreCount * 6 -> servicesController.unlockAchievement(LUCKY)
                // 190 points
                roundScoreCount * 9 -> servicesController.unlockAchievement(THE_FASTEST_GUN_IN_THE_WILD_WEST)
                // 274 points
                roundScoreCount * 13 -> servicesController.unlockAchievement(NINJA)
                // 400 points
                roundScoreCount * 19 -> servicesController.unlockAchievement(SLOW_DOWN_ROACH)
            }
        } else {
            if (numAppVisit == 30) servicesController.unlockAchievement(FREQUENT_GUEST)
        }
    }

    private fun animateMainActorAndReInit(element: CellIcon) {
        bracketLeft.animate(AnimationType.PULSE)
        bracketRight.animate(AnimationType.PULSE)
        element.animate(AnimationType.SCORE_INCREASE, Runnable {
            scoreActor.score = ++score
            headIcon.clickByIcon(element.index)

            if (headIcon.isAllChecked()) {
                timer.increaseTimer()
                roundControl()
                if (numIconsOnScene < iconsActors.size) numIconsOnScene += 1
                reInitActors()
                headIcon.clearAll()
                setMainIcons(round)

                if (isFirstAppRunning) helper.watchByActor(activeMap[numMainIconForHelper])
                else if (helper.isVisible) {
                    helper.isVisible = false
                    timer.start()
                }
            }
        })

        digitalBlow.revert = false
        digitalBlow.animate(AnimationType.BLOW, duration = 0.5f)
    }

    private fun roundControl() {
        val maxIconNumber = rowCount * collCount
        if (numIconsOnScene == maxIconNumber && round < maxRoundCount) {
            round += 1
            numIconsOnScene = round
        }
    }

    private fun setMainIcon(value: Int = -1) {
        val num = if (value == -1) {
            val randomValue = Random.nextInt(0, numIconsOnScene - 1)
            activeMap.keys.elementAt(randomValue)
        } else value
        setMainIconParams(num)
    }

    private fun setMainIconParams(num: Int) {
        activeMap[num]?.isMain = true
        activeMap[num]?.setNormalView()
        activeMap[num]?.index = num
        headIcon.addIconNumber(num)
        numMainIconForHelper = num
    }

    private fun setMainIcons(num: Int = 1) {
        val numbers = ArrayList<Int>()
        while (numbers.size < num) {
            val randomValue = Random.nextInt(0, numIconsOnScene - 1)
            val iconNum = activeMap.keys.elementAt(randomValue)
            if (!numbers.contains(iconNum) && !headIcon.contains(iconNum)) {
                numbers.add(iconNum)
            }
        }
        numbers.forEach { setMainIconParams(it) }
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
        val iconNumber = getUniqueRandomNumber({
            activeMap.contains(it) && activeMap[it]!!.isMain.not()
        }, iconsRegions.size)

        val actorNumber = getUniqueRandomNumber({
            iconsActors[it].isActive()
        }, iconsActors.size)

        val icon = iconsActors[actorNumber]
        icon.index = actorNumber
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
        val headY = Config.HEIGHT_GAME - bracketLeft.height - 50f
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
            digitalBlow.remove()
            AudioManager.stopAll()
            iconsActors.forEach { icon -> icon.clear() }
            ScreenManager.setScreen(ScreenManager.Screens.GAME_OVER,
                    Pair(Param.SCORE, score))
        }
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