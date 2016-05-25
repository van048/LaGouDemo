package cn.ben.lagoudemo.Splash;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ben.lagoudemo.R;

public class SplashActivity extends Activity implements SplashView {

    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenterImpl(this);

        Handler handler = new Handler();
        presenter.countDownToDisappear(handler);
    }

    @Override
    public void disappear() {
        //// TODO: 2016/5/23
        finish();
    }
}
