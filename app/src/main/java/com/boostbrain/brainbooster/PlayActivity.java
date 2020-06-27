package com.boostbrain.brainbooster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private int timer = 0;
    private int attempetd = 0;
    private int total_questions = 0;
    private static int correct_index;

    private TextView countTimer, score, question, modeText;
    private Button first, second, third, fourth;
    private Button[] ans;
    private Button exit;

    private int mode;

    Handler time, handler;
    Runnable runTime, myRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Intent intent = getIntent();
        mode = intent.getIntExtra("MODE", 0);

        Toast.makeText(getApplicationContext(), "MODE : " + mode, Toast.LENGTH_SHORT).show();

        countTimer = (TextView) findViewById(R.id.timer);
        score = (TextView) findViewById(R.id.score);
        question = (TextView) findViewById(R.id.question);
        modeText = (TextView) findViewById(R.id.mode_name);

        score.setText("Score : 0 / 0");


        first = (Button) findViewById(R.id.first_ans);
        second = (Button) findViewById(R.id.second_ans);
        third = (Button) findViewById(R.id.third_ans);
        fourth = (Button) findViewById(R.id.fourth_ans);

        ans = new Button[]{first, second, third, fourth};

        exit = (Button) findViewById(R.id.exit_button);

        modeChanger();

        for(int i = 0; i < 4; i++) ans[i].setOnClickListener(choose);
        makeLayout();


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimer();

            }
        });




    }

    private void modeChanger() {
        switch (mode) {
            case 1 :
                modeText.setText("Normal Mode");
                startTimer();
                break;
            case 2 :
                modeText.setText("Fast Mode");
                fastTimer();
                break;
            case 3 :
                modeText.setText("Quick Mode");
                countdownTimer();
                break;
            default:
        }
    }

    public void startTimer() {
        time = new Handler();
        final int[] timeCount = {0};
        final int[] elapsedTime = {0};
        runTime = new Runnable() {
            @Override
            public void run() {
                countTimer.setText("Time : " + timeCount[0]);
                timer = timeCount[0];
                timeCount[0]++;
                time.postDelayed(this, 1000);

            }
        };
        time.post(runTime);
    }

    public void countdownTimer() {
        time = new Handler();
        final int[] timePassed = {60};
        final int[] nextTime = {60};

        final Handler check = new Handler();
        runTime = new Runnable() {
            @Override
            public void run() {
                if(timePassed [0] >= 0) {
                    countTimer.setText("Time : " + timePassed[0]);
                    timePassed[0]--;
                    nextTime[0] = timePassed[0];
                    timer = 60 - timePassed[0];
                    time.postDelayed(this, 1000);
                }
                else
                    endTimer();
            }
        };

        time.post(runTime);

    }

    public void fastTimer() {
        time = new Handler();

        runTime = new Runnable() {
            @Override
            public void run() {
                makeLayout();
                handler = new Handler();
                final int[] timeCount = {5};
                myRunner = new Runnable() {
                    @Override
                    public void run() {
                        if(timeCount[0] >= 0) {
                            countTimer.setText("Time : " + timeCount[0]);
                            timeCount[0]--;
                            if(timeCount[0] >= 0)
                                timer++;
                            handler.postDelayed(myRunner, 1000);
                        }
                        else
                            handler.removeCallbacks(runTime);
                    }
                };
                handler.post(myRunner);
                time.postDelayed(runTime, 6000);
            }
        };
        time.post(runTime);
    }

    public void makeLayout() {
        total_questions++;
        Random random = new Random();
        int value = random.nextInt(3);
        switch(value) {
            case 0 :
                addNumber();
                break;
            case 1 :
                mulNumber();
                break;
            case 2 :
                subNumber();
                break;
            default:
                addNumber();
        }

    }

    private void subNumber() {
        Random random = new Random();
        int value = random.nextInt(4);

        correct_index = value;

        int a = 100 + random.nextInt(787);
        int b = 12 + random.nextInt(787);

        if(b > a) {
            int temp = b;
            b = a;
            a = temp;
        }

        question.setText(a + " - " + b + " = ? ");

        int pre = -1;

        for(int i = 0; i < 4; i++) {
            if(value == i) {
                int correct = a - b;
                ans[i].setText(Integer.toString(correct));
            }
            else {
                int incorrect = a - b + random.nextInt(30);
                while(incorrect == (a - b) || incorrect == pre) incorrect = a - b + random.nextInt(30);
                ans[i].setText(Integer.toString(incorrect));
                pre = incorrect;
            }
        }

    }

    private void mulNumber() {
        Random random = new Random();
        int value = random.nextInt(4);

        correct_index = value;

        int a = 9 + random.nextInt(20);
        int b = a + random.nextInt(30);

        question.setText(a + " * " + b + " = ? ");

        int pre = -1;

        for(int i = 0; i < 4; i++) {
            if(value == i) {
                int correct = a * b;
                ans[i].setText(Integer.toString(correct));
            }
            else {
                int incorrect = a * b + random.nextInt(100);
                while(incorrect == (a * b) || incorrect == pre) incorrect = a * b + random.nextInt(100);
                ans[i].setText(Integer.toString(incorrect));
                pre = incorrect;
            }
        }
    }

    private void addNumber() {
        Random random = new Random();
        int value = random.nextInt(4);

        correct_index = value;

        int a = 12 + random.nextInt(87);
        int b = 12 + random.nextInt(87);

        question.setText(a + " + " + b + " = ? ");

        int previous = 0;

        for(int i = 0; i < 4; i++) {
            if(value == i) {
                int correct = a + b;
                ans[i].setText(Integer.toString(correct));
            }
            else {
                int p = (random.nextInt() % 2 == 1 ? -1 : 1);
                int incorrect = a + b + random.nextInt(9) * 10 * p;
                while(incorrect == (a + b) || incorrect < 0 || incorrect == previous) incorrect = a + b + random.nextInt(9) * 10 * p;
                previous = incorrect;
                ans[i].setText(Integer.toString(incorrect));
            }
        }

    }

    View.OnClickListener choose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String idx = v.getTag().toString();
            int actual_idx = Integer.parseInt(idx);
            if(correct_index == actual_idx) {
                callToast("Correct");

                ((Button) v).setTextColor(getResources().getColor(R.color.COLOR_GREEN));

                attempetd++;
                score.setText("Score : " + attempetd + " / " + total_questions);
            }

            else {
                callToast("Incorrect");

                ((Button) v).setTextColor(getResources().getColor(R.color.COLOR_RED));

                score.setText("Score : " + attempetd + " / " + total_questions);
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 4; i++)
                        ans[i].setTextColor(getResources().getColor(R.color.COLOR_WHITE));
                    if(mode != 2)
                        makeLayout();
                    else {
                        handler.removeCallbacks(myRunner);
                        time.removeCallbacks(runTime);
                        time.post(runTime);
                    }
                }
            }, 1000);
        }
    };

    public void endTimer() {

        time.removeCallbacks(runTime);
        if(mode == 2)
            handler.removeCallbacks(myRunner);

        total_questions--;

        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
        builder.setCancelable(true)
                .setTitle("Finished")
                .setMessage("Your Score Is : " + attempetd + " / " + total_questions + "\n"
                        + "Time Elapsed : " + timer + " seconds.");
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent intent = new Intent();
                intent.putExtra("ATTEMPTED", attempetd);
                Log.i("checkk", "Attempted value is " + attempetd);
                intent.putExtra("QUESTIONS", total_questions);
                intent.putExtra("TIME", timer);
                intent.putExtra("MODE", mode);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent intent = new Intent();
                intent.putExtra("ATTEMPTED", attempetd);
                Log.i("checkk", "Attempted value is " + attempetd);
                intent.putExtra("QUESTIONS", total_questions);
                intent.putExtra("TIME", timer);
                intent.putExtra("MODE", mode);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    public void callToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}