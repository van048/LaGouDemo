package cn.ben.lagoudemo.ui.presenter.impl;

import android.app.Activity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import cn.ben.lagoudemo.ui.activity.ILoginView;
import cn.ben.lagoudemo.ui.presenter.ILoginPresenter;

public class LoginPresenterImpl implements ILoginPresenter {
    private final ILoginView loginView;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private final Activity loginActivity;

    public LoginPresenterImpl(final ILoginView loginView, Activity loginActivity) {
        this.loginView = loginView;
        this.loginActivity = loginActivity;

        KeyboardVisibilityEvent.setEventListener(loginActivity, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                LoginPresenterImpl.this.loginView.startAnim(isOpen);
            }
        });
    }
}
