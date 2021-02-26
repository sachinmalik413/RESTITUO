package com.samapps.restituo.ui.view.uinav.home;

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
        //View root = inflater.inflate(R.layout.fragment_home, container, false);
       // final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        return root;


    }


    public List<CardData> fill_with_data() {

        List<CardData> data = new ArrayList<>();

//        data.add(new CardData("Batman vs Superman", "Following the destruction of Metropolis, Batman embarks on a personal vendetta against Superman "));
//        data.add(new CardData("X-Men: Apocalypse", "X-Men: Apocalypse is an upcoming American superhero film based on the X-Men characters that appear in Marvel Comics "));
//        data.add(new CardData("Captain America: Civil War", "A feud between Captain America and Iron Man leaves the Avengers in turmoil.  "));
//        data.add(new CardData("Kung Fu Panda 3", "After reuniting with his long-lost father, Po  must train a village of pandas"));
//        data.add(new CardData("Warcraft", "Fleeing their dying home to colonize another, fearsome orc warriors invade the peaceful realm of Azeroth. "));
//        data.add(new CardData("Alice in Wonderland", "Alice in Wonderland: Through the Looking Glass "));

        data.add(new CardData("ABC", "XYZ "));
        data.add(new CardData("ABC", "XYZ "));
        data.add(new CardData("ABC", "XYZ"));

        return data;
    }
}