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

package cn.ben.lagoudemo.data.source;

import android.support.annotation.NonNull;

import cn.ben.lagoudemo.data.UserAuthInfo;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginRepository implements LoginDataSource {

    private static LoginRepository INSTANCE = null;

    private final LoginDataSource mLoginRemoteDataSource;

    private final LoginDataSource mLoginLocalDataSource;

    // Prevent direct instantiation.
    private LoginRepository(@NonNull LoginDataSource loginRemoteDataSource,
                            @NonNull LoginDataSource loginLocalDataSource) {
        mLoginRemoteDataSource = checkNotNull(loginRemoteDataSource);
        mLoginLocalDataSource = checkNotNull(loginLocalDataSource);
    }

    public static LoginRepository getInstance(LoginDataSource loginRemoteDataSource,
                                              LoginDataSource loginLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new LoginRepository(loginRemoteDataSource, loginLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void verifyUser(@NonNull UserAuthInfo userAuthInfo, @NonNull VerifyUserCallback callback) {
        // TODO: 2016/12/16  
    }
}
