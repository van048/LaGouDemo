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

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.UUID;

public final class UserAuthInfo {

    @NonNull
    private final String mId;

    @NonNull
    private final String mName;

    @NonNull
    private final String mPassword;

    public UserAuthInfo(@NonNull String name, @NonNull String password) {
        this(name, password, UUID.randomUUID().toString());
    }

    public UserAuthInfo(@NonNull String name, @NonNull String password, @NonNull String id) {
        mId = id;
        mName = name;
        mPassword = password;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getPassword() {
        return mPassword;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mName) &&
                Strings.isNullOrEmpty(mPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthInfo userAuthInfo = (UserAuthInfo) o;
        return Objects.equal(mId, userAuthInfo.mId) &&
                Objects.equal(mName, userAuthInfo.mName) &&
                Objects.equal(mPassword, userAuthInfo.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mPassword);
    }
}
