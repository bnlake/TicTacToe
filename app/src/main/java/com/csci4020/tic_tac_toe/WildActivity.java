package com.csci4020.tic_tac_toe;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class WildActivity extends Activity implements View.OnClickListener{
    private int currentPiece = R.drawable.letter_x, currentPlayer = 0, piecesPlaced = 0;
    private boolean[] gameBoardFlags = new boolean[36];
    private boolean gameOver = false;
    private int[][] gameBoardSquares = new int[6][6];
    private int Col2DIndex = -1;
    private int Row2DIndex = -1;

    public int[] gameIds = {
            R.id.imageButton00, R.id.imageButton01, R.id.imageButton02, R.id.imageButton03,
            R.id.imageButton04, R.id.imageButton05, R.id.imageButton10, R.id.imageButton11,
            R.id.imageButton12, R.id.imageButton13, R.id.imageButton14, R.id.imageButton15,
            R.id.imageButton20, R.id.imageButton21, R.id.imageButton22, R.id.imageButton23,
            R.id.imageButton24, R.id.imageButton25, R.id.imageButton30, R.id.imageButton31,
            R.id.imageButton32, R.id.imageButton33, R.id.imageButton34, R.id.imageButton35,
            R.id.imageButton40, R.id.imageButton41, R.id.imageButton42, R.id.imageButton43,
            R.id.imageButton44, R.id.imageButton45, R.id.imageButton50, R.id.imageButton51,
            R.id.imageButton52, R.id.imageButton53, R.id.imageButton54, R.id.imageButton55
    };

    public int[][] game2DArrayIds = {
            {R.id.imageButton00, R.id.imageButton01, R.id.imageButton02, R.id.imageButton03, R.id.imageButton04, R.id.imageButton05},
            {R.id.imageButton10, R.id.imageButton11, R.id.imageButton12, R.id.imageButton13, R.id.imageButton14, R.id.imageButton15},
            {R.id.imageButton20, R.id.imageButton21, R.id.imageButton22, R.id.imageButton23, R.id.imageButton24, R.id.imageButton25},
            {R.id.imageButton30, R.id.imageButton31, R.id.imageButton32, R.id.imageButton33, R.id.imageButton34, R.id.imageButton35},
            {R.id.imageButton40, R.id.imageButton41, R.id.imageButton42, R.id.imageButton43, R.id.imageButton44, R.id.imageButton45},
            {R.id.imageButton50, R.id.imageButton51, R.id.imageButton52, R.id.imageButton53, R.id.imageButton54, R.id.imageButton55}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild);

        // set up all the listeners for the buttons
        setupButtonListener();

        for(int i = 0; i < gameBoardFlags.length; i++){
            gameBoardFlags[i] = false;
        }

        for(int i = 0; i < game2DArrayIds.length; i++){ // col
            for(int j = 0; j < game2DArrayIds[i].length; j++){ //row
                gameBoardSquares[i][j] = R.drawable.white_square;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_x:
                currentPiece = R.drawable.letter_x;
                ((ImageButton) findViewById(R.id.btn_x)).setBackgroundResource(R.drawable.white_button_black_border);
                ((ImageButton) findViewById(R.id.btn_o)).setBackgroundResource(R.drawable.white_button_no_black_border);
                break;
            case R.id.btn_o:
                currentPiece = R.drawable.letter_o;
                ((ImageButton) findViewById(R.id.btn_x)).setBackgroundResource(R.drawable.white_button_no_black_border);
                ((ImageButton) findViewById(R.id.btn_o)).setBackgroundResource(R.drawable.white_button_black_border);
                break;
            case R.id.imageButton00: case R.id.imageButton01: case R.id.imageButton02:
            case R.id.imageButton03: case R.id.imageButton04: case R.id.imageButton05:
            case R.id.imageButton10: case R.id.imageButton11: case R.id.imageButton12:
            case R.id.imageButton13: case R.id.imageButton14: case R.id.imageButton15:
            case R.id.imageButton20: case R.id.imageButton21: case R.id.imageButton22:
            case R.id.imageButton23: case R.id.imageButton24: case R.id.imageButton25:
            case R.id.imageButton30: case R.id.imageButton31: case R.id.imageButton32:
            case R.id.imageButton33: case R.id.imageButton34: case R.id.imageButton35:
            case R.id.imageButton40: case R.id.imageButton41: case R.id.imageButton42:
            case R.id.imageButton43: case R.id.imageButton44: case R.id.imageButton45:
            case R.id.imageButton50: case R.id.imageButton51: case R.id.imageButton52:
            case R.id.imageButton53: case R.id.imageButton54: case R.id.imageButton55:
                placeGamePiece(view.getId());
                break;
            default:
                Toast.makeText(getApplicationContext(),"Invalid view Id ",Toast.LENGTH_SHORT).show();
        }
    }

    public void setupButtonListener(){
        int[] ids = {
                R.id.btn_x, R.id.btn_o, R.id.imageButton00, R.id.imageButton01, R.id.imageButton02,
                R.id.imageButton03, R.id.imageButton04, R.id.imageButton05, R.id.imageButton10,
                R.id.imageButton11, R.id.imageButton12, R.id.imageButton13, R.id.imageButton14,
                R.id.imageButton15, R.id.imageButton20, R.id.imageButton21, R.id.imageButton22,
                R.id.imageButton23, R.id.imageButton24, R.id.imageButton25, R.id.imageButton30,
                R.id.imageButton31, R.id.imageButton32, R.id.imageButton33, R.id.imageButton34,
                R.id.imageButton35, R.id.imageButton40, R.id.imageButton41, R.id.imageButton42,
                R.id.imageButton43, R.id.imageButton44, R.id.imageButton45, R.id.imageButton50,
                R.id.imageButton51, R.id.imageButton52, R.id.imageButton53, R.id.imageButton54,
                R.id.imageButton55
        };
        for(int i = 0; i < ids.length; i++){
            ((ImageButton) findViewById(ids[i])).setOnClickListener(this);
        }
    }

    public void placeGamePiece(int id){
        int imageButtonIndex = findIndexOfId(id);

        if(imageButtonIndex != -1){
            gameBoardFlags[imageButtonIndex] = true;
        }


        // get the col and row
        get2DIndex(id);

        // don't continue if there's no white square
        if(gameBoardSquares[Col2DIndex][Row2DIndex] != R.drawable.white_square) {
            return;
        }

        // don't continue if game is over
        if(gameOver){
            Toast.makeText(getApplicationContext(),"Press the back button to start another game",Toast.LENGTH_SHORT).show();
            return;
        }

        ((ImageButton) findViewById(id)).setImageResource(currentPiece);

        // the current piece's column and row
        gameBoardSquares[Col2DIndex][Row2DIndex] = currentPiece;

        int consecutivePieces = 1;
        int pastImage = -1;

        // iterate through each row of the 2D column index
        for(int i = 0; i < gameBoardSquares.length; i++){
            if (gameBoardSquares[Col2DIndex][i] == pastImage && gameBoardSquares[Col2DIndex][i] == currentPiece){
                consecutivePieces++;
            } else {
                consecutivePieces = 1;
            }

            pastImage = gameBoardSquares[Col2DIndex][i];

            if (consecutivePieces == 5){
                // game over
                endGame("Order");
            }
        }

        consecutivePieces = 1;
        pastImage = -1;

        // iterate through each row of the 2D row index
        for(int i = 0; i < gameBoardSquares.length; i++){
            if (gameBoardSquares[i][Row2DIndex] == pastImage && gameBoardSquares[i][Row2DIndex] == currentPiece){
                consecutivePieces++;
            } else {
                consecutivePieces = 1;
            }

            pastImage = gameBoardSquares[i][Row2DIndex];

            if (consecutivePieces == 5){
                // we have a winner
                endGame("Order");
            }
        }


        // Diagonal
        consecutivePieces = 1;
        pastImage = -1;

        // starts top left
        if ( (Col2DIndex - Row2DIndex) == 1){
            // Top
            for (int i = 0; i < game2DArrayIds.length-1; i++){
                if (gameBoardSquares[i+1][i] == pastImage && gameBoardSquares[i+1][i] == currentPiece){
                    consecutivePieces++;
                } else {
                    consecutivePieces = 1;
                }
                pastImage = gameBoardSquares[i+1][i];
                if (consecutivePieces == 5){
                    // game over
                    endGame("Order");
                }
            }
        } else if( (Col2DIndex - Row2DIndex) == 0){
            // Middle
            for (int i = 0; i < game2DArrayIds.length; i++){
                if (gameBoardSquares[i][i] == pastImage && gameBoardSquares[i][i] == currentPiece){
                    consecutivePieces++;
                } else {
                    consecutivePieces = 1;
                }
                pastImage = gameBoardSquares[i][i];
                if (consecutivePieces == 5){
                    // game over
                    endGame("Order");
                }
            }
        } else if ( (Col2DIndex - Row2DIndex) == -1){
            // Bottom
            for (int i = 0; i < game2DArrayIds.length-1; i++){
                if (gameBoardSquares[i][i+1] == pastImage && gameBoardSquares[i][i+1] == currentPiece){
                    consecutivePieces++;
                } else {
                    consecutivePieces = 1;
                }
                pastImage = gameBoardSquares[i][i+1];
                if (consecutivePieces == 5){
                    // game over
                    endGame("Order");
                }
            }
        }

        consecutivePieces = 1;
        pastImage = -1;

        // starts top right
        if( (Col2DIndex + Row2DIndex) == 4){
            // Top
            for (int i = 0, k = game2DArrayIds.length-2; i < game2DArrayIds.length-1; i++,k--){
                if(gameBoardSquares[k][i] != currentPiece){
                    break;
                }
                if(i == game2DArrayIds.length-2){
                    // game over
                    endGame("Order");
                }
            }
        } else if ( (Col2DIndex + Row2DIndex) == 5){
            // Middle
            for (int i = 0, k = game2DArrayIds.length-1; i < game2DArrayIds.length; i++,k--){
                if (gameBoardSquares[k][i] == pastImage && gameBoardSquares[k][i] == currentPiece){
                    consecutivePieces++;
                } else {
                    consecutivePieces = 1;
                }
                pastImage = gameBoardSquares[k][i];
                if (consecutivePieces == 5){
                    // game over
                    endGame("Order");
                }
            }
        } else if ( (Col2DIndex + Row2DIndex) == 6){
            // Bottom
            for (int i = 0, k = game2DArrayIds.length-1; i < game2DArrayIds.length-1; i++,k--){
                if(gameBoardSquares[k][i+1] != currentPiece){
                    break;
                }
                if(i == game2DArrayIds.length-2){
                    // game over
                    endGame("Order");
                }
            }
        }
        piecesPlaced++;
        switchPlayers();
        // if board is completely filled with pieces
        if(allPiecesUsed()) {
            endGame("Chaos");
        }
    }

    // if no more pieces can be used
    public boolean allPiecesUsed(){
        return piecesPlaced >= 36;
    }

    // game over
    public void endGame(String winner){
        gameOver = true;
        Toast.makeText(getApplicationContext(),winner + " is the winner!",Toast.LENGTH_SHORT).show();
    }

    public int findIndexOfId(int id){
        for(int i = 0; i < gameIds.length; i++){
            if(gameIds[i] == id){
                return i;
            }
        }
        return -1;
    }

    public void get2DIndex(int id){
        for(int i = 0; i < game2DArrayIds.length; i++){ // col
            for(int j = 0; j < game2DArrayIds[i].length; j++){ //row
                if(game2DArrayIds[i][j] == id){
                    Col2DIndex = i;
                    Row2DIndex = j;
                    return;
                }
            }
        }
    }

    // switches the players for each round by giving the text bold or removing bold
    public void switchPlayers(){
        if( currentPlayer == 0 ){
            currentPlayer = 1;
            ((TextView) findViewById(R.id.player_chaos)).setTypeface(Typeface.DEFAULT_BOLD);
            ((TextView) findViewById(R.id.player_order)).setTypeface(Typeface.DEFAULT);
        } else {
            currentPlayer = 0;
            ((TextView) findViewById(R.id.player_order)).setTypeface(Typeface.DEFAULT_BOLD);
            ((TextView) findViewById(R.id.player_chaos)).setTypeface(Typeface.DEFAULT);
        }
    }
}
