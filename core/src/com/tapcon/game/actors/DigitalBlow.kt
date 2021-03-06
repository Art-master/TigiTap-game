package com.tapcon.game.actors

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Array
import com.tapcon.game.api.Animated
import com.tapcon.game.api.AnimationType
import com.tapcon.game.api.GameActor
import com.tapcon.game.data.Descriptors
import kotlin.random.Random

class DigitalBlow(manager: AssetManager, var target: GameActor) : GameActor(), Animated {
    private val font = manager.get(Descriptors.scoreFont)
    private val particles: Array<Particle> = Array()

    var particlesCount = 100
    var sideSize = 1000

    var revert = false

    private fun generateDigitalBlow() {
        val centerX = target.centerX.toInt()
        val centerY = target.centerY.toInt()

        for (unitNum in 0..particlesCount) {
            val randomX = Random.nextInt(-(centerX - sideSize / 2), centerX + sideSize / 2)
            val randomY = Random.nextInt(-(centerY - sideSize / 2), centerY + sideSize / 2)
            val velocity = Vector2(randomX.toFloat(), randomY.toFloat())
            val text = if (unitNum % 2 == 0) "1" else "0"
            val particle = Particle(centerX.toFloat(), centerY.toFloat(), text, velocity)
            if (revert) {
                particle.x = randomX.toFloat()
                particle.y = randomY.toFloat()
            }
            particles.add(particle)
        }
    }


    private data class Particle(var x: Float = 0f, var y: Float = 0f, val unit: String, val velocity: Vector2)

    override fun act(delta: Float) {
        super.act(delta)

        updateParticles(delta)
    }

    private fun updateParticles(delta: Float) {
        particles.forEach {
            if (revert) {
                if (it.x > target.x) it.x -= it.velocity.x * delta
                if (it.y > target.y) it.y -= it.velocity.y * delta
            } else {
                it.x += it.velocity.x * delta
                it.y += it.velocity.y * delta
            }
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = if (parentAlpha < 1f) parentAlpha else color.a
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
                val alphaFrom = if (revert) 0f else 0.6f
                val alphaTo = if (revert) 0.1f else 0f

                setColor(color.r, color.g, color.b, alphaFrom)
                Actions.sequence(
                        Actions.alpha(alphaTo, duration),
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