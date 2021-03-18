package com.tigitap.game.actors

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.tigitap.game.Config
import com.tigitap.game.api.Animated
import com.tigitap.game.api.AnimationType
import com.tigitap.game.api.GameActor
import com.tigitap.game.data.Descriptors
import kotlin.random.Random

class DigitalMatrix(manager: AssetManager) : GameActor(), Animated {
    private val font = manager.get(Descriptors.juraFont)
    private val particles: Array<Particle> = Array()

    var particlesCount = 100

    var isVertical = false

    private fun generateMatrix() {
        for (unitNum in 0..particlesCount) {
            val randomX = Random.nextInt(0, Config.WIDTH_GAME.toInt())
            val randomY = Random.nextInt(0, Config.HEIGHT_GAME.toInt())

            val velocity = Random.nextInt(20, 100).toFloat()
            val text = if (unitNum % 2 == 0) "1" else "0"
            val particle = Particle(randomX.toFloat(), randomY.toFloat(), text, Vector2(velocity, velocity))
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
            if (isVertical) {
                it.x += it.velocity.x * delta
                if (it.x >= Config.WIDTH_GAME + 30) it.x = 0f
            } else {
                it.y += it.velocity.y * delta
                if (it.y >= Config.HEIGHT_GAME + 30) it.y = 0f
            }
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        color.a = if (parentAlpha < 1f) parentAlpha else color.a
        batch!!.color = color
        color.a = 0.1f
        drawBlow(batch)
        color.a = 1f
    }

    private fun drawBlow(batch: Batch) {
        font.color = color
        if (particles.isEmpty) return
        for (particle in particles) {
            font.draw(batch, particle.unit, particle.x, particle.y)
        }
    }

    override fun animate(type: AnimationType, runAfter: Runnable, duration: Float) {
        generateMatrix()
    }
}