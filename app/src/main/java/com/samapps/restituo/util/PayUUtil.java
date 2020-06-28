package com.samapps.restituo.util;

import com.payumoney.core.PayUmoneySdkInitializer;
import com.samapps.restituo.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PayUUtil {

    String hashSequence = key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||salt;

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

    public void initializePayment(String amount){
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(amount)                          // Payment amount
                .setTxnId(txnId)                                             // Transaction ID
                .setPhone("+918802729349")                                           // User Phone number
                .setProductName(R.string.app_name)                   // Product Name or description
                .setFirstName("Restituo")                              // User First name
                .setEmail("sachinmalik413@gmail.com")                                            // User Email ID
                .setsUrl(appEnvironment.surl())                    // Success URL (surl)
                .setfUrl(appEnvironment.furl())                     //Failure URL (furl)
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                .setKey("t0fZh41Z")                        // Merchant key
                .setMerchantId("MZ31GphUoi");             // Merchant ID
    }


}
