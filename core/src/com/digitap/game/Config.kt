package com.digitap.game

object Config {
    const val WIDTH_GAME = 1920f
    const val HEIGHT_GAME = 1080f
    const val SHADOW_ANIMATION_TIME_S = 1f
    const val NUMBER_STEPS_FOR_HELPERS = 3
    const val SOUNDS_FOLDER = "sounds/"

    enum class Debug(var state: Boolean, var info: Any = 0) {
        PLAY_SERVICES(false),
        ADS(false),
        ALWAYS_SHOW_HELPER(false),
        DENY_GAME_OVER(false),
        NUM_SCREEN_ACTORS(false, 19)
    }

    enum class Achievement(val score: Int = 0) {
        NOT_A_BAD_START,
        WELL_DONE,
        LUCKY,
        THE_FASTEST_GUN_IN_THE_WILD_WEST,
        NINJA,
        SLOW_DOWN_ROACH,
        FREQUENT_GUEST,
    }
}