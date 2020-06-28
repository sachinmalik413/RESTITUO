package com.samapps.restituo.ui.view.fragments;

import android.content.ClipData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PackageViewModel extends ViewModel {
    private final MutableLiveData<Integer> minutes = new MutableLiveData<Integer>();

    public MutableLiveData<Integer> getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes.setValue(minutes);
    }
}