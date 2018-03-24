package com.mindvalley.mindvalleyexercise.modules.splash;

import android.os.Handler;

/**
 * This class is used to initialise the Splash Activity UI Components and Objects.
 *
 * @author AjinkyaD
 * @version 1.0
 */
class SplashPresenterImplementor implements SplashPresenter {

    final private int SPLASH_TIME_MILLIS = 1500;
    private SplashView splashView;

    SplashPresenterImplementor(SplashView splashView) {
        this.splashView = splashView;
    }

    @Override
    public void showSplashAnimation() {
        if (splashView != null) {
            splashView.animateViews();
        }
    }

    @Override
    public void delayNavigation() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (splashView != null) {
                    splashView.navigateToListingScreen();
                }
            }
        }, SPLASH_TIME_MILLIS);
    }
}
