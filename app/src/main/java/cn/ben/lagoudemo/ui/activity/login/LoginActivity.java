package cn.ben.lagoudemo.ui.activity.login;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import ben.cn.library.activity.BaseEntryActivity;
import cn.ben.lagoudemo.R;

public class LoginActivity extends BaseEntryActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private ImageView login_input_username_icon;
    private EditText login_input_username_edit_text;
    private EditText login_input_pw_edit_text;
    private ImageView login_input_pw_icon;
    private View animGroup1;
    private View animGroup2;
    private boolean isKeyboardOpen = false;
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

        login_input_username_edit_text = $(R.id.login_input_username_edit_text);
        login_input_username_icon = $(R.id.login_input_username_icon);
        login_input_pw_edit_text = $(R.id.login_input_pw_edit_text);
        login_input_pw_icon = $(R.id.login_input_pw_icon);
        animGroup1 = $(R.id.login_anim_group_1);
        animGroup2 = $(R.id.login_anim_group_2);

        login_input_username_edit_text.setOnFocusChangeListener(this);
        login_input_pw_edit_text.setOnFocusChangeListener(this);
        login_input_username_icon.setOnClickListener(this);
        login_input_pw_icon.setOnClickListener(this);

        KeyboardVisibilityEvent.setEventListener(LoginActivity.this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                startAnim(isOpen);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        login_input_username_edit_text.clearFocus();
        login_input_pw_edit_text.clearFocus();
    }

    public void startAnim(boolean isOpen) {
        if (isOpen == isKeyboardOpen) return;
        isKeyboardOpen = isOpen;

        Animation scaleDown = AnimationUtils.loadAnimation(this, R.anim.login_scale_down_anim);
        Animation moveUp = AnimationUtils.loadAnimation(this, R.anim.login_move_up_anim);
        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.login_scale_up_anim);
        Animation moveDown = AnimationUtils.loadAnimation(this, R.anim.login_move_down_anim);
        Animation anim1, anim2;

        if (isOpen) {
            anim1 = scaleDown;
            anim2 = moveUp;
            anim1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    animGroup1.setScaleX(0f);
                    animGroup1.setScaleY(0f);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            anim2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    animGroup2.setTranslationY(-120f);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else {
            anim1 = scaleUp;
            anim2 = moveDown;
            anim1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    animGroup1.setScaleX(1f);
                    animGroup1.setScaleY(1f);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            anim2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    animGroup2.setTranslationY(0);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        animGroup1.startAnimation(anim1);
        animGroup2.startAnimation(anim2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_input_username_icon:
                clickOnIconBeforeEditText(login_input_username_edit_text);
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
                    login_input_username_icon.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_month, getTheme()));
                } else {
                    login_input_username_icon.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_btn_speak_now, getTheme()));
                }
                break;
            case R.id.login_input_pw_edit_text:
                if (hasFocus) {
                    login_input_pw_icon.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_day, getTheme()));
                } else {
                    login_input_pw_icon.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_lock_lock, getTheme()));
                }
                break;
            default:
                break;
        }
    }
}
