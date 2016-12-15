package cn.ben.lagoudemo.ui.activity;

import cn.ben.lagoudemo.R;

public class SplashActivity extends ben.cn.library.ui.activity.BaseSplashActivity {

    @Override
    protected int getThemeResourceID() {
        return R.style.SplashTheme;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_splash;
    }

    @Override
    protected Class<?> getJumpingActivityClass() {
        return LoginActivity.class;
    }

    @Override
    protected long getSplashDuration() {
        return 3000;
    }
}
