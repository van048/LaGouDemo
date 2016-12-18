package cn.ben.lagoudemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.utils.KeyboardUtils;
import com.blankj.utilcode.utils.StringUtils;

import ben.cn.library.constants.Constants;
import ben.cn.library.ui.fragment.BaseFragment;
import ben.cn.library.utils.MyToastUtils;
import cn.ben.lagoudemo.R;
import cn.ben.lagoudemo.ui.adapter.TextWatcherAdapter;
import cn.ben.lagoudemo.ui.contract.LoginLoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginLoginFragment extends BaseFragment implements LoginLoginContract.View, View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {

    private LoginLoginContract.Presenter mPresenter;

    private ImageView login_input_user_name_icon;
    private EditText login_input_user_name_edit_text;
    private ImageView login_input_user_name_delete;
    private ImageView login_input_pw_icon;
    private EditText login_input_pw_edit_text;
    private ImageView login_input_pw_delete;
    private Button login_login_btn;

    // edit text state
    private boolean b_user_name_empty = true;
    private boolean b_user_pw_empty = true;

    public static LoginLoginFragment newInstance() {
        return new LoginLoginFragment();
    }

    public LoginLoginFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
    }

    private void setUpView() {
        login_input_user_name_edit_text = $(R.id.login_input_username_edit_text);
        login_input_user_name_icon = $(R.id.login_input_username_icon);
        login_input_user_name_delete = $(R.id.login_input_username_delete);
        login_input_pw_edit_text = $(R.id.login_input_pw_edit_text);
        login_input_pw_icon = $(R.id.login_input_pw_icon);
        login_input_pw_delete = $(R.id.login_input_pw_delete);
        login_login_btn = $(R.id.login_login_btn);

        login_input_user_name_edit_text.setOnFocusChangeListener(this);
        login_input_user_name_edit_text.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                b_user_name_empty = StringUtils.isEmpty(charSequence);
                updateOnTextChanged(login_input_user_name_delete, b_user_name_empty);
            }
        });
        login_input_user_name_edit_text.setOnKeyListener(this);

        login_input_pw_edit_text.setOnFocusChangeListener(this);
        login_input_pw_edit_text.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                b_user_pw_empty = StringUtils.isEmpty(charSequence);
                updateOnTextChanged(login_input_pw_delete, b_user_pw_empty);
            }
        });
        login_input_pw_edit_text.setOnKeyListener(this);

        login_input_user_name_icon.setOnClickListener(this);
        login_input_pw_icon.setOnClickListener(this);
        login_input_user_name_delete.setOnClickListener(this);
        login_input_pw_delete.setOnClickListener(this);
        login_login_btn.setOnClickListener(this);
    }

    private void updateOnTextChanged(View deleteView, boolean isEmpty) {
        login_login_btn.setEnabled(!b_user_name_empty && !b_user_pw_empty);
        deleteView.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();

        // init state
        login_login_btn.setEnabled(false);
        login_input_user_name_edit_text.requestFocus();
    }

    @Override
    public void onPause() {
        super.onPause();

        login_input_user_name_edit_text.clearFocus();
        login_input_pw_edit_text.clearFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_input_username_icon:
                clickOnIconBeforeEditText(login_input_user_name_edit_text);
                break;
            case R.id.login_input_pw_icon:
                clickOnIconBeforeEditText(login_input_pw_edit_text);
                break;
            case R.id.login_input_username_delete:
                login_input_user_name_edit_text.setText(Constants.EMPTY_STRING);
                break;
            case R.id.login_input_pw_delete:
                login_input_pw_edit_text.setText(Constants.EMPTY_STRING);
                break;
            case R.id.login_login_btn:
                KeyboardUtils.hideSoftInput(getActivity());
                mPresenter.verifyUser(login_input_user_name_edit_text.getText().toString(), login_input_pw_edit_text.getText().toString());
                break;
            default:
                break;
        }
    }

    private void clickOnIconBeforeEditText(EditText v) {
        v.requestFocus();
        v.setSelection(0);
        KeyboardUtils.showSoftInput(getContext(), v);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.login_input_username_edit_text:
                if (hasFocus) {
                    ((EditText) v).setSelection(0);
                    updateOnTextChanged(login_input_user_name_delete, b_user_name_empty);
                    updateDrawableOnFocusChanged(login_input_user_name_icon, android.R.drawable.ic_media_pause);
                } else {
                    login_input_user_name_delete.setVisibility(View.INVISIBLE);
                    updateDrawableOnFocusChanged(login_input_user_name_icon, android.R.drawable.ic_media_play);
                }
                break;
            case R.id.login_input_pw_edit_text:
                if (hasFocus) {
                    ((EditText) v).setSelection(0);
                    updateOnTextChanged(login_input_pw_delete, b_user_pw_empty);
                    updateDrawableOnFocusChanged(login_input_pw_icon, android.R.drawable.ic_lock_idle_lock);
                } else {
                    login_input_pw_delete.setVisibility(View.INVISIBLE);
                    updateDrawableOnFocusChanged(login_input_pw_icon, android.R.drawable.ic_lock_lock);
                }
                break;
            default:
                break;
        }
    }

    private void updateDrawableOnFocusChanged(ImageView icon, int drawableID) {
        icon.setImageDrawable(getResources().getDrawable(drawableID, getContext().getTheme()));
    }

    @Override
    public void setPresenter(LoginLoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (v == login_input_user_name_edit_text && keyCode == KeyEvent.KEYCODE_ENTER) {
            v.clearFocus();
            return true;
        }
        if (v == login_input_pw_edit_text && keyCode == KeyEvent.KEYCODE_ENTER) {
            KeyboardUtils.hideSoftInput(getActivity());
            return true;
        }
        return false;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void moveToMainPage() {
        // TODO: 2016/12/16
        MyToastUtils.showLongToastSafe(getContext(), "moveToMainPage", true);
    }

    @Override
    public void showVerifyErrorMessage(String errorMessage) {
        MyToastUtils.showShortToastSafe(getContext(), errorMessage, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
