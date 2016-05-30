package cn.ben.lagoudemo.Login;

import android.app.Activity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

class LoginPresenterImpl implements LoginPresenter {
    private final LoginView loginView;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private final Activity loginActivity;

    public LoginPresenterImpl(final LoginView loginView, Activity loginActivity) {
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
