package com.samapps.restituo.ui.view.uinav.invite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.samapps.restituo.R;
import com.samapps.restituo.ui.view.fragments.BottomSheetFragment;

public class InviteFragment extends Fragment {

    private com.samapps.restituo.ui.view.uinav.wallet.InviteViewModel inviteViewModel;
    private Button paytmButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inviteViewModel =
                ViewModelProviders.of(this).get(com.samapps.restituo.ui.view.uinav.wallet.InviteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_invite, container, false);
        inviteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}