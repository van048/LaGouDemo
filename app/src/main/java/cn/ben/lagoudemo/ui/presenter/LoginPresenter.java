package cn.ben.lagoudemo.ui.presenter;

import android.support.annotation.NonNull;

import cn.ben.lagoudemo.data.source.LoginRepository;
import cn.ben.lagoudemo.ui.contract.LoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginRepository mLoginRepository;

    private final LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginRepository loginRepository, @NonNull LoginContract.View loginView) {
        mLoginRepository = checkNotNull(loginRepository, "loginRepository cannot be null");
        mLoginView = checkNotNull(loginView, "loginView cannot be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {
        // TODO: 2016/12/16  
    }
}