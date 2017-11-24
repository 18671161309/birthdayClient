package com.trip.core.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/16.
 */

public final class Happy {

    public static Configurator init(Context context) {
        Configurator.getInstance().getLatteConfigs().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }

    public static Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static <T> T getConfiguration(Object key) {
        return Configurator.getInstance().getConfiguration(key);
    }


}
