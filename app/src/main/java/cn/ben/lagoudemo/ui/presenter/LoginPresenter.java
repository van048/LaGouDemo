package cn.ben.lagoudemo.ui.presenter;

import android.support.annotation.NonNull;

import cn.ben.lagoudemo.data.UserAuthInfo;
import cn.ben.lagoudemo.data.source.LoginDataSource;
import cn.ben.lagoudemo.data.source.LoginRepository;
import cn.ben.lagoudemo.ui.contract.LoginContract;

import static cn.ben.lagoudemo.constant.Constants.ErrorMessage.PW_LENGTH_NOT_RIGHT;
import static cn.ben.lagoudemo.constant.Constants.Login.PASSWORD_MAX_LEN;
import static cn.ben.lagoudemo.constant.Constants.Login.PASSWORD_MIN_LEN;
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
    }

    @Override
    public void verifyUser(String name, String password) {
        if (password.length() < PASSWORD_MIN_LEN || password.length() > PASSWORD_MAX_LEN) {
            mLoginView.showVerifyErrorMessage(PW_LENGTH_NOT_RIGHT.getMessage());
            return;
        }

        UserAuthInfo userAuthInfo = new UserAuthInfo(name, password);
        mLoginRepository.verifyUser(userAuthInfo, new LoginDataSource.VerifyUserCallback() {

            @Override
            public void onVerifiedSuccess() {
                mLoginView.moveToMainPage();
            }

            @Override
            public void onVerifiedFailed(String errorMessage) {
                mLoginView.showVerifyErrorMessage(errorMessage);
            }
        });
    }
}