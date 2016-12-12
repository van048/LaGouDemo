package cn.ben.lagoudemo.ui.presenter.impl;

import android.os.Handler;

import cn.ben.lagoudemo.ui.activity.ISplashView;
import cn.ben.lagoudemo.ui.presenter.ISplashPresenter;

public class SplashPresenterImpl implements ISplashPresenter {
    private final ISplashView splashView;

    public SplashPresenterImpl(ISplashView splashView) {
        this.splashView = splashView;
    }

    @Override
    public void countDownToDisappear(Handler handler) {
        if (handler != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (splashView != null) {
                        splashView.moveToNextView();
                    }
                }
            }, 3000);
        }
    }
}