package com.tigitap.game;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.tigitap.game.services.AdsCallback;
import com.tigitap.game.services.AdsController;
import com.tigitap.game.services.CallBack;
import com.tigitap.game.services.ServicesController;

import org.jetbrains.annotations.NotNull;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

public class IOSLauncher extends IOSApplication.Delegate implements AdsController, ServicesController {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new GdxGame(this), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    @Override
    public void unlockAchievement(@NotNull Config.Achievement achievement) {

    }

    @Override
    public void incrementAchievement(@NotNull Config.Achievement achievement, int value) {

    }

    @Override
    public void showAllAchievements() {

    }

    @Override
    public boolean isNetworkAvailable() {
        return false;
    }

    @Override
    public boolean isInterstitialLoaded() {
        return false;
    }

    @Override
    public void showBannerAd() {

    }

    @Override
    public void hideBannerAd() {

    }


    @Override
    public void signIn() {

    }

    @Override
    public void signInSilently() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void submitScore(long highScore) {

    }

    @Override
    public void showLeaderboard() {

    }

    @Override
    public void getPlayerCenteredScores(@NotNull CallBack callBack) {

    }

    @Override
    public void getTopScores(int scoreType, @NotNull CallBack callBack) {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }

    @Override
    public void share(int score) {

    }

    @Override
    public void showInterstitialAd(@NotNull AdsCallback then) {

    }

    @Override
    public void showVideoAd(@NotNull AdsCallback then) {

    }
}