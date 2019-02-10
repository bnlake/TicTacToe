package com.csci4020.tic_tac_toe;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class WildActivity extends Activity implements View.OnClickListener {

    private boolean gameOver = false;
    private int[][] gameBoardSquares = new int[3][3];
    private int Col2DIndex = 0;
    private int Row2DIndex = 0;

    private int playerOnePiece = R.drawable.letter_x;
    private int playerOnePiecesPlaced = 0;

    private int playerTwoPiece = R.drawable.letter_o;
    private int playerTwoPiecesPlaced = 0;

    private int currentPlayer = R.drawable.letter_x;

    // Array for each column
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

        // set all squares to empty from the beginning
        for(int i = 0; i < game2DArrayIds.length; i++){ // col
            for(int j = 0; j < game2DArrayIds[i].length; j++){ //row
                gameBoardSquares[i][j] = R.drawable.white_square;
            }
        }

        // textViews to keep score
        textViewPlayer1 = findViewById(R.id.player_one);
        textViewPlayer2 = findViewById(R.id.player_two);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("gameOver", gameOver);
        outState.putInt("playerOnePiecesPlaced", playerOnePiecesPlaced);
        outState.putInt("playerTwoPiecesPlaced", playerTwoPiecesPlaced);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        gameOver = savedInstanceState.getBoolean("gameOver");
        playerOnePiecesPlaced = savedInstanceState.getInt("playerOnePiecesPlaced");
        playerTwoPiecesPlaced = savedInstanceState.getInt("playerTwoPiecesPlaced");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_x:
                playerOnePiece = R.drawable.letter_x;
                ((ImageButton) findViewById(R.id.btn_x)).setBackgroundResource(R.drawable.white_button_black_border);
                ((ImageButton) findViewById(R.id.btn_o)).setBackgroundResource(R.drawable.white_button_no_black_border);

                currentPlayer = R.drawable.letter_x;

                break;
            case R.id.btn_o:
                playerTwoPiece = R.drawable.letter_o;
                ((ImageButton) findViewById(R.id.btn_x)).setBackgroundResource(R.drawable.white_button_no_black_border);
                ((ImageButton) findViewById(R.id.btn_o)).setBackgroundResource(R.drawable.white_button_black_border);

                currentPlayer = R.drawable.letter_o;

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

        // get the col and row
        get2DIndex(id);
        Log.d("GET2D", "Col2DIndex: " + Col2DIndex + " ROW2DIndex: " + Row2DIndex);

        // squares will start off as white squares
        if(gameBoardSquares[Col2DIndex][Row2DIndex] != R.drawable.white_square) {
            return;
        }

        // don't continue if game is over
        if(gameOver){
            Toast.makeText(getApplicationContext(),"Press the back button to start another game",Toast.LENGTH_SHORT).show();
            return;
        }

        ((ImageButton) findViewById(id)).setImageResource(currentPlayer);

        // the current piece's column and row
        gameBoardSquares[Col2DIndex][Row2DIndex] = currentPlayer;

        playerOnePiecesPlaced = 1;
        playerTwoPiecesPlaced = 1;
        int pastImage = -1;

        // iterate through each row of the 2D column index
        for(int i = 0; i < gameBoardSquares.length; i++){

            if (gameBoardSquares[Col2DIndex][i] == pastImage && gameBoardSquares[Col2DIndex][i] == currentPlayer
                    && gameBoardSquares[Col2DIndex][i] == playerOnePiece){
                playerOnePiecesPlaced++;

            } else if (gameBoardSquares[Col2DIndex][i] == pastImage && gameBoardSquares[Col2DIndex][i] == currentPlayer
                    && gameBoardSquares[Col2DIndex][i] == playerTwoPiece){
                playerTwoPiecesPlaced++;

            } else {

                playerOnePiecesPlaced = 1;
                playerTwoPiecesPlaced = 1;
            }

            pastImage = gameBoardSquares[Col2DIndex][i];

            if (playerOnePiecesPlaced == 3) {
                player1Wins();
            } else if (playerTwoPiecesPlaced == 3) {
                player2Wins();
            }
        }

        playerOnePiecesPlaced = 1;
        playerTwoPiecesPlaced = 1;
        pastImage = -1;

        // iterate through each row of the 2D row index
        for(int i = 0; i < gameBoardSquares.length; i++){
            if (gameBoardSquares[i][Row2DIndex] == pastImage && gameBoardSquares[i][Row2DIndex] == currentPlayer
                    && gameBoardSquares[i][Row2DIndex] == playerOnePiece){
                playerOnePiecesPlaced++;

            } else if (gameBoardSquares[i][Row2DIndex] == pastImage && gameBoardSquares[i][Row2DIndex] == currentPlayer
                    && gameBoardSquares[i][Row2DIndex] == playerTwoPiece){
                playerTwoPiecesPlaced++;
            }

            pastImage = gameBoardSquares[i][Row2DIndex];

            if (playerOnePiecesPlaced == 3) {

                player1Wins();
            } else if (playerTwoPiecesPlaced == 3) {

                player2Wins();
            }
        }

        // Diagonal
        playerOnePiecesPlaced = 1;
        playerTwoPiecesPlaced = 1;
        pastImage = -1;

        // starts top left\
        if ( (Col2DIndex - Row2DIndex) == 1){
            // Top
            for (int i = 0; i < game2DArrayIds.length - 1; i++){
                if (gameBoardSquares[i + 1][i] == pastImage && gameBoardSquares[i + 1][i] == currentPlayer
                        && gameBoardSquares[i + 1][i] == playerOnePiece){
                    playerOnePiecesPlaced++;
                } else if (gameBoardSquares[i+1][i] == pastImage && gameBoardSquares[i+1][i] == currentPlayer
                        && gameBoardSquares[i+1][i] == playerTwoPiece) {
                    playerTwoPiecesPlaced++;
                } else {
                    playerOnePiecesPlaced = 1;
                    playerTwoPiecesPlaced = 1;
                }

                pastImage = gameBoardSquares[i + 1][i];

                if (playerOnePiecesPlaced == 3) {
                    player1Wins();
                } else if (playerTwoPiecesPlaced == 3) {
                    player2Wins();
                }
            }
        } else if( (Col2DIndex - Row2DIndex) == 0){
            // Middle
            for (int i = 0; i < game2DArrayIds.length; i++){
                if (gameBoardSquares[i][i] == pastImage && gameBoardSquares[i][i] == currentPlayer
                        && gameBoardSquares[i][i] == playerOnePiece){
                    playerOnePiecesPlaced++;
                } else if (gameBoardSquares[i][i] == pastImage && gameBoardSquares[i][i] == currentPlayer
                        && gameBoardSquares[i][i] == playerTwoPiece) {
                    playerTwoPiecesPlaced++;
                } else {
                    playerOnePiecesPlaced = 1;
                    playerTwoPiecesPlaced = 1;
                }

                pastImage = gameBoardSquares[i][i];

                if (playerOnePiecesPlaced == 3) {
                    player1Wins();
                } else if (playerTwoPiecesPlaced == 3) {
                    player2Wins();
                }
            }
        } else if ( (Col2DIndex - Row2DIndex) == -1){
            // Bottom
            for (int i = 0; i < game2DArrayIds.length - 1; i++){
                if (gameBoardSquares[i][i + 1] == pastImage && gameBoardSquares[i][i + 1] == currentPlayer
                        && gameBoardSquares[i][i + 1] == playerOnePiece){
                    playerOnePiecesPlaced++;
                } else if (gameBoardSquares[i][i+1] == pastImage && gameBoardSquares[i][i+1] == currentPlayer
                        && gameBoardSquares[i][i+1] == playerTwoPiece) {
                    playerTwoPiecesPlaced++;
                } else {
                    playerOnePiecesPlaced = 1;
                    playerTwoPiecesPlaced = 1;
                }

                pastImage = gameBoardSquares[i][i+1];

                if (playerOnePiecesPlaced == 3) {
                    player1Wins();
                } else if (playerTwoPiecesPlaced == 3) {
                    player2Wins();
                }
            }
        }

        playerOnePiecesPlaced = 1;
        playerTwoPiecesPlaced = 1;
        pastImage = -1;

        // starts top right
        if( (Col2DIndex + Row2DIndex) == 1){
            // Top
            for (int i = 0, k = game2DArrayIds.length - 2; i < game2DArrayIds.length - 1; i++,k--){

                if (gameBoardSquares[k][i] != currentPlayer) {
                    break;
                }

                if(i == game2DArrayIds.length-2 && currentPlayer == playerOnePiece){
                    player1Wins();
                } else if (i == game2DArrayIds.length - 2 && currentPlayer == playerTwoPiece) {
                    player2Wins();
                }
            }
        } else if ( (Col2DIndex + Row2DIndex) == 2){
            // Middle
            for (int i = 0, k = game2DArrayIds.length - 1; i < game2DArrayIds.length; i++,k--){
                if (gameBoardSquares[k][i] == pastImage && gameBoardSquares[k][i] == currentPlayer
                        && gameBoardSquares[k][i] == playerOnePiece){
                    playerOnePiecesPlaced++;
                } else if (gameBoardSquares[k][i] == pastImage && gameBoardSquares[k][i] == currentPlayer
                        && gameBoardSquares[k][i] == playerTwoPiece) {
                    playerTwoPiecesPlaced++;
                } else {
                    playerOnePiecesPlaced = 1;
                    playerTwoPiecesPlaced = 1;
                }

                pastImage = gameBoardSquares[k][i];

                if (playerOnePiecesPlaced == 3) {
                    player1Wins();
                    Toast.makeText(getApplicationContext(),"Player One is the winner!",Toast.LENGTH_SHORT).show();
                } else if (playerTwoPiecesPlaced == 3) {
                    player2Wins();
                    Toast.makeText(getApplicationContext(),"Player TWo is the winner!",Toast.LENGTH_SHORT).show();
                }
            }
        } else if ( (Col2DIndex + Row2DIndex) == 3){
            // Bottom
            for (int i = 0, k = game2DArrayIds.length - 1; i < game2DArrayIds.length - 1; i++,k--){
                if(gameBoardSquares[k][i+1] != currentPlayer){
                    break;
                }

                if(i == game2DArrayIds.length-2 && currentPlayer == playerOnePiece){
                    player1Wins();
                    Toast.makeText(getApplicationContext(),"Player 1 is the winner!",Toast.LENGTH_SHORT).show();
                } else if (i == game2DArrayIds.length - 2 && currentPlayer == playerTwoPiece) {
                    player2Wins();
                    Toast.makeText(getApplicationContext(),"Player 2 is the winner!",Toast.LENGTH_SHORT).show();
                }
            }
        }

        // TODO: may be able to remove this
//        playerOnePiecesPlaced++;
//        playerTwoPiecesPlaced++;


        // if board is completely filled with pieces
        if(allPiecesUsed()) {
            draw();
        }
    }

    // if no more pieces can be used
    public boolean allPiecesUsed(){

        int totalPieces = playerOnePiecesPlaced + playerTwoPiecesPlaced;
        return totalPieces >= 9;
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
        resetGame();
    }

    private void updatePointsText() {

        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    // TODO: If we want to let user reset the game
    private void resetGame() {

        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    // TODO: Removing images from the board.
    private void resetBoard() {

//        for (int i = 0; i < 3; i++)
//        {
//            for (int j = 0; j < 3; j++)
//            {
//                gameBoardSquares[i][j] = R.drawable.white_square;
//
////                imageButtons[i][j].setTag("");
////                imageButtons[i][j].setImageResource(0);
//            }
//        }

        for(int i = 0; i < game2DArrayIds.length; i++){ // col
            for(int j = 0; j < game2DArrayIds[i].length; j++){ //row
                gameBoardSquares[i][j] = R.drawable.white_square;
            }
        }

        currentPlayer = R.drawable.letter_x;
    }
}
