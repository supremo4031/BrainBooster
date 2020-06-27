package com.boostbrain.brainbooster;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class modes_fragment extends Fragment {


    RadioGroup radioGroup;
    RadioButton normal, quick, fast;

    Communicator communicator;

    private int MODE;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modes, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupID);

        normal = (RadioButton) view.findViewById(R.id.normal_mode);
        quick = (RadioButton) view.findViewById(R.id.quick_mode);
        fast = (RadioButton) view.findViewById(R.id.fast_mode);

//        radioGroup.check(R.id.quick_mode);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.normal_mode:
                        communicator.passData(1);
                        break;
                    case R.id.fast_mode:
                        communicator.passData(2);
                        break;
                    case R.id.quick_mode:
                        communicator.passData(3);
                        break;
                }

            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator = (Communicator) getActivity();

        MODE = communicator.getData();
        setChecker(MODE);
    }

    private void setChecker(int value) {
        Log.i("checkk", "Mode is " + value);
        switch (value) {
            case 1 :
                radioGroup.check(R.id.normal_mode);
                break;
            case 2 :
                radioGroup.check(R.id.fast_mode);
                break;
            case 3 :
                radioGroup.check(R.id.quick_mode);
                break;
            default:
                radioGroup.check(R.id.normal_mode);
        }
    }
}