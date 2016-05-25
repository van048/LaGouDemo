package cn.ben.lagoudemo.Splash;

import android.os.Handler;

public class SplashPresenterImpl implements SplashPresenter {
    private final SplashView splashView;
    private final SplashInteractor splashInteractor;

    public SplashPresenterImpl(SplashView splashView) {
        this.splashView = splashView;
        this.splashInteractor = new SplashInteractorImpl();
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
