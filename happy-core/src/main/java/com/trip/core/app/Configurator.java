package com.trip.core.app;

import java.util.HashMap;


/**
 * Created by Administrator on 2017/10/16.
 */

public class Configurator {

    private static final HashMap<Object, Object> HAPPY_CONFIGS = new HashMap<>();

    private Configurator() {
        HAPPY_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getLatteConfigs() {
        return HAPPY_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final Configurator withApiHost(String host) {
        HAPPY_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final void configure() {
        HAPPY_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) HAPPY_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = HAPPY_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) HAPPY_CONFIGS.get(key);
    }



}
