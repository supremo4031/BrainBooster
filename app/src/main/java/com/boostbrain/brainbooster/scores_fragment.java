package com.boostbrain.brainbooster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class scores_fragment extends Fragment {


    SharedPreferences normalPrefs, fastPrefs, quickPrefs;

    Communicator communicator;

    private TextView high_score, normal, fast, quick;

    private String[] data;

    private static final String PREFS_NAME = "SCORE_UPDATER";
    private static final String NORMAL_MODE = "NORMAL_MODE";
    private static final String FAST_MODE = "FAST_MODE";
    private static final String QUICK_MODE = "QUICK_MODE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scores, container, false);

        normal = (TextView) view.findViewById(R.id.normalMode_highScore);
        fast = (TextView) view.findViewById(R.id.fastMode_highScore);
        quick = (TextView) view.findViewById(R.id.quickMode_highScore);


        normalPrefs = getActivity().getSharedPreferences(NORMAL_MODE, 0);
        fastPrefs = getActivity().getSharedPreferences(FAST_MODE, 0);
        quickPrefs = getActivity().getSharedPreferences(QUICK_MODE, 0);

        if(normalPrefs.contains("attempted")) {
            int score = normalPrefs.getInt("attempted", 0);
            normal.setText("Normal Mode : " + score);
        }

        if(fastPrefs.contains("attempted")) {
            int score = fastPrefs.getInt("attempted", 0);
            fast.setText("Fast Mode : " + score);
        }

        if(quickPrefs.contains("attempted")) {
            int score = quickPrefs.getInt("attempted", 0);
            quick.setText("Quick Mode : " + score);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        communicator = (Communicator) getActivity();

        data = new String[3];

        data[0] = normal.getText().toString();
        data[1] = fast.getText().toString();
        data[2] = quick.getText().toString();

        communicator.shareData(data);

    }
}