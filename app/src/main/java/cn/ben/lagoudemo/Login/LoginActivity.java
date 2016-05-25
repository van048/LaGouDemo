package cn.ben.lagoudemo.Login;

import android.app.Activity;
import android.os.Bundle;

import cn.ben.lagoudemo.R;

public class LoginActivity extends Activity implements LoginView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
