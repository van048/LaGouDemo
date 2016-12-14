package cn.ben.lagoudemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.utils.KeyboardUtils;
import com.blankj.utilcode.utils.StringUtils;

import ben.cn.library.ui.fragment.BaseFragment;
import cn.ben.lagoudemo.R;
import cn.ben.lagoudemo.ui.contract.login.LoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginLoginFragment extends BaseFragment implements LoginContract.View, View.OnClickListener, View.OnFocusChangeListener{

    private LoginContract.Presenter mPresenter;

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
        login_input_user_name_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                b_user_name_empty = StringUtils.isEmpty(charSequence);
                updateOnTextChanged(login_input_user_name_delete, b_user_name_empty);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        login_input_pw_edit_text.setOnFocusChangeListener(this);
        login_input_pw_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                b_user_pw_empty = StringUtils.isEmpty(charSequence);
                updateOnTextChanged(login_input_pw_delete, b_user_pw_empty);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        login_input_user_name_icon.setOnClickListener(this);
        login_input_pw_icon.setOnClickListener(this);

        // init state
        login_login_btn.setEnabled(false);
        login_input_user_name_edit_text.requestFocus();
    }

    private void updateOnTextChanged(View deleteView, boolean isEmpty) {
        login_login_btn.setEnabled(!b_user_name_empty && !b_user_pw_empty);
        deleteView.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
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
                    updateDrawableOnFocusChanged(login_input_user_name_icon, android.R.drawable.ic_media_pause);
                } else {
                    updateDrawableOnFocusChanged(login_input_user_name_icon, android.R.drawable.ic_media_play);
                }
                break;
            case R.id.login_input_pw_edit_text:
                if (hasFocus) {
                    updateDrawableOnFocusChanged(login_input_pw_icon, android.R.drawable.ic_lock_idle_lock);
                } else {
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
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
