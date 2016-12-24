package cn.ben.lagoudemo.ui.presenter;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.blankj.utilcode.utils.RegexUtils;

import cn.ben.lagoudemo.data.source.LoginRepository;
import cn.ben.lagoudemo.ui.contract.LoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginRegPresenter implements LoginContract.RegPresenter {

    private final LoginRepository mLoginRepository;

    private final LoginContract.RegView mLoginRegView;

    public LoginRegPresenter(@NonNull LoginRepository loginRepository, @NonNull LoginContract.RegView loginRegView) {
        mLoginRepository = checkNotNull(loginRepository, "loginRepository cannot be null");
        mLoginRegView = checkNotNull(loginRegView, "loginRegView cannot be null!");

        mLoginRegView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public boolean checkPhoneValidity(String phoneNumber) {
        return RegexUtils.isMobileExact(phoneNumber);
    }

    @Override
    public void getCaptcha() {
        if (mLoginRegView.isActive()) {
            mLoginRegView.showGettingCaptchaUI();
        } else {
            return;
        }
        // TODO: 2016/12/24

        // simulate the progress of getting captcha
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLoginRegView.isActive()) {
                    // TODO: 2016/12/24
                    // success
                    mLoginRegView.showCaptchaGotSuccessUI("success message");
                    // failed
//                    mLoginRegView.showCaptchaGotFailedUI("error message");
                }
            }
        }, 3000);
    }
}