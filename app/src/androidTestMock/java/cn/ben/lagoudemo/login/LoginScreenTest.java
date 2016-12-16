/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.ben.lagoudemo.login;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cn.ben.lagoudemo.R;
import cn.ben.lagoudemo.data.FakeLoginRemoteDataSource;
import cn.ben.lagoudemo.data.UserAuthInfo;
import cn.ben.lagoudemo.data.source.LoginRepository;
import cn.ben.lagoudemo.ui.activity.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Tests for the login screen.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginScreenTest {

    // TODO: 2016/12/16
    // second param
    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class, true, false);

    @Before
    public void addAuthInfoAndStartActivity() {
        // Given some entries
        LoginRepository.destroyInstance();
        FakeLoginRemoteDataSource.getInstance().addMultipleUserAuthInfo(new UserAuthInfo("van048", "yyb28853048"), new UserAuthInfo("lustyi1", "mima12345"));

        // Lazily start the Activity from the ActivityTestRule
        Intent startIntent = new Intent();
        mLoginActivityTestRule.launchActivity(startIntent);
    }

    @Test
    public void Login_LoginSuccess() throws Exception {
        // TODO: 2016/12/16
        onView(withId(R.id.login_input_username_edit_text)).check(matches(hasFocus()));
    }

    @Test
    public void Login_LoginFail() throws Exception {
        // TODO: 2016/12/16
        onView(withId(R.id.login_input_pw_edit_text)).check(matches(not(hasFocus())));
    }
}