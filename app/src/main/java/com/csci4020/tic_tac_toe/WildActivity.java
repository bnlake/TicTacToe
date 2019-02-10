package com.csci4020.tic_tac_toe;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;

public class WildActivity extends Activity implements View.OnClickListener {

//    private int currentPiece = R.drawable.letter_x, currentPlayer = 0, piecesPlaced = 0;
    private int playerOnePiece = R.drawable.letter_x;
    private int playerOneNumber = 0;
    private int playerOnePiecesPlaced = 0;

    private int playerTwoPiece = R.drawable.letter_o;
    private int playerTwoNumber = 1 ;
    private int playerTwoPiecesPlaced = 0;

    private boolean[] gameBoardFlags = new boolean[9];
    private boolean gameOver = false;
    private int[][] gameBoardSquares = new int[3][3];
    private int Col2DIndex = -1;
    private int Row2DIndex = -1;

    public int[] gameIds = {
            R.id.imageButton00, R.id.imageButton10, R.id.imageButton20,
            R.id.imageButton01, R.id.imageButton11, R.id.imageButton21,
            R.id.imageButton02, R.id.imageButton12, R.id.imageButton22
    };

    public int[][] game2DArrayIds = {
            {R.id.imageButton00, R.id.imageButton01, R.id.imageButton02},
            {R.id.imageButton10, R.id.imageButton11, R.id.imageButton12},
            {R.id.imageButton20, R.id.imageButton21, R.id.imageButton22}
    };

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

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

