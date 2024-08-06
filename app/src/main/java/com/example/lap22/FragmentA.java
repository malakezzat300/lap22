package com.example.lap22;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentA extends Fragment {

    Button countButton;
    int counter = 0;
    public static final String COUNTER = "counter";

    Communicator communicator;

    public FragmentA() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        countButton = view.findViewById(R.id.countButton);
        communicator = (Communicator) getActivity();
        if(savedInstanceState != null){
            counter = savedInstanceState.getInt(COUNTER);
            communicator.response(""+(counter),false);
        }

        countButton.setOnClickListener(v -> {
            communicator.response(""+(++counter),true);
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTER,counter);
    }

    void setCounter(int data){
        counter = data;
    }
}