package cn.ben.lagoudemo.Login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.ben.lagoudemo.R;

public class LoginActivity extends Activity implements LoginView, View.OnClickListener, View.OnFocusChangeListener {

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private LoginPresenter loginPresenter;
    @SuppressWarnings("FieldCanBeLocal")
    private View login_wander;
    private ImageView login_input_username_icon;
    private EditText login_input_username_edit_text;
    private EditText login_input_pw_edit_text;
    private ImageView login_input_pw_icon;
    private View animGroup1;
    private View animGroup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenterImpl(this, this);

        login_wander = findViewById(R.id.login_wander);
        login_wander.setOnClickListener(this);
        login_input_username_edit_text = (EditText) findViewById(R.id.login_input_username_edit_text);
        login_input_username_icon = (ImageView) findViewById(R.id.login_input_username_icon);
        login_input_pw_edit_text = (EditText) findViewById(R.id.login_input_pw_edit_text);
        login_input_pw_icon = (ImageView) findViewById(R.id.login_input_pw_icon);
        animGroup1 = findViewById(R.id.login_anim_group_1);
        animGroup2 = findViewById(R.id.login_anim_group_2);

        login_input_username_edit_text.setOnFocusChangeListener(this);
        login_input_pw_edit_text.setOnFocusChangeListener(this);
        login_input_username_icon.setOnClickListener(this);
        login_input_pw_icon.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        login_input_username_edit_text.clearFocus();
        login_input_pw_edit_text.clearFocus();
    }

    public void startAnim(boolean isOpen) {
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
                    animGroup2.setTranslationY(-120);
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
                    animGroup1.setScaleX(1f);
                    animGroup1.setScaleY(1f);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
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
            case R.id.login_wander:
                moveToMainPage();
                break;
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

    private void moveToMainPage() {
        //// TODO: 2016/5/25
        System.out.println("moveToMainPage");
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

    private int backPressedCount = 0;
    @SuppressWarnings("FieldCanBeLocal")
    private final int BACK_PRESSED_CHECK_TIME = 2000;
    private final Handler BACK_PRESSED_CHECK_HANDLER = new Handler();
    private boolean backPressedCheckEnabled = true;

    @Override
    public void onBackPressed() {
        if (backPressedCheckEnabled && backPressedCount == 0) {
            backPressedCount++;
            Toast.makeText(LoginActivity.this, R.string.toast_back_pressed, Toast.LENGTH_SHORT).show();
            BACK_PRESSED_CHECK_HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressedCount = 0;
                }
            }, BACK_PRESSED_CHECK_TIME);
        } else {
            backPressedCheckEnabled = false;
            super.onBackPressed();
        }
    }
}
