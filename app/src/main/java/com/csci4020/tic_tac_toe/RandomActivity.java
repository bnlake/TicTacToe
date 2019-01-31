package com.csci4020.tic_tac_toe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class RandomActivity extends Activity implements View.OnClickListener {

    private boolean gameOver = false;
    private int[][] gameBoardSquares = new int[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_random);
    }

    @Override
    public void onClick(View v) {

    }
}
