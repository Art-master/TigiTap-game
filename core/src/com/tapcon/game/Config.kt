package com.tapcon.game

object Config {
    const val WIDTH_GAME = 1920f
    const val HEIGHT_GAME = 1080f
    const val SHADOW_ANIMATION_TIME_S = 1f
    const val BUTTONS_ANIMATION_TIME_S = 1f
    const val NUMBER_STEPS_FOR_HELPERS = 3
    const val SOUNDS_FOLDER = "sounds/"

    enum class Debug(var state: Boolean, var info: Any = 0) {
        COLLISIONS(false),
        PLAY_SERVICES(false),
        ADS(false)
    }

    enum class Achievement(val score: Int) {
        SUNGLASSES(50),
        HAT(100),
        BOOTS(150),
        BELT(200),
        GUN(250),
        BULLETS(300),
        FINISH_GAME(400)
    }
}