package cn.ben.lagoudemo.ui.presenter;

import android.support.annotation.NonNull;

import ben.cn.library.utils.EspressoIdlingResource;
import cn.ben.lagoudemo.data.UserAuthInfo;
import cn.ben.lagoudemo.data.source.LoginDataSource;
import cn.ben.lagoudemo.data.source.LoginRepository;
import cn.ben.lagoudemo.ui.contract.LoginContract;

import static cn.ben.lagoudemo.constant.Constants.ErrorMessage.PW_LENGTH_NOT_RIGHT;
import static cn.ben.lagoudemo.constant.Constants.Login.PASSWORD_MAX_LEN;
import static cn.ben.lagoudemo.constant.Constants.Login.PASSWORD_MIN_LEN;
import static com.google.common.base.Preconditions.checkNotNull;

public class LoginLoginPresenter implements LoginContract.LoginPresenter {

    private final LoginRepository mLoginRepository;

    private final LoginContract.LoginView mLoginLoginView;

    public LoginLoginPresenter(@NonNull LoginRepository loginRepository, @NonNull LoginContract.LoginView loginLoginView) {
        mLoginRepository = checkNotNull(loginRepository, "loginRepository cannot be null");
        mLoginLoginView = checkNotNull(loginLoginView, "loginLoginView cannot be null!");

        mLoginLoginView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void verifyUser(String name, String password) {
        if (password.length() < PASSWORD_MIN_LEN || password.length() > PASSWORD_MAX_LEN) {
            if (mLoginLoginView.isActive())
                mLoginLoginView.showVerifyErrorMessage(PW_LENGTH_NOT_RIGHT.getMessage());
            return;
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        UserAuthInfo userAuthInfo = new UserAuthInfo(name, password);
        mLoginRepository.verifyUser(userAuthInfo, new LoginDataSource.VerifyUserCallback() {

            @Override
            public void onVerifiedSuccess() {
                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }
                if (mLoginLoginView.isActive())
                    mLoginLoginView.moveToMainPage();
            }

            @Override
            public void onVerifiedFailed(String errorMessage) {
                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }
                if (mLoginLoginView.isActive())
                    mLoginLoginView.showVerifyErrorMessage(errorMessage);
            }
        });
    }
}