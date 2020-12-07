package com.tapcon.game.data

class Assets {
    class BackgroundTexture {
        companion object {
            const val NAME = "background.jpg"
        }
    }

    class ProgressAtlas {
        companion object {
            const val NAME = "progressBar.atlas"
            const val SQUARE = "square"
            const val LOADING_TEXT = "loading_text"
        }
    }

    class InterfaceAtlas {
        companion object {
            const val NAME = "interface.atlas"
            const val PLAY = "play"
            const val BORDER = "border"
            const val BORDER_MINI = "border_mini"
            const val SOUND_ICON = "sound_icon"
            const val MUSIC_ICON = "music_icon"
            const val VIBRATION_ICON = "vibration_icon"
            const val GAME_NAME = "game_name"
            const val CELL = "cell"
            const val BRACKET = "bracket"
            const val TIME_BORDER = "time_border"
            const val TIME_ICON = "timer_icon"
            const val RELOAD_ICON = "reload"
            const val MENU_ICON = "menu"
            const val SHARE_ICON = "share"
            const val AWARDS_ICON = "awards"
            const val SCORES_ICON = "scores"
        }
    }

    class IconsAtlas {
        companion object {
            const val NAME = "icons.atlas"
            const val ICON = "icon"

        }
    }

    class Fonts {
        companion object {
            const val JURA = "fonts/Jura-VariableFont_wght.ttf"
            const val FONT = "fonts/LuckiestGuy-Regular.ttf"
            const val MAIN = "fonts/LuckiestGuy-Regular.ttf"
        }
    }
}