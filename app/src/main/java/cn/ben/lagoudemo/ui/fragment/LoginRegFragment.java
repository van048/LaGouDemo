package cn.ben.lagoudemo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;

import ben.cn.library.constants.Constants;
import ben.cn.library.ui.fragment.BaseFragment;
import ben.cn.library.utils.MyToastUtils;
import cn.ben.lagoudemo.R;
import cn.ben.lagoudemo.ui.adapter.TextWatcherAdapter;
import cn.ben.lagoudemo.ui.contract.LoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginRegFragment extends BaseFragment implements LoginContract.RegView, View.OnClickListener, View.OnFocusChangeListener {

    public static final int MILLIS_COUNT_DOWN = 3000; // TODO: 2016/12/24
    public static final int COUNT_DOWN_INTERVAL = 1000;
    private LoginContract.RegPresenter mPresenter;

    private EditText reg_input_phone_edit_text;
    private EditText reg_input_captcha_edit_text;
    private ImageView reg_input_phone_icon;
    private ImageView reg_input_captcha_icon;
    private ImageView reg_input_phone_delete;
    private Button reg_confirm_btn;
    private Button reg_return_login_btn;
    private View reg_input_captcha_get_captcha_group;
    private TextView reg_input_captcha_get_captcha;
    private TextView reg_input_captcha_get_captcha_count_down;
    private ProgressBar reg_input_captcha_get_captcha_progress_bar;

    @NonNull
    private OnReturnLoginBtnClickedListener mOnReturnLoginBtnClickedListener;

    private boolean b_phone_empty = true;
    private boolean b_captcha_empty = true;
    private boolean b_captcha_get_clicked = false;

    private CountDownTimer mCountDownTimer;

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
        reg_input_phone_delete = $(R.id.reg_input_phone_delete);
        reg_confirm_btn = $(R.id.login_reg_confirm_btn);
        reg_return_login_btn = $(R.id.login_return_login_btn);
        reg_input_captcha_get_captcha_group = $(R.id.reg_input_captcha_get_captcha_group);
        reg_input_captcha_get_captcha = $(R.id.reg_input_captcha_get_captcha);
        reg_input_captcha_get_captcha_count_down = $(R.id.reg_input_captcha_get_captcha_count_down);
        reg_input_captcha_get_captcha_progress_bar = $(R.id.reg_input_captcha_progress_bar);

        reg_input_phone_edit_text.setOnFocusChangeListener(this);
        reg_input_phone_edit_text.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                b_phone_empty = StringUtils.isEmpty(charSequence);
                updateOnTextChanged(reg_input_phone_delete, b_phone_empty);
            }
        });

        reg_input_captcha_edit_text.setOnFocusChangeListener(this);
        reg_input_captcha_edit_text.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                b_captcha_empty = StringUtils.isEmpty(charSequence);
                updateOnTextChanged(null, b_captcha_empty);
            }
        });

        reg_return_login_btn.setOnClickListener(this);
        reg_input_phone_delete.setOnClickListener(this);
        reg_input_captcha_get_captcha_group.setOnClickListener(this);
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
    public void showGettingCaptchaUI() {
        b_captcha_get_clicked = true;
        reg_input_captcha_get_captcha_count_down.setVisibility(View.GONE);
        reg_input_captcha_get_captcha.setVisibility(View.GONE);
        reg_input_captcha_get_captcha_progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCaptchaGotSuccessUI(String successMessage) {
        b_captcha_get_clicked = true;
        reg_input_captcha_get_captcha_count_down.setVisibility(View.VISIBLE);
        reg_input_captcha_get_captcha.setVisibility(View.GONE);
        reg_input_captcha_get_captcha_progress_bar.setVisibility(View.GONE);
        MyToastUtils.showShortToastSafe(getContext(), successMessage, true);
        startCaptchaCountDown();
    }

    @Override
    public void showCaptchaGotFailedUI(String errorMessage) {
        b_captcha_get_clicked = false;
        reg_input_captcha_get_captcha_count_down.setVisibility(View.GONE);
        reg_input_captcha_get_captcha.setVisibility(View.VISIBLE);
        reg_input_captcha_get_captcha_progress_bar.setVisibility(View.GONE);
        MyToastUtils.showShortToastSafe(getContext(), errorMessage, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean isHidden) {
        super.onHiddenChanged(isHidden);
        if (!isHidden) {
            reg_input_phone_edit_text.requestFocus();
        }
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
            case R.id.reg_input_captcha_get_captcha_group:
                if (b_captcha_get_clicked) return;
                boolean isPhoneValid = mPresenter.checkPhoneValidity(reg_input_phone_edit_text.getText().toString());
                if (!isPhoneValid) {
                    MyToastUtils.showShortToastSafe(getContext(), getResources().getString(R.string.ret_input_phone_hint), true);
                    return;
                }
                mPresenter.getCaptcha();
                break;
            case R.id.reg_input_phone_delete:
                reg_input_phone_edit_text.setText(Constants.EMPTY_STRING);
                break;
        }
    }

    private void startCaptchaCountDown() {
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(MILLIS_COUNT_DOWN, COUNT_DOWN_INTERVAL) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // TODO: 2016/12/24  
                    reg_input_captcha_get_captcha_count_down.setText((int) (millisUntilFinished / 1000f) + "s");
                }

                @Override
                public void onFinish() {
                    b_captcha_get_clicked = false;
                    reg_input_captcha_get_captcha_count_down.setVisibility(View.GONE);
                    reg_input_captcha_get_captcha.setVisibility(View.VISIBLE);
                    reg_input_captcha_get_captcha_progress_bar.setVisibility(View.GONE);
                }
            };
        }
        mCountDownTimer.cancel();
        mCountDownTimer.start();
    }

    private void updateOnTextChanged(View deleteView, boolean isEmpty) {
        reg_confirm_btn.setEnabled(!b_phone_empty && !b_captcha_empty && b_captcha_get_clicked);
        if (deleteView != null) deleteView.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
    }

    private void updateDrawableOnFocusChanged(ImageView icon, int drawableID) {
        icon.setImageDrawable(getResources().getDrawable(drawableID, getContext().getTheme()));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.reg_input_phone_edit_text:
                if (hasFocus) {
                    ((EditText) v).setSelection(0);
                    updateOnTextChanged(reg_input_phone_delete, b_phone_empty);
                    updateDrawableOnFocusChanged(reg_input_phone_icon, android.R.drawable.ic_input_delete);
                } else {
                    reg_input_phone_delete.setVisibility(View.INVISIBLE);
                    updateDrawableOnFocusChanged(reg_input_phone_icon, android.R.drawable.ic_btn_speak_now);
                }
                break;
            case R.id.reg_input_captcha_edit_text:
                if (hasFocus) {
                    ((EditText) v).setSelection(0);
                    updateOnTextChanged(null, b_captcha_empty);
                    updateDrawableOnFocusChanged(reg_input_captcha_icon, android.R.drawable.ic_menu_add);
                } else {
                    updateDrawableOnFocusChanged(reg_input_captcha_icon, android.R.drawable.ic_input_add);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    public interface OnReturnLoginBtnClickedListener {
        void switchToLoginFragment();
    }
}
