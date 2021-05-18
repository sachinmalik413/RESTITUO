package com.samapps.restituo.ui.view.uinav.home;

public class CardData {

    public String mobileNo;
    public String textMail;
    public String timer;


    CardData(String mobileNo, String textMail) {
        this.mobileNo = mobileNo;
        this.textMail = textMail;

    }
    CardData(){}

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getTextMail() {
        return textMail;
    }

    public void setTextMail(String textMail) {
        this.textMail = textMail;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }
}
