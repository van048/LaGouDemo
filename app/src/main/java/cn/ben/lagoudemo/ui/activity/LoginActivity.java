package cn.ben.lagoudemo.ui.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.view.View;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import ben.cn.library.ui.activity.BaseActivity;
import ben.cn.library.utils.EspressoIdlingResource;
import ben.cn.library.utils.MyActivityUtils;
import cn.ben.lagoudemo.Injection;
import cn.ben.lagoudemo.R;
import cn.ben.lagoudemo.constant.Constants;
import cn.ben.lagoudemo.ui.fragment.LoginLoginFragment;
import cn.ben.lagoudemo.ui.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity {
    private LoginPresenter mLoginPresenter;

    private View login_animGroup_logo;
    private View login_animGroup_edit_text;

    // animation related
    private ValueAnimator mValueAnimatorKeyboardOpen, mValueAnimatorKeyboardClose;
    private boolean isKeyboardOpening = false; // latest param of startAnim
    private float mLatestScaleOpen = 0, mLatestScaleClose = 1; // start from latest scale

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        setUpAnimator();
    }

    private void setUpAnimator() {
        mValueAnimatorKeyboardOpen = ValueAnimator.ofFloat(0, 1);
        mValueAnimatorKeyboardOpen.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                value += (1 - mLatestScaleClose);
                if (value > 1) value = 1;
                value = 1 - value;
                mLatestScaleOpen = value;
                updateAnimGroupPos(value);
            }
        });

        mValueAnimatorKeyboardClose = ValueAnimator.ofFloat(0, 1);
        mValueAnimatorKeyboardClose.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                value += mLatestScaleOpen;
                if (value > 1) value = 1;
                mLatestScaleClose = value;
                updateAnimGroupPos(value);
            }
        });

        mValueAnimatorKeyboardOpen.setDuration(Constants.Login.KEYBOARD_ANIM_DURATION);
        mValueAnimatorKeyboardClose.setDuration(Constants.Login.KEYBOARD_ANIM_DURATION);
    }

    private void updateAnimGroupPos(float logoScale) {
        login_animGroup_logo.setScaleX(logoScale);
        login_animGroup_logo.setScaleY(logoScale);
        login_animGroup_edit_text.setTranslationY(Constants.Login.KEYBOARD_ANIM_EDIT_TEXT_FINAL_Y * (1 - logoScale));
    }

    @Override
    protected int getThemeResourceID() {
        return R.style.DefaultFullScreenTheme;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpView() {
        super.setUpView();

        login_animGroup_logo = $(R.id.login_anim_group_1);
        login_animGroup_edit_text = $(R.id.login_anim_group_2);

        LoginLoginFragment loginLoginFragment = (LoginLoginFragment) getSupportFragmentManager().findFragmentById(R.id.login_anim_group_2);
        if (loginLoginFragment == null) {
            loginLoginFragment = LoginLoginFragment.newInstance();
            MyActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginLoginFragment, R.id.login_anim_group_2);
        }

        mLoginPresenter = new LoginPresenter(
                Injection.provideLoginRepository(getApplicationContext()), loginLoginFragment);

        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                startAnim(isOpen);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // original state
        isKeyboardOpening = false;
        login_animGroup_logo.setScaleX(1);
        login_animGroup_logo.setScaleY(1);
        login_animGroup_edit_text.setTranslationY(0);
    }

    private void startAnim(boolean isOpen) {
        //  animate once
        if (isOpen == isKeyboardOpening) return;

        mValueAnimatorKeyboardOpen.cancel();
        mValueAnimatorKeyboardClose.cancel();

        isKeyboardOpening = isOpen;
        if (isKeyboardOpening) mValueAnimatorKeyboardOpen.start();
        else mValueAnimatorKeyboardClose.start();
    }

    @Override
    protected int getExitHintResourceID() {
        return R.string.toast_back_pressed;
    }

    // see http://www.jianshu.com/p/9bda5f58daf1
    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
