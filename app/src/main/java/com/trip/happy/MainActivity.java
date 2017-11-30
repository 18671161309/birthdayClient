package com.trip.happy;


import android.view.KeyEvent;

import com.trip.happy.delegates.HomeDelegate;
import com.trip.happy.delegates.activities.ProxyActivity;
import com.trip.happy.delegates.base.HappyDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public HappyDelegate setRootDelegate() {
        return new HomeDelegate();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }

        return super.dispatchKeyEvent(event);
    }





}
