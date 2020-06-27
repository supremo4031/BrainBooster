package com.boostbrain.brainbooster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ItemClickListener, Communicator{

    Fragment fragment;
    BottomNavigationView bottomNavigationView;

    private int MAIN_MODE = 1;

    private String[] high_scores;

    SharedPreferences normalPrefs, fastPrefs, quickPrefs;
    private static final String NORMAL_MODE = "NORMAL_MODE";
    private static final String FAST_MODE = "FAST_MODE";
    private static final String QUICK_MODE = "QUICK_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        Log.i("checkk", "MainActivity");

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new play_fragment()).commit();

        normalPrefs = getSharedPreferences(NORMAL_MODE, 0);
        fastPrefs = getSharedPreferences(FAST_MODE, 0);
        quickPrefs = getSharedPreferences(QUICK_MODE, 0);

        high_scores = new String[3];

        if(normalPrefs.contains("attempted")) {
            int score = normalPrefs.getInt("attempted", 0);
            high_scores[0] = ("Normal Mode : " + score);
        } else {
            high_scores[0] = ("Normal Mode : 0");
        }

        if(fastPrefs.contains("attempted")) {
            int score = fastPrefs.getInt("attempted", 0);
            high_scores[1] = ("Fast Mode : " + score);
        }
        else {
            high_scores[1] = ("Fast Mode : 0");
        }

        if(quickPrefs.contains("attempted")) {
            int score = quickPrefs.getInt("attempted", 0);
            high_scores[2] = ("Quick Mode : " + score);
        } else {
            high_scores[2] = ("Quick Mode : 0");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.play_fragment :
                    fragment = new play_fragment();
                    break;
                case R.id.modes_fragment:
                    fragment = new modes_fragment();
                    break;
                case R.id.scores_fragment:
                    fragment = new scores_fragment();
                    break;
                default:
                    fragment =  new play_fragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();

            return true;
        }
    };

    @Override
    public void onItemClick() {

    }

    @Override
    public void passData(int data) {
        MAIN_MODE = data;
    }

    @Override
    public int getData() {
        return MAIN_MODE;
    }

    @Override
    public void shareData(String[] data) {
        high_scores = data;
    }

    @Override
    public String[] getShareData() {
        return high_scores;
    }
}