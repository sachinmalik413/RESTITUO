package com.samapps.restituo.ui.view.uinav.useragreement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AgreementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AgreementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}