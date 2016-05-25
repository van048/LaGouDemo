package cn.ben.lagoudemo.Splash;

import android.os.Handler;

/**
 * Created by Administrator on 2016/5/23.
 */
public class SplashPresenterImpl implements SplashPresenter {
    private SplashView splashView;
    private SplashInteractor splashInteractor;

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
                        splashView.disappear();
                    }
                }
            }, 3000);
        }
    }
}
