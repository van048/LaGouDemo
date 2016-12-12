package cn.ben.lagoudemo.ui.activity.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.ben.lagoudemo.R;
import cn.ben.lagoudemo.ui.activity.ISplashView;
import cn.ben.lagoudemo.ui.presenter.ISplashPresenter;
import cn.ben.lagoudemo.ui.presenter.impl.SplashPresenterImpl;

public class SplashActivity extends Activity implements ISplashView {

    @SuppressWarnings("FieldCanBeLocal")
    private ISplashPresenter presenter;

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
