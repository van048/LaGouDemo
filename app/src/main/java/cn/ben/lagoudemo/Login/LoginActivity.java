package cn.ben.lagoudemo.Login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import cn.ben.lagoudemo.R;

public class LoginActivity extends Activity implements LoginView, View.OnClickListener {

    @SuppressWarnings("unused")
    private static final String TAG = "LoginActivity";
    @SuppressWarnings("FieldCanBeLocal")
    private View login_wander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_wander = findViewById(R.id.login_wander);
        login_wander.setOnClickListener(this);

        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                startAnim(isOpen);
            }
        });
    }

    private void startAnim(boolean isOpen) {
        Animation scaleDown = AnimationUtils.loadAnimation(this, R.anim.login_scale_down_anim);
        Animation moveUp = AnimationUtils.loadAnimation(this, R.anim.login_move_up_anim);
        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.login_scale_up_anim);
        Animation moveDown = AnimationUtils.loadAnimation(this, R.anim.login_move_down_anim);
        final View animGroup1 = findViewById(R.id.login_anim_group_1);
        final View animGroup2 = findViewById(R.id.login_anim_group_2);
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
            default:
                break;
        }
    }

    private void moveToMainPage() {
        //// TODO: 2016/5/25
        System.out.println("moveToMainPage");
    }
}
