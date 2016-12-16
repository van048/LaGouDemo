/*
 * Copyright (C) 2015 The Android Open Source Project
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

package cn.ben.lagoudemo;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.ben.lagoudemo.data.source.LoginDataSource;
import cn.ben.lagoudemo.data.source.LoginRepository;
import cn.ben.lagoudemo.data.source.local.LoginLocalDataSource;
import cn.ben.lagoudemo.data.source.remote.LoginRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of mock implementations for
 * {@link LoginDataSource} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static LoginRepository provideLoginRepository(@NonNull Context context) {
        checkNotNull(context);
        return LoginRepository.getInstance(LoginRemoteDataSource.getInstance(),
                LoginLocalDataSource.getInstance(context));
    }
}
