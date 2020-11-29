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
            const val BORDER_GREEN = "border_green"
            const val SOUND = "sound"
            const val VIBRATION = "vibration"
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