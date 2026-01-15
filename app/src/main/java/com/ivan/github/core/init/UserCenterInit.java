package com.ivan.github.core.init;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.ivan.github.account.IUserCenter;
import com.ivan.github.account.UserCenterImpl;

import java.util.Arrays;
import java.util.List;

public class UserCenterInit extends AbsInitializer<IUserCenter>{

    @NonNull
    @Override
    public String getTag() {
        return "UserCenter";
    }

    @Override
    public IUserCenter onCreate(Context context) {
        UserCenterImpl userCenter = UserCenterImpl.getInstance();
        userCenter.loadUser();
        return userCenter;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Arrays.asList(LogInit.class, AppInit.class);
    }
}
