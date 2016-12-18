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

package cn.ben.lagoudemo.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.HashMap;
import java.util.Map;

import cn.ben.lagoudemo.constant.Constants;
import cn.ben.lagoudemo.data.source.LoginDataSource;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
public class FakeLoginRemoteDataSource implements LoginDataSource {

    private static FakeLoginRemoteDataSource INSTANCE;

    private static final Map<String, UserAuthInfo> USER_AUTH_SERVICE_DATA = new HashMap<>();

    // Prevent direct instantiation.
    private FakeLoginRemoteDataSource() {
    }

    public static FakeLoginRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeLoginRemoteDataSource();
        }
        return INSTANCE;
    }

    // TODO: 2016/12/16 use in tests
    @VisibleForTesting
    public void addMultipleUserAuthInfo(UserAuthInfo... multipleUserAuthInfo) {
        for (UserAuthInfo userAuthInfo : multipleUserAuthInfo) {
            USER_AUTH_SERVICE_DATA.put(userAuthInfo.getName(), userAuthInfo);
        }
    }

    @Override
    public void verifyUser(@NonNull UserAuthInfo userAuthInfo, @NonNull VerifyUserCallback callback) {
        UserAuthInfo tmpUserAuthInfo = USER_AUTH_SERVICE_DATA.get(userAuthInfo.getName());
        if (userAuthInfo.equals(tmpUserAuthInfo)) {
            callback.onVerifiedSuccess();
            return;
        }
        callback.onVerifiedFailed(Constants.ErrorMessage.USER_NOT_FOUND.getMessage());
    }
}
