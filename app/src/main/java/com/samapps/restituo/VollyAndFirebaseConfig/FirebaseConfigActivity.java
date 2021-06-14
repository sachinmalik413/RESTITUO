package com.samapps.restituo.VollyAndFirebaseConfig;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.samapps.restituo.R;

public class FirebaseConfigActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView textViewTitle, textViewDescription;
    Button buttonMoreInfo;
    // Remote Config keys
    private static final String TITLE_KEY = "title";
    private static final String DESCRIPTION_KEY = "description";
    private static final String MORE_INFO_KEY = "more_info";
    // Firebase Remote Config
    private FirebaseRemoteConfig firebaseRemoteConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_config);
        // init view
        textViewTitle = findViewById(R.id.tvTitle);
        textViewDescription = findViewById(R.id.tvDescription);
        buttonMoreInfo = findViewById(R.id.btnMore);
        // Get Firebase Remote Config instance.
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // Create a Remote Config Setting to enable developer mode,
        FirebaseRemoteConfigSettings.Builder configBuilder = new FirebaseRemoteConfigSettings.Builder();
        // Sets the minimum interval between successive fetch calls.
        /**
         * For developer mode I'm setting 0 (zero) second
         * The default mode is 12 Hours. So for production mode it will be 12 hours
         */
        if (BuildConfig.DEBUG) {
            long cacheInterval = 0;
            configBuilder.setMinimumFetchIntervalInSeconds(cacheInterval);
        }
        // finally build config settings and sets to Remote Config
        firebaseRemoteConfig.setConfigSettingsAsync(configBuilder.build());
        /**
         *  Set default Remote Config parameter values. An app uses the in-app default values, and
         * when you need to adjust those defaults, you set an updated value for only the values you
         * want to change in the Firebase console
         */
        //set default values
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        fetchRemoteTitle();
    }
    private void fetchRemoteTitle() {
        // set text from remote
        textViewTitle.setText(firebaseRemoteConfig.getString(TITLE_KEY));
        textViewDescription.setText(firebaseRemoteConfig.getString(DESCRIPTION_KEY));
        buttonMoreInfo.setText(firebaseRemoteConfig.getString(MORE_INFO_KEY));
        // [START fetch_config_with_callback]
        // override default value from Remote Config
        firebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d(TAG, "Config params updated: " + updated);
                            Toast.makeText(FirebaseConfigActivity.this, "Fetch and activate succeeded",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FirebaseConfigActivity.this, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END fetch_config_with_callback]
    }
}