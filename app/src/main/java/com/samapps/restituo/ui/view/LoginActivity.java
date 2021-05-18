package com.samapps.restituo.ui.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.samapps.restituo.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameEditText = findViewById(R.id.username);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final CountryCodePicker et1 = findViewById(R.id.et1);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String mob=et1.getSelectedCountryCodeWithPlus().concat(usernameEditText.getText().toString());
                Toast.makeText(getApplicationContext(),mob,Toast.LENGTH_LONG).show();

                loadingProgressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(LoginActivity.this,VerifyPhoneActivity.class).putExtra("mobile",mob));
               // startActivity(new Intent(LoginActivity.this,UserProfileActivity.class));

            }
        });
    }

}
