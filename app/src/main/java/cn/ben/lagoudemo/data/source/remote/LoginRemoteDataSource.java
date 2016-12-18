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

package cn.ben.lagoudemo.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import cn.ben.lagoudemo.constant.Constants;
import cn.ben.lagoudemo.data.UserAuthInfo;
import cn.ben.lagoudemo.data.source.LoginDataSource;

/**
 * Implementation of the data source that adds a latency simulating network.
 */
// TODO: 2016/12/18 real login
public class LoginRemoteDataSource implements LoginDataSource {

    private static LoginRemoteDataSource INSTANCE;

    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;

    private final static Map<String, UserAuthInfo> USER_AUTH_SERVICE_DATA;

    static {
        USER_AUTH_SERVICE_DATA = new HashMap<>(2);
        addUserAuthInfo("van048", "28853048", true);
        addUserAuthInfo("van04825", "yyb28853048", false);
    }

    public static LoginRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private LoginRemoteDataSource() {
    }

    private static void addUserAuthInfo(String name, String password, boolean valid) {
        UserAuthInfo newUserAuthInfo = new UserAuthInfo(name, password, valid);
        USER_AUTH_SERVICE_DATA.put(name, newUserAuthInfo);
    }

    @Override
    public void verifyUser(@NonNull final UserAuthInfo userAuthInfo, @NonNull final VerifyUserCallback callback) {
        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UserAuthInfo tmpUserAuthInfo = USER_AUTH_SERVICE_DATA.get(userAuthInfo.getName());
                if (userAuthInfo.equals(tmpUserAuthInfo)) {
                    callback.onVerifiedSuccess();
                    return;
                }
                callback.onVerifiedFailed(Constants.ErrorMessage.USER_NOT_FOUND.getMessage());
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }
}
