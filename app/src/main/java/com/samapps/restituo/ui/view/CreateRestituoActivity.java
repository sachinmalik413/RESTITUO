package com.samapps.restituo.ui.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.samapps.restituo.R;
import com.samapps.restituo.ui.view.fragments.CommunicationFragment;
import com.samapps.restituo.ui.view.fragments.PackageFragment;
import com.shuhart.stepview.StepView;

public class CreateRestituoActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private StepView step_view;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restituo);
        step_view = findViewById(R.id.step_view);
        addFragment(1);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    public void addFragment(int screen){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.your_placeholder, new PackageFragment());
        switch (screen){
            case 1: ft.replace(R.id.your_placeholder, new PackageFragment());
                    ft.addToBackStack("1");
                    step_view.go(0,true);
                    break;

            case 2: ft.replace(R.id.your_placeholder, new CommunicationFragment());
                    ft.addToBackStack("2");
                    step_view.go(1,true);
                    break;

            case 3: ft.replace(R.id.your_placeholder, new PackageFragment());
                    ft.addToBackStack("3");
                    step_view.go(2,true);
                    break;
        }
        ft.commit();
    }

    @Override
    public void onBackStackChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
            Log.e("create restituo","tag "+fragmentTag);
            currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
            switch (fragmentTag){
                case "1": step_view.go(0,true);
                    break;
                case "2": step_view.go(1,true);
                    break;
                case "3": step_view.go(2,true);
                    break;
            }
        } else {
            Log.e("create restituo","no tag ");
            currentFragment = new PackageFragment();
            finish();
        }

    }
}