package com.run.cookie.run.game.services

import com.tapcon.game.services.ServicesController

interface AdsController: ServicesController {
    fun isWifiConnected(): Boolean
    fun isInterstitialLoaded(): Boolean
    fun showBannerAd()
    fun hideBannerAd()
    fun showInterstitialAd(then: Runnable? = null)
    fun showVideoAd(then: Runnable? = null)
}