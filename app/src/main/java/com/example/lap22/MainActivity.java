package com.example.lap22;

import static com.example.lap22.ActivityB.FRAGMENTB;
import static com.example.lap22.FragmentA.COUNTER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Communicator{
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FragmentB fragmentB;

    String counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();



        if(savedInstanceState != null){
            fragmentB = (FragmentB) fragmentManager.findFragmentByTag(FRAGMENTB);
            counter = savedInstanceState.getString(COUNTER);
            Log.i("counter test", "onCreate: savedInstanceState " + counter);

        } else {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentB = new FragmentB();
            fragmentTransaction.add(R.id.fragmentContainerViewAB, fragmentB, FRAGMENTB);
            fragmentTransaction.commit();
        }
        Intent intent = getIntent();
        if(intent != null){
            counter = intent.getStringExtra(COUNTER);
            fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
                    super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                    if (f == fragmentB) {
                        Log.i("counter test", "onCreate: intent " + counter);
                        fragmentB.receiveData(counter);
                    } else if(f instanceof FragmentA){
                        if(counter != null) {
                            ((FragmentA) f).setCounter(Integer.parseInt(counter));
                        }
                    }
                }

            }, false);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("counter test", "onSaveInstanceState: " + counter);
        outState.putString(COUNTER,counter);
    }

    @Override
    public void response(String data,boolean isClicked) {
        counter = data;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
                    super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                    if (f == fragmentB) {
                        fragmentB.receiveData(counter);
                    }
                }
            }, false);
            fragmentB.receiveData(counter);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && isClicked){
            Intent intent = new Intent(getApplicationContext(),ActivityB.class);
            intent.putExtra(COUNTER,counter);
            startActivity(intent);
        }
    }
}