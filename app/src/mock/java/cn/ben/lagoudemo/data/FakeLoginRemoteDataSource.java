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

import android.support.annotation.VisibleForTesting;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.ben.lagoudemo.data.source.LoginDataSource;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
public class FakeLoginRemoteDataSource implements LoginDataSource {

    private static FakeLoginRemoteDataSource INSTANCE;

    private static final Map<String, UserAuthInfo> USER_AUTH_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    private FakeLoginRemoteDataSource() {
    }

    public static FakeLoginRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeLoginRemoteDataSource();
        }
        return INSTANCE;
    }

    // TODO: 2016/12/16  
    @VisibleForTesting
    public void addMultipleUserAuthInfo(UserAuthInfo... multipleUserAuthInfo) {
        for (UserAuthInfo userAuthInfo : multipleUserAuthInfo) {
            USER_AUTH_SERVICE_DATA.put(userAuthInfo.getId(), userAuthInfo);
        }
    }

    @Override
    public void verifyUser(UserAuthInfo userAuthInfo) {
        // TODO: 2016/12/16
    }
}
