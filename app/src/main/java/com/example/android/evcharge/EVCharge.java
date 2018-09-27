package com.example.android.evcharge;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class EVCharge extends Application {
    private boolean prepaid;
    private boolean postpaid;
    private boolean regular;
    // for FIREBASE
    //launches every time for every activity..so all activities can use firebase


    @Override
    public void onCreate() {
        super.onCreate();
        //Firebase.setAndroidContext(this);
        FirebaseApp.initializeApp(getApplicationContext());
    }

    public boolean isPrepaid() {
        return prepaid;
    }

    public void setPrepaid(boolean prepaid) {
        this.prepaid = prepaid;
    }

    public boolean isPostpaid() {
        return postpaid;
    }

    public void setPostpaid(boolean postpaid) {
        this.postpaid = postpaid;
    }

    public boolean isRegular() {
        return regular;
    }

    public void setRegular(boolean regular) {
        this.regular = regular;
    }
}
