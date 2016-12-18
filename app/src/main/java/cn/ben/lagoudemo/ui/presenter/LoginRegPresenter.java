package cn.ben.lagoudemo.ui.presenter;

import android.support.annotation.NonNull;

import cn.ben.lagoudemo.data.source.LoginRepository;
import cn.ben.lagoudemo.ui.contract.LoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginRegPresenter implements LoginContract.RegPresenter {

    private final LoginRepository mLoginRepository;

    private final LoginContract.RegView mLoginRegView;

    public LoginRegPresenter(@NonNull LoginRepository loginRepository, @NonNull LoginContract.RegView loginRegView) {
        mLoginRepository = checkNotNull(loginRepository, "loginRepository cannot be null");
        mLoginRegView = checkNotNull(loginRegView, "loginRegView cannot be null!");

        mLoginRegView.setPresenter(this);
    }

    @Override
    public void start() {
    }
}