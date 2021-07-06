package com.samapps.restituo.ui.view.uinav.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samapps.restituo.R;
import com.samapps.restituo.ui.view.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        List<CardData> data = fill_with_data();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        Recycler_View_Adapter adapter = new Recycler_View_Adapter(data, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;


    }


    public List<CardData> fill_with_data() {

        List<CardData> data = new ArrayList<>();

        data.add(new CardData("8080097426", "text/Email"));
        data.add(new CardData("1231231233", "text/Email"));
        data.add(new CardData("4444445556", "text/Email"));
        data.add(new CardData("7776668884", "text/Email"));
        data.add(new CardData("1122334455", "text/Email"));
        data.add(new CardData("6655773388", "text/Email"));

        return data;
    }
}