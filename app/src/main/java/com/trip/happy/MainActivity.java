package com.trip.happy;


import com.trip.happy.delegates.HomeDelegate;
import com.trip.happy.delegates.activities.ProxyActivity;
import com.trip.happy.delegates.base.HappyDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public HappyDelegate setRootDelegate() {
        return new HomeDelegate();
    }
}
