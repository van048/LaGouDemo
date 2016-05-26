package cn.ben.lagoudemo.Login;

import android.app.Activity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

class LoginPresenterImpl implements LoginPresenter{
    private final LoginView loginView;

    public LoginPresenterImpl(final LoginView loginView, Activity activity) {
        this.loginView = loginView;

        KeyboardVisibilityEvent.setEventListener(activity, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                LoginPresenterImpl.this.loginView.startAnim(isOpen);
            }
        });
    }
}
