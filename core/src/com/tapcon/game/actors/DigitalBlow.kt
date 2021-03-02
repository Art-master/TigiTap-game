package com.tapcon.game.actors

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Array
import com.tapcon.game.Config
import com.tapcon.game.api.Animated
import com.tapcon.game.api.AnimationType
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Descriptors
import kotlin.random.Random

class DigitalBlow(manager: AssetManager, var target: GameActor) : GameActor(), Animated {
    private val font = manager.get(Descriptors.scoreFont)
    private val particles: Array<Particle> = Array()

    init {
        x = Config.WIDTH_GAME - 200
        y = Config.HEIGHT_GAME - 100
    }

    private fun generateDigitalBlow() {
        val width = 1000
        val height = 1000
        val minimalCount = 5
        val maximalCount = 100

        val numOfUnits = Random.nextInt(minimalCount, maximalCount)
        val numOfZeros = Random.nextInt(minimalCount, maximalCount)
        val centerX = target.centerX.toInt()
        val centerY = target.centerY.toInt()

        for (unitNum in minimalCount..numOfUnits) {
            val randomX = Random.nextInt(-(centerX - width / 2), centerX + width / 2)
            val randomY = Random.nextInt(-(centerY - height / 2), centerY + height / 2)
            val velocity = Vector2(randomX.toFloat(), randomY.toFloat())
            particles.add(Particle(centerX.toFloat(), centerY.toFloat(), "1", velocity))
        }

        for (unitNum in minimalCount..numOfZeros) {
            val randomX = Random.nextInt(-(centerX - width / 2), centerX + width / 2)
            val randomY = Random.nextInt(-(centerY - height / 2), centerY + height / 2)
            val velocity = Vector2(randomX.toFloat(), randomY.toFloat())
            particles.add(Particle(centerX.toFloat(), centerY.toFloat(), "0", velocity))
        }
    }


    private data class Particle(var x: Float = 0f, var y: Float = 0f, val unit: String, val velocity: Vector2)

    override fun act(delta: Float) {
        super.act(delta)

        updateParticles(delta)
    }

    private fun updateParticles(delta: Float) {
        particles.forEach {
            it.x += it.velocity.x * delta
            it.y += it.velocity.y * delta
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = if(parentAlpha < 1f) parentAlpha else color.a
        batch!!.color = color
        drawBlow(batch)
    }

    private fun drawBlow(batch: Batch) {
        font.color = color
        if (particles.isEmpty) return
        for (particle in particles) {
            font.draw(batch, particle.unit, particle.x, particle.y)
        }
    }

    override fun animate(type: AnimationType, runAfter: Runnable, duration: Float) {

        val action = when (type) {
            AnimationType.BLOW -> {
                generateDigitalBlow()

                val color = color
                setColor(color.r, color.g, color.b, 0.6f)
                Actions.sequence(
                        Actions.alpha(0f, duration),
                        Actions.run {
                            particles.clear()
                        }
                )
            }
            else -> return
        }
        val run = Actions.run(runAfter)
        val sequence = Actions.sequence(action, run)
        addAction(sequence)
    }
}