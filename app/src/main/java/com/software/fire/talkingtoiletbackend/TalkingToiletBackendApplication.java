package com.software.fire.talkingtoiletbackend;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Brad on 12/5/2016.
 */

public class TalkingToiletBackendApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //used to persist data. This saves us from manually creating and handling local storage and
        //creating services/background tasks/buses to interact with data
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}
