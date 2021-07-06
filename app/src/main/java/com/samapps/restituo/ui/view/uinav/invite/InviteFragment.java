package com.samapps.restituo.ui.view.uinav.invite;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
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
        TextView number = view.findViewById(R.id.number);
        number.setText(FirebaseAuth.getInstance()
                .getCurrentUser()
                .getPhoneNumber());
        ((Button)view.findViewById(R.id.share)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
    }
}