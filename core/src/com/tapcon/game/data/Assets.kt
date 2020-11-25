package com.tapcon.game.data

class Assets {
    class BackgroundTexture {
        companion object {
            const val NAME = "background.jpg"
        }
    }

    class ProgressAtlas {
        companion object {
            const val NAME = "loadingBar.atlas"
            const val WHITE_SQUARE = "white_square"
            const val BLACK_SQUARE = "black"
            const val PROGRESS_BAR = "loading_bar"
            const val PROGRESS_LINE = "loading_progress"
        }
    }

    class InterfaceAtlas {
        companion object {
            const val NAME = "interface.atlas"
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
            const val FONT = "fonts/LuckiestGuy-Regular.ttf"
            const val MAIN = "fonts/LuckiestGuy-Regular.ttf"
        }
    }
}