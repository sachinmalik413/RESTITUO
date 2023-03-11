package com.samapps.restituo.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.samapps.restituo.R;
import com.samapps.restituo.util.FireBaseAuthUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity implements FireBaseAuthUtil.CallBack {

    // variable for FirebaseAuth class
    private FirebaseAuth mAuth;

    // for volly api
    private RequestQueue mRequestQueue;
    // variable for our text input
    // field for phone and OTP.
    EditText edtOTP,edtPh;
    Button verifyOTPBtn;

    // string for storing our verification ID
    private String verificationId;
    FireBaseAuthUtil fireBaseAuthUtil;
    EditText editTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        editTextNumber = findViewById(R.id.editTextNumber);

        // below line is for getting instance
        // of our FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();
        // for  volly request queue
        mRequestQueue = Volley.newRequestQueue(this);

        // initializing variables for button and Edittext.

        // edit text find view
        edtOTP = findViewById(R.id.editTextNumber);
        verifyOTPBtn = findViewById(R.id.verify_otp);
        edtPh = findViewById(R.id.invitePhone);


        // below line is for getting instance
        // of our FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();
        // for  volly request queue
        mRequestQueue = Volley.newRequestQueue(this);

        // initializing variables for button and Edittext.

        // edit text find view
        edtOTP = findViewById(R.id.editTextNumber);
        verifyOTPBtn = findViewById(R.id.verify_otp);
        edtPh = findViewById(R.id.invitePhone);

        Intent intent = getIntent();
        final String mobile = intent.getStringExtra("mobile");
        sendVerificationCode(mobile);

        // setting onclick listner for generate OTP button.


        final String invitedPhone = edtPh.getText().toString();
        // for json object request



        // initializing on click listener
        // for verify otp button
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the OTP text field is empty or not.
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(VerifyPhoneActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    //verifyCode(edtOTP.getText().toString());

                    // for volly json object request

                    String url = "https://protochordate-falls.000webhostapp.com/restituo/register.php?phone="+mobile+"&invitedBy="+invitedPhone;
                    JsonObjectRequest
                            jsonObjectRequest
                            = new JsonObjectRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {


                                @Override
                                public void onResponse(JSONObject response)
                                {
                                    // textView.setText(response.getString("message"));
                                    try {
                                        Toast.makeText(VerifyPhoneActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    Toast.makeText(VerifyPhoneActivity.this, "Some error occurred!!", Toast.LENGTH_LONG).show();

                                }
                            });
                    mRequestQueue.add(jsonObjectRequest);

                    // end volly json object request

                    Toast.makeText(VerifyPhoneActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                    Intent intent1=new Intent(VerifyPhoneActivity.this,PrivacyPolicyActivity.class);
                    startActivity(intent1);
                }
            }
        });
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number, // first parameter is user's mobile number
                120, // second parameter is time limit for OTP
                // verification which is 60 seconds in our case.
                TimeUnit.SECONDS, // third parameter is for initializing units
                // for time period which is in seconds in our case.
                TaskExecutors.MAIN_THREAD, // this task will be excuted on Main thread.
                mCallBack // we are calling callback method when we recieve OTP for
                // auto verification of user.
        );
    }

    @Override
    public void codeRecieved(String code) {

    }


    // callback method is called on Phone auth provider.
 /*   private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.

                edtOTP.setText(code);
                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
        final String mobile = intent.getStringExtra("mobile");
        sendVerificationCode(mobile);

        // setting onclick listner for generate OTP button.


        final String invitedPhone = edtPh.getText().toString();
        // for json object request



        // initializing on click listener
        // for verify otp button

        verifyOTPBtn.setOnClickListener(view -> {
            String code = ((EditText) findViewById(R.id.editTextNumber)).getText().toString().trim();
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                fireBaseAuthUtil.verifyVerificationCode(code);
            }
            // validating if the OTP text field is empty or not.
            if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                // if the OTP text field is empty display
                // a message to user to enter OTP
                Toast.makeText(VerifyPhoneActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            } else {
                // if OTP field is not empty calling
                // method to verify the OTP.
                verifyCode(edtOTP.getText().toString());

                // for volly json object request

                String url = "https://protochordate-falls.000webhostapp.com/restituo/register.php?phone=" + mobile + "&invitedBy=" + invitedPhone;
                JsonObjectRequest
                        jsonObjectRequest
                        = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {


                            @Override
                            public void onResponse(JSONObject response) {
                                // textView.setText(response.getString("message"));
                                try {
                                    Toast.makeText(VerifyPhoneActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(VerifyPhoneActivity.this, "Some error occurred!!", Toast.LENGTH_LONG).show();

                            }
                        });
                mRequestQueue.add(jsonObjectRequest);

                // end volly json object request

                Toast.makeText(VerifyPhoneActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(VerifyPhoneActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent1);
            }
        });

    }



    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        edtOTP.setText(code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }


    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number, // first parameter is user's mobile number
                120, // second parameter is time limit for OTP
                // verification which is 60 seconds in our case.
                TimeUnit.SECONDS, // third parameter is for initializing units
                // for time period which is in seconds in our case.
                TaskExecutors.MAIN_THREAD, // this task will be excuted on Main thread.
                mCallBack // we are calling callback method when we recieve OTP for
                // auto verification of user.
        );
    }
*/

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
}
