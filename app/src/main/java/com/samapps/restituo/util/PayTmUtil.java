package com.samapps.restituo.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.samapps.restituo.R;
import com.samapps.restituo.VollyAndFirebaseConfig.ApiActivity;
import com.samapps.restituo.retroutil.ServiceWrapper;
import com.samapps.restituo.retroutil.Token_Res;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class PayTmUtil {

    private String TAG = "PayTmUtil";
    private String midString ="LvvmzN49988179009102", orderIdString="", txnTokenString="";
    private Integer ActivityRequestCode = 2;
    private Activity activity;
    //for volly library
    private RequestQueue mRequestQueue;


    // variable to hold context
    private Context context;

//save the context recievied via constructor in a local variable

    public PayTmUtil(Context context){
        this.context=context;
    }

    public PayTmUtil() {

    }

    public static PayTmUtil getInstance(){
        return new PayTmUtil();
    }

    public void getToken(Activity activity, final String amount){
        this.activity = activity;
        Log.e(TAG, " get token start");
        getOrderID();
        //progressBar.setVisibility(View.VISIBLE);
        ServiceWrapper serviceWrapper = new ServiceWrapper(null);
        Call<Token_Res> call = serviceWrapper.getTokenCall("12345", midString, orderIdString, amount);
        call.enqueue(new Callback<Token_Res>() {
            @Override
            public void onResponse(Call<Token_Res> call, Response<Token_Res> response) {
                Log.e(TAG, " respo "+ response.isSuccessful() );
                //progressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful() && response.body()!=null){
                        if (response.body().getBody().getTxnToken()!="") {
                            Log.e(TAG, " transaction token : "+response.body().getBody().getTxnToken());
                            startPaytmPayment(response.body().getBody().getTxnToken(),amount);
                        }else {
                            Log.e(TAG, " Token status false");
                        }
                    }
                }catch (Exception e){
                    Log.e(TAG, " error in Token Res "+e.toString());
                }
            }

            @Override
            public void onFailure(Call<Token_Res> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e(TAG, " response error "+t.toString());
            }
        });

    }

    private void getOrderID() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min =1000, max= 9999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
        int randomNum = rand.nextInt((max - min) + 1) + min;
        orderIdString =  date+String.valueOf(randomNum);
    }

    private void startPaytmPayment (final String token, final String amount){

        txnTokenString = token;
        // for test mode use it
        String host = "https://securegw-stage.paytm.in/";
        // for production mode use it
        //String host = "https://securegw.paytm.in/";
        String orderDetails = "MID: " + midString + ", OrderId: " + orderIdString + ", TxnToken: " + txnTokenString
                + ", Amount: " + amount;
        Log.e(TAG, "order details "+ orderDetails);

        String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+orderIdString;
        Log.e(TAG, " callback URL "+callBackUrl);
        PaytmOrder paytmOrder = new PaytmOrder(orderIdString, midString, txnTokenString, amount, callBackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){
            @Override
            public void onTransactionResponse(Bundle bundle) {
                Log.e(TAG, "Response (onTransactionResponse) : "+bundle.toString());
                mRequestQueue = Volley.newRequestQueue(context);
                // volly implementation

                String url = "https://protochordate-falls.000webhostapp.com/restituo/wallet.php?user=jyoti&amount="+amount+"&orderid="+orderIdString;
                JsonObjectRequest
                        jsonObjectRequest
                        = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new com.android.volley.Response.Listener<JSONObject>() {


                            @Override
                            public void onResponse(JSONObject response)
                            {
                                try {
                                    //textView.setText(response.getString("message"));
                                    Log.e(TAG,response.getString("message"));
                                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(context, "Some error occurred!!", Toast.LENGTH_LONG).show();

                            }
                        });
                mRequestQueue.add(jsonObjectRequest);

            }

            @Override
            public void networkNotAvailable() {
                Log.e(TAG, "network not available ");
            }

            @Override
            public void onErrorProceed(String s) {
                Log.e(TAG, " onErrorProcess "+s.toString());
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e(TAG, "Clientauth "+s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e(TAG, " UI error "+s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e(TAG, " error loading web "+s+"--"+s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e(TAG, "backPress ");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e(TAG, " transaction cancel "+s);
            }
        });

        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(activity, ActivityRequestCode);

    }

}