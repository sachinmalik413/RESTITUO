package com.samapps.restituo.ui.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.Manifest;
import android.app.Activity;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.samapps.restituo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private PopupWindow mPopupWindow;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        mContext = getApplicationContext();

        // Get the activity
        mActivity = PrivacyPolicyActivity.this;

        WebView browser = findViewById(R.id.webview);
        browser.setWebViewClient(new WebViewClient());
         progressBar = findViewById(R.id.progressBar);
        browser.loadUrl("https://www.eulatemplate.com/live.php?token=O0s73kEcT5uhLFv2T9Px7xW7xAesc9OQ");


        // for next button code

        Button next = findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(PrivacyPolicyActivity.this,UserProfileActivity.class);
                startActivity(intent1);
            }
        });

        final Button agree=findViewById(R.id.btnAgree);

        //for popup window

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Inflate the custom layout/view
        final View customView = inflater.inflate(R.layout.signature_pop_up_layout,null);


        // Initialize a new instance of popup window
        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Intent intent1=new Intent(PrivacyPolicyActivity.this,SignaturePadActivity.class);

                ActivityCompat.finishAffinity(PrivacyPolicyActivity.this);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);

                SignaturePopUpClass popUpClass = new SignaturePopUpClass();
                popUpClass.showPopupWindow(view);

                */

                // Initialize a new instance of LayoutInflater service


                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }


                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.btn_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                // mSignaturePad

                mSignaturePad = (SignaturePad) customView.findViewById(R.id.signature_pad);
                mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
                    @Override
                    public void onStartSigning() {
                      //  Toast.makeText(PrivacyPolicyActivity.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSigned() {
                        mSaveButton.setEnabled(true);
                        mClearButton.setEnabled(true);
                    }

                    @Override
                    public void onClear() {
                        mSaveButton.setEnabled(false);
                        mClearButton.setEnabled(false);
                    }
                });

                //mSavebutton and mclearButton code here

                mClearButton = (Button) customView.findViewById(R.id.clear_button);
                mSaveButton = (Button) customView.findViewById(R.id.save_button);


               // mSignaturePad.setBackgroundColor(Color.rgb(253,239,239));
                mClearButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSignaturePad.clear();
                    }
                });

                mSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(checkContactPermission()) {
                            Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                            if (addJpgSignatureToGallery(signatureBitmap)) {
                                Toast.makeText(PrivacyPolicyActivity.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                               // Intent intent1=new Intent(PrivacyPolicyActivity.this,DashboardActivity.class);

                                // ActivityCompat.finishAffinity(PrivacyPolicyActivity.this);
                                //intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                              //  startActivity(intent1);

                            } else {
                                Toast.makeText(PrivacyPolicyActivity.this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
                            }
                            if (addSvgSignatureToGallery(mSignaturePad.getSignatureSvg())) {
                                Toast.makeText(PrivacyPolicyActivity.this, "SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PrivacyPolicyActivity.this, "Unable to store the SVG signature", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            requestContactPermission();
                        }
                    }
                });

                // ends here



                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(view, Gravity.CENTER,0,0);


            }


        });

        if(mPopupWindow.isShowing())
        {
            agree.setEnabled(false);
            agree.setFocusable(false);
        }
        else if(!mPopupWindow.isShowing())
        {
            agree.setEnabled(true);
            agree.setFocusable(true);
        }



    }


    // here

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    //here


    @Override
    protected void onSaveInstanceState(Bundle oldInstanceState) {
        super.onSaveInstanceState(oldInstanceState);
        oldInstanceState.clear();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PrivacyPolicyActivity.this, "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        PrivacyPolicyActivity.this.sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

    }

    private boolean checkContactPermission(){
        //check if contact permission was granted or not
        boolean result = ContextCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED
        );

        return result;  //true if permission granted, false if not
    }

    private void requestContactPermission(){
        //permissions to request
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        ActivityCompat.requestPermissions(this, permission, 1);
    }

}