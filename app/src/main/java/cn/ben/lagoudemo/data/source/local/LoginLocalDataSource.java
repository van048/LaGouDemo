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

package cn.ben.lagoudemo.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import cn.ben.lagoudemo.constant.Constants;
import cn.ben.lagoudemo.data.UserAuthInfo;
import cn.ben.lagoudemo.data.source.LoginDataSource;
import cn.ben.lagoudemo.data.source.local.LoginPersistenceContract.UserAuthInfoEntry;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation of a data source as a db.
 */
public class LoginLocalDataSource implements LoginDataSource {

    private static LoginLocalDataSource INSTANCE;

    private LoginDbHelper mDbHelper;

    // Prevent direct instantiation.
    private LoginLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new LoginDbHelper(context);
    }

    public static LoginLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LoginLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void verifyUser(@NonNull UserAuthInfo userAuthInfo, @NonNull VerifyUserCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] columns = {
                UserAuthInfoEntry.COLUMN_NAME_ENTRY_ID,
                UserAuthInfoEntry.COLUMN_NAME_NAME,
                UserAuthInfoEntry.COLUMN_NAME_PASSWORD,
                UserAuthInfoEntry.COLUMN_NAME_VALID
        };
        String selection = UserAuthInfoEntry.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = new String[]{userAuthInfo.getName()};


        Cursor c = db.query(
                UserAuthInfoEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean hasOne = false;

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String name = c.getString(c.getColumnIndexOrThrow(UserAuthInfoEntry.COLUMN_NAME_NAME));
                String password = c.getString(c.getColumnIndexOrThrow(UserAuthInfoEntry.COLUMN_NAME_PASSWORD));
                boolean valid =
                        c.getInt(c.getColumnIndexOrThrow(UserAuthInfoEntry.COLUMN_NAME_VALID)) == 1;
                if (userAuthInfo.matchesAllProperties(name, password, valid)) {
                    hasOne = true;
                    break;
                }
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (hasOne) {
            callback.onVerifiedSuccess();
        } else {
            callback.onVerifiedFailed(Constants.ErrorMessage.USER_NOT_FOUND.getMessage());
        }
    }
}
