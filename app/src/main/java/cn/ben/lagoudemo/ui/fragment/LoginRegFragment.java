package cn.ben.lagoudemo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import ben.cn.library.ui.fragment.BaseFragment;
import cn.ben.lagoudemo.R;
import cn.ben.lagoudemo.ui.contract.LoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginRegFragment extends BaseFragment implements LoginContract.RegView, View.OnClickListener {

    private LoginContract.RegPresenter mPresenter;

    private EditText reg_input_phone_edit_text;
    private EditText reg_input_captcha_edit_text;
    private ImageView reg_input_phone_icon;
    private ImageView reg_input_captcha_icon;
    private Button reg_confirm_btn;
    private Button reg_return_login_btn;

    private OnReturnLoginBtnClickedListener mOnReturnLoginBtnClickedListener;

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
        reg_input_phone_edit_text = $(R.id.reg_input_phone_edit_text);
        reg_input_captcha_edit_text = $(R.id.reg_input_captcha_edit_text);
        reg_input_phone_icon = $(R.id.reg_input_phone_icon);
        reg_input_captcha_icon = $(R.id.reg_input_captcha_icon);
        reg_confirm_btn = $(R.id.login_reg_confirm_btn);
        reg_return_login_btn = $(R.id.login_return_login_btn);

        reg_return_login_btn.setOnClickListener(this);
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

        reg_input_phone_edit_text.requestFocus();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnReturnLoginBtnClickedListener = (OnReturnLoginBtnClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnReturnLoginBtnClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnReturnLoginBtnClickedListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_return_login_btn:
                mOnReturnLoginBtnClickedListener.switchToLoginFragment();
                break;
        }
    }

    public interface OnReturnLoginBtnClickedListener {
        void switchToLoginFragment();
    }
}
