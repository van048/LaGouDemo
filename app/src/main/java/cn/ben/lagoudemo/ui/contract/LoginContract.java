package cn.ben.lagoudemo.ui.contract;

import ben.cn.library.ui.activity.BaseView;
import ben.cn.library.ui.presenter.BasePresenter;

public interface LoginContract {
    interface LoginView extends BaseView<LoginPresenter> {
        boolean isActive();

        void moveToMainPage();

        void showVerifyErrorMessage(String errorMessage);
    }

    interface LoginPresenter extends BasePresenter {
        void verifyUser(String name, String password);
    }

    interface RegView extends BaseView<RegPresenter> {
        boolean isActive();

        void showGettingCaptchaUI();

        void showCaptchaGotSuccessUI(String successMessage);

        void showCaptchaGotFailedUI(String errorMessage);
    }

    interface RegPresenter extends BasePresenter {
        boolean checkPhoneValidity(String phoneNumber);

        void getCaptcha();
    }
}
