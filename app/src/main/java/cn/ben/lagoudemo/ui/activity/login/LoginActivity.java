package cn.ben.lagoudemo.ui.activity.login;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.utils.StringUtils;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import ben.cn.library.activity.BaseEntryActivity;
import cn.ben.lagoudemo.R;

public class LoginActivity extends BaseEntryActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private static final float EDIT_TEXT_ANIM_FINAL_Y = -120;
    private static final long ANIM_DURATION = 300;

    private ImageView login_input_user_name_icon;
    private EditText login_input_user_name_edit_text;
    private ImageView login_input_user_name_delete;
    private ImageView login_input_pw_icon;
    private EditText login_input_pw_edit_text;
    private ImageView login_input_pw_delete;
    private View login_animGroup_logo;
    private View login_animGroup_edit_text;
    private Button login_login_btn;

    // animation
    private boolean isKeyboardOpen = false; // latest param of startAnim
    private ValueAnimator mValueAnimatorKeyboardOpen, mValueAnimatorKeyboardClose;
    // may reverse direction when one animation running
    private float mLatestScaleOpen, mLatestScaleClose = 1;

    private boolean b_user_name_empty = true;
    private boolean b_user_pw_empty = true;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        setUpAnimator();
    }

    private void setUpAnimator() {
        mValueAnimatorKeyboardOpen = ValueAnimator.ofFloat(0, 1);
        mValueAnimatorKeyboardOpen.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                value += (1 - mLatestScaleClose);
                if (value > 1) value = 1;
                value = 1 - value;
                mLatestScaleOpen = value;
                updateAnimGroupPos(value);
            }
        });

        mValueAnimatorKeyboardClose = ValueAnimator.ofFloat(0, 1);
        mValueAnimatorKeyboardClose.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                value += mLatestScaleOpen;
                if (value > 1) value = 1;
                mLatestScaleClose = value;
                updateAnimGroupPos(value);
            }
        });

        mValueAnimatorKeyboardOpen.setDuration(ANIM_DURATION);
        mValueAnimatorKeyboardClose.setDuration(ANIM_DURATION);
    }

    private void updateAnimGroupPos(float logoScale) {
        login_animGroup_logo.setScaleX(logoScale);
        login_animGroup_logo.setScaleY(logoScale);
        login_animGroup_edit_text.setTranslationY(EDIT_TEXT_ANIM_FINAL_Y * (1 - logoScale));
    }

    @Override
    protected int getThemeResourceID() {
        return R.style.DefaultFullScreenTheme;
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpView() {
        super.setUpView();

        login_input_user_name_edit_text = $(R.id.login_input_username_edit_text);
        login_input_user_name_icon = $(R.id.login_input_username_icon);
        login_input_user_name_delete = $(R.id.login_input_username_delete);
        login_input_pw_edit_text = $(R.id.login_input_pw_edit_text);
        login_input_pw_icon = $(R.id.login_input_pw_icon);
        login_input_pw_delete = $(R.id.login_input_pw_delete);
        login_login_btn = $(R.id.login_login_btn);
        login_animGroup_logo = $(R.id.login_anim_group_1);
        login_animGroup_edit_text = $(R.id.login_anim_group_2);

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

        KeyboardVisibilityEvent.setEventListener(LoginActivity.this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                startAnim(isOpen);
            }
        });
    }

    private void updateOnTextChanged(View deleteView, boolean isEmpty) {
        login_login_btn.setEnabled(!b_user_name_empty && !b_user_pw_empty);
        deleteView.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        login_input_user_name_edit_text.clearFocus();
        login_input_pw_edit_text.clearFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // original state
        isKeyboardOpen = false;
        login_animGroup_logo.setScaleX(1);
        login_animGroup_logo.setScaleY(1);
        login_animGroup_edit_text.setTranslationY(0);
    }

    private void startAnim(boolean isOpen) {
        //  animate once
        if (isOpen == isKeyboardOpen) return;

        mValueAnimatorKeyboardOpen.cancel();
        mValueAnimatorKeyboardClose.cancel();

        isKeyboardOpen = isOpen;
        if (isKeyboardOpen) mValueAnimatorKeyboardOpen.start();
        else mValueAnimatorKeyboardClose.start();
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
        showKeyboard(v);
    }

    private void showKeyboard(EditText v) {
        final InputMethodManager inputMethodManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
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
        icon.setImageDrawable(getResources().getDrawable(drawableID, getTheme()));
    }

    @Override
    protected int getExitHintResourceID() {
        return R.string.toast_back_pressed;
    }
}
