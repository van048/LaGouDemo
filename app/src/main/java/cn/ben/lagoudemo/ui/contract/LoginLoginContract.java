package cn.ben.lagoudemo.ui.contract;

import ben.cn.library.ui.activity.BaseView;
import ben.cn.library.ui.presenter.BasePresenter;

public interface LoginLoginContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();

        void moveToMainPage();

        void showVerifyErrorMessage(String errorMessage);
    }

    interface Presenter extends BasePresenter{
        void verifyUser(String name, String password);
    }
}