        textViewPlayer1 = findViewById(R.id.player_order);
        textViewPlayer2 = findViewById(R.id.player_chaos);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_x:
                playerOnePiece = R.drawable.letter_x;
                ((ImageButton) findViewById(R.id.btn_x)).setBackgroundResource(R.drawable.white_button_black_border);
                ((ImageButton) findViewById(R.id.btn_o)).setBackgroundResource(R.drawable.white_button_no_black_border);
                break;
            case R.id.btn_o:
                playerTwoPiece = R.drawable.letter_o;
                ((ImageButton) findViewById(R.id.btn_x)).setBackgroundResource(R.drawable.white_button_no_black_border);
                ((ImageButton) findViewById(R.id.btn_o)).setBackgroundResource(R.drawable.white_button_black_border);
                break;
            case R.id.imageButton00: case R.id.imageButton01: case R.id.imageButton02:
            case R.id.imageButton10: case R.id.imageButton11: case R.id.imageButton12:
            case R.id.imageButton20: case R.id.imageButton21: case R.id.imageButton22:
                placeGamePiece(view.getId());
                break;
            default:
                Toast.makeText(getApplicationContext(),"Invalid view Id ",Toast.LENGTH_SHORT).show();
        }
    }

    public void setupButtonListener() {
        int[] ids = {
                R.id.btn_x, R.id.btn_o,
                R.id.imageButton00, R.id.imageButton01, R.id.imageButton02,
                R.id.imageButton10, R.id.imageButton11, R.id.imageButton12,
                R.id.imageButton20, R.id.imageButton21, R.id.imageButton22,

        };

        for (int i = 0; i < ids.length; i++) {
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

//        ((ImageButton) findViewById(id)).setImageResource(currentPiece);
        ((ImageButton) findViewById(id)).setImageResource(playerOnePiece);

        // the current piece's column and row
//        gameBoardSquares[Col2DIndex][Row2DIndex] = currentPiece;
        gameBoardSquares[Col2DIndex][Row2DIndex] = playerOnePiece;

        int consecutivePieces = 1;
        int pastImage = -1;

        // iterate through each row of the 2D column index
        for(int i = 0; i < gameBoardSquares.length; i++){
//            if (gameBoardSquares[Col2DIndex][i] == pastImage && gameBoardSquares[Col2DIndex][i] == currentPiece){
            if (gameBoardSquares[Col2DIndex][i] == pastImage && gameBoardSquares[Col2DIndex][i] == playerOnePiece){
                consecutivePieces++;
            } else {
                consecutivePieces = 1;
            }

            pastImage = gameBoardSquares[Col2DIndex][i];

            if (consecutivePieces == 3){
                // game over
                player1Wins();
//                endGame("Order");
            }
        }

        consecutivePieces = 1;
        pastImage = -1;

        // iterate through each row of the 2D row index
        for(int i = 0; i < gameBoardSquares.length; i++){
//            if (gameBoardSquares[i][Row2DIndex] == pastImage && gameBoardSquares[i][Row2DIndex] == currentPiece){
//                consecutivePieces++;
//            } else {
//                consecutivePieces = 1;
//            }
//
//            pastImage = gameBoardSquares[i][Row2DIndex];

            if (gameBoardSquares[i][Row2DIndex] == pastImage && gameBoardSquares[i][Row2DIndex] == playerOnePiece){
                playerOnePiecesPlaced++;
//                consecutivePieces++;
            } else {
//                consecutivePieces = 1;
            }

            pastImage = gameBoardSquares[i][Row2DIndex];


            if (consecutivePieces == 3){
                // we have a winner
                player1Wins();
//                endGame("Order");
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
                if (consecutivePieces == 3){
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
                if (consecutivePieces == 3){
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
                if (consecutivePieces == 3){
                    // game over
                    endGame("Order");
                }
            }
        }

        consecutivePieces = 1;
        pastImage = -1;

        // starts top right
        if( (Col2DIndex + Row2DIndex) == 1){
            // Top
            for (int i = 0, k = game2DArrayIds.length-2; i < game2DArrayIds.length-1; i++,k--){
                if(gameBoardSquares[k][i] != currentPiece){
                    break;
                }
                if(i == game2DArrayIds.length-2){
                    // game over
                    player1Wins();
//                    endGame("Order");
                }
            }
        } else
        if ( (Col2DIndex + Row2DIndex) == 2){
            // Middle
            for (int i = 0, k = game2DArrayIds.length-1; i < game2DArrayIds.length; i++,k--){
                if (gameBoardSquares[k][i] == pastImage && gameBoardSquares[k][i] == currentPiece){
                    consecutivePieces++;
                } else {
                    consecutivePieces = 1;
                }
                pastImage = gameBoardSquares[k][i];
                if (consecutivePieces == 3){
                    // game over
                    player1Wins();
//                    endGame("Order");
                }
            }
        } else if ( (Col2DIndex + Row2DIndex) == 3){
            // Bottom
            for (int i = 0, k = game2DArrayIds.length-1; i < game2DArrayIds.length-1; i++,k--){
                if(gameBoardSquares[k][i+1] != currentPiece){
                    break;
                }
                if(i == game2DArrayIds.length-2){
                    // game over
                    player1Wins();
//                    endGame("Order");
                }
            }
        }

        piecesPlaced++;
        switchPlayers();
        // if board is completely filled with pieces
        if(allPiecesUsed()) {
            endGame("Draw");
        }
    }

    // if no more pieces can be used
    public boolean allPiecesUsed(){
        return piecesPlaced >= 9;
    }

    // TODO: Can probably delete this
    // game over.
    public void endGame(String winner){
        gameOver = true;
        if (allPiecesUsed()) {
            Toast.makeText(getApplicationContext(),"It's a draw!",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),winner + " is the winner!",Toast.LENGTH_SHORT).show();
        }


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

            textViewPlayer1.setTypeface(Typeface.DEFAULT_BOLD);
            textViewPlayer2.setTypeface(Typeface.DEFAULT);
//            ((TextView) findViewById(R.id.player_chaos)).setTypeface(Typeface.DEFAULT_BOLD);
//            ((TextView) findViewById(R.id.player_order)).setTypeface(Typeface.DEFAULT);
        } else {
            currentPlayer = 0;

            textViewPlayer1.setTypeface(Typeface.DEFAULT_BOLD);
            textViewPlayer2.setTypeface(Typeface.DEFAULT);
//            ((TextView) findViewById(R.id.player_order)).setTypeface(Typeface.DEFAULT_BOLD);
//            ((TextView) findViewById(R.id.player_chaos)).setTypeface(Typeface.DEFAULT);
        }
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText(); // will update points
        resetBoard(); // will reset the board
    }

    /**
     * When player 2 wins, increment their points, display message, update the points text, and reset the board
     */
    private void player2Wins()
    {
        player2Points++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText(); // will update points
        resetBoard(); // will reset the board
    }


    private void draw() {

        Toast.makeText(this, "It's a Draw!", Toast.LENGTH_SHORT).show();
//        resetGame();
    }

    private void updatePointsText() {

        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    // TODO: If we want to let user reset the game
//    private void resetGame() {
//
//        player1Points = 0;
//        player2Points = 0;
//        updatePointsText();
//        resetBoard();
//    }

    private void resetBoard() {

        // reset game and points
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();

    }
}
