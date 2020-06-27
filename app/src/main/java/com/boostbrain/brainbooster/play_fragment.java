package com.boostbrain.brainbooster;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class play_fragment extends Fragment {

    private Button play, share, tutorial;

    private int requestCODE = 1;

    Communicator communicator;

    private int MODE = 1;

    private String[] high_scores;

    SharedPreferences normalPrefs, fastPrefs, quickPrefs;
    private static final String NORMAL_MODE = "NORMAL_MODE";
    private static final String FAST_MODE = "FAST_MODE";
    private static final String QUICK_MODE = "QUICK_MODE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        play = (Button) view.findViewById(R.id.play_button);
        share = (Button) view.findViewById(R.id.share_button);
        tutorial = (Button) view.findViewById(R.id.tutorial_button);

        Log.i("checkk", "OnCreateView");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShare();
            }
        });
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTutorial();
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("checkk", "onActivityCreated");

        communicator = (Communicator) getActivity();

        MODE = communicator.getData();
        high_scores = communicator.getShareData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK && requestCode == 1) {

            int score = data.getIntExtra("ATTEMPTED", 111);
            int mode = data.getIntExtra("MODE", 0);

            Log.i("checkk", "Intent Returned Successfully with requestCode " + score);

            Toast.makeText(getContext(), "Score is " + score, Toast.LENGTH_SHORT).show();

            switch (mode) {
                case 1:
                    normalPrefs = getActivity().getSharedPreferences(NORMAL_MODE, 0);
                    SharedPreferences.Editor normalEditor = normalPrefs.edit();

                    if(normalPrefs.contains("attempted")) {
                        int old_score = normalPrefs.getInt("attempted", 0);
                        if(old_score < score) {
                            normalEditor.putInt("attempted", score);
                            normalEditor.commit();
                        }
                    } else {
                        normalEditor.putInt("attempted", score);
                        normalEditor.commit();
                    }

                    break;
                case 2:
                    fastPrefs = getActivity().getSharedPreferences(FAST_MODE, 0);
                    SharedPreferences.Editor fastEditor = fastPrefs.edit();

                    if(fastPrefs.contains("attempted")) {
                        int old_score = fastPrefs.getInt("attempted", 0);
                        if(old_score < score) {
                            fastEditor.putInt("attempted", score);
                            fastEditor.commit();
                        }
                    } else {
                        fastEditor.putInt("attempted", score);
                        fastEditor.commit();
                    }

                    break;
                case 3:
                    quickPrefs = getActivity().getSharedPreferences(QUICK_MODE, 0);
                    SharedPreferences.Editor quickEditor = quickPrefs.edit();

                    if(quickPrefs.contains("attempted")) {
                        int old_score = quickPrefs.getInt("attempted", 0);
                        if(old_score < score) {
                            quickEditor.putInt("attempted", score);
                            quickEditor.commit();
                        }
                    } else {
                        quickEditor.putInt("attempted", score);
                        quickEditor.commit();
                    }

                    break;
            }
        }

    }

    private void startGame() {
        Intent intent = new Intent(getContext(), PlayActivity.class);
        intent.putExtra("MODE", MODE);
        startActivityForResult(intent, 1);
    }

    private void startShare() {
        StringBuilder dataString = new StringBuilder();
        for(String s : high_scores) {
            dataString.append(s + "\n");
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
//        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_SUBJECT, "High Scores in Brain Booster");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"whomyouwanttosend@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, dataString.toString());

        try{
            startActivity(Intent.createChooser(intent, "Send Mail"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "Please Install any Mail app", Toast.LENGTH_LONG).show();
        }
    }

    private void startTutorial() {
        Intent intent = new Intent(getContext(), tutorialActivity.class);
        startActivity(intent);
    }
}