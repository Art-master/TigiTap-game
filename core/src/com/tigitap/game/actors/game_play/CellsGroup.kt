package com.tigitap.game.actors.game_play

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.tigitap.game.api.Animated
import com.tigitap.game.api.AnimationType

class CellsGroup : Group(), Animated {

    override fun animate(type: AnimationType, runAfter: Runnable, duration: Float) {
        val action = when (type) {
            AnimationType.PULSE -> {
                Actions.sequence(
                        Actions.moveBy(-10f, -10f, duration / 2),
                        Actions.moveBy(10f, 10f, duration / 2)
                )
            }
            else -> return
        }
        val run = Actions.run(runAfter)
        val sequence = Actions.sequence(action, run)
        addAction(sequence)
    }
}