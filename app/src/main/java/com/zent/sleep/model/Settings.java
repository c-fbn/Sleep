package com.zent.sleep.model;

import io.realm.RealmObject;

/**
 * Created by Fabian Choi on 5/23/2017.
 */

public class Settings extends RealmObject {
    private boolean isEnabled;
    private boolean isNightMode; // TODO: Implement. On by default

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isNightMode() {
        return isNightMode;
    }

    public void setNightMode(boolean nightMode) {
        isNightMode = nightMode;
    }
}
