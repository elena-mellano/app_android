package com.yumba.app;


/**
 * Created by elena on 09/03/14.
 */

 import android.os.Bundle;
 import android.preference.PreferenceActivity;
 import android.preference.PreferenceManager;


public class PrefActivity extends PreferenceActivity { //
    @Override
    protected void onCreate(Bundle savedInstanceState) { //
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref); //

    }
}