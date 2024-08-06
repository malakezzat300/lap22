package com.example.lap22;

import static com.example.lap22.FragmentA.COUNTER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class ActivityB extends AppCompatActivity {

    String counter;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    FragmentB fragmentB;
    public static final String FRAGMENTB = "FragmentB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        if(savedInstanceState != null){
            counter = savedInstanceState.getString(COUNTER);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentManager = getSupportFragmentManager();
            fragmentB = (FragmentB) fragmentManager.findFragmentByTag(FRAGMENTB);
            fragmentB.receiveData(counter);
            finish();
        } else {
            Intent intent = getIntent();
            counter = intent.getStringExtra(COUNTER);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentB = new FragmentB();
            fragmentTransaction.add(R.id.fragmentContainerViewB, fragmentB, FRAGMENTB);
            fragmentTransaction.commit();


            fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
                    super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                    if (f == fragmentB) {
                        sendToB(counter);
                    }
                }
            }, false);

        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(COUNTER,counter);
    }

    public void sendToB(String data){

        fragmentB = (FragmentB) fragmentManager.findFragmentByTag(FRAGMENTB);
        fragmentB.receiveData(data);

    }

}