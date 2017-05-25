package com.zent.sleep;

import io.realm.RealmConfiguration;

/**
 * Created by Fabian Choi on 5/23/2017.
 * Contains realm configuration
 * TODO: Implement migration logic
 */
public class Realm {
    public static RealmConfiguration getConfig() {
        return new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded()
                .build();
    }
}
