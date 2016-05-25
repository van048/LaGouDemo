package cn.ben.lagoudemo.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.ben.lagoudemo.Login.LoginActivity;
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
    public void moveToNextView() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
