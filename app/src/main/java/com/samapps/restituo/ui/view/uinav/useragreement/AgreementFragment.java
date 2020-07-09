package com.samapps.restituo.ui.view.uinav.useragreement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.samapps.restituo.R;

public class AgreementFragment extends Fragment {

    private AgreementViewModel agreementViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        agreementViewModel =
                ViewModelProviders.of(this).get(AgreementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_agreement, container, false);
        agreementViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView browser = view.findViewById(R.id.webview);
        browser.loadUrl("https://www.eulatemplate.com/live.php?token=O0s73kEcT5uhLFv2T9Px7xW7xAesc9OQ");
    }
}