package cn.ben.lagoudemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ben.cn.library.ui.fragment.BaseFragment;
import cn.ben.lagoudemo.R;
import cn.ben.lagoudemo.ui.contract.LoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginRegFragment extends BaseFragment implements LoginContract.RegView {

    private LoginContract.RegPresenter mPresenter;

    public static LoginRegFragment newInstance() {
        return new LoginRegFragment();
    }

    public LoginRegFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_reg, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
    }

    private void setUpView() {
    }

    @Override
    public void setPresenter(LoginContract.RegPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
