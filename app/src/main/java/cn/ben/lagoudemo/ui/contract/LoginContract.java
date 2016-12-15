package cn.ben.lagoudemo.ui.contract;

import ben.cn.library.ui.activity.BaseView;
import ben.cn.library.ui.presenter.BasePresenter;

public interface LoginContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter{
    }
}