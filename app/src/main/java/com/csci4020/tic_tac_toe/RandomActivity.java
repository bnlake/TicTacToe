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
import android.widget.Button;
import android.widget.TextView;

public class RandomActivity extends Activity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    // Todo: For now we start w/ player 1 but once we generate random code, the first player will be random
    private boolean player1Turn = true;

    // counts the # of rounds. If round gets more than 9, which is the max, we know it's a draw
    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        // loop through our rows and columns of buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;

                // set click listeners on the buttons
                int resId = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        // checks if button has already been used
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        // if it's player one's turn and they click a button, give the button a text of X or O
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        // loop through all the buttons and save the buttons text as a string, either X or O
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // checks each row to make sure each field has the same text, either X or O
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        // checks each column to make sure each field has the same text, either X or O
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

    }
}
