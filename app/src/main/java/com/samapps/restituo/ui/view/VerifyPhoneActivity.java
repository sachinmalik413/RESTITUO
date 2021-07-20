package com.samapps.restituo.ui.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.samapps.restituo.R;
import com.samapps.restituo.util.FireBaseAuthUtil;

public class VerifyPhoneActivity extends AppCompatActivity implements FireBaseAuthUtil.CallBack {

    FireBaseAuthUtil fireBaseAuthUtil;
    EditText editTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        editTextNumber = findViewById(R.id.editTextNumber);
        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        fireBaseAuthUtil = new FireBaseAuthUtil(this);
        fireBaseAuthUtil.sendVerificationCode(mobile);
        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = ((EditText)findViewById(R.id.editTextNumber)).getText().toString().trim();
                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    fireBaseAuthUtil.verifyVerificationCode(code);
                }

            }
        });
    }

    @Override
    public void codeRecieved(String code) {
        editTextNumber.setText(code);
    }
}