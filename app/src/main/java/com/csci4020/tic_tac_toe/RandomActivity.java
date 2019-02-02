package com.csci4020.tic_tac_toe;
/*
CSCI 4020
Assignment 1
Han Kim
Brian Lake
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class RandomActivity extends Activity implements View.OnClickListener {


    private boolean gameOver = false;
    private int[][] gameBoardSquares = new int[3][3];

    // TODO: Current piece will be generated randomly

    public int[] gameIds = {
            R.id.imageButton00, R.id.imageButton01, R.id.imageButton02, R.id.imageButton03,
            R.id.imageButton04, R.id.imageButton05, R.id.imageButton06, R.id.imageButton07,
            R.id.imageButton08
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
    }

    @Override
    public void onClick(View v) {

    }

    public void setupButtonListener() {
        int[] ids = {
                R.id.btn_o, R.id.btn_x, R.id.imageButton00, R.id.imageButton01, R.id.imageButton02,
                R.id.imageButton03, R.id.imageButton04, R.id.imageButton05, R.id.imageButton06,
                R.id.imageButton07, R.id.imageButton08
        };
    }

}
