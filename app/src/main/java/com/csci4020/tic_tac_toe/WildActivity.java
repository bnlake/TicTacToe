package com.csci4020.tic_tac_toe;
/*
CSCI 4020
Assignment 1
Han Kim
Brian Lake
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WildActivity extends Activity
		implements View.OnClickListener
{
	// Global variables
	SharedPreferences sharedPreferences;
	private ImageButton[][] imageButtons = new ImageButton[3][3];
	// counts the # of rounds. If round gets more than 9, which is the max, we know it's a draw
	private int roundCount;
	private boolean player1Turn = true;

	private int player1Points;
	private int player2Points;

	private TextView textViewPlayer1;
	private TextView textViewPlayer2;
	//---------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wild);

		// Need a way to restore state of moves. Shared Preferences seems to be ok
		sharedPreferences = this.getSharedPreferences("tictactoewild", Context.MODE_PRIVATE);
		// Restore current player or pick player A for first move.
		if (sharedPreferences.contains("isPlayer1Turn"))
		{
			if (sharedPreferences.getBoolean("isPlayer1Turn", true))
			{
				choosePlayer(clsGamePiece.PLAYER_A);
			}
			else
			{
				choosePlayer(clsGamePiece.PLAYER_B);
			}
		}
		textViewPlayer1 = findViewById(R.id.text_view_p1);
		textViewPlayer2 = findViewById(R.id.text_view_p2);


		// loop through our rows and columns of imageButtons
		for (int r = 0; r < 3; r++)
		{
			for (int c = 0; c < 3; c++)
			{
				String buttonID = "button_" + r + c;

				// set click listeners on the imageButtons
				int resId = getResources().getIdentifier(buttonID, "id", getPackageName());
				imageButtons[r][c] = findViewById(resId);
				imageButtons[r][c].setOnClickListener(this);
				// Attempt to restore state if changes were made
				if (sharedPreferences.getBoolean(resId + "_isPlayed", false))
				{
					// Retrieve state objects. Error handle by using default null values (odd case)
					imageButtons[r][c].setImageResource(sharedPreferences.getInt(resId + "_image", R.drawable.ic_gamepiece_placeholder));
					imageButtons[r][c].setTag(sharedPreferences.getString(resId + "_tag", ""));
				}
			}
		}

		Button buttonReset = findViewById(R.id.button_reset);
		buttonReset.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				resetGame();
			}
		});


	}


	/**
	 * Open activity to let user choose which piece to play.
	 * Logic after choice is made
	 *
	 * @param v Chosen piece location
	 */
	@Override
	public void onClick(View v)
	{
		// checks if button has already been used
		if (!(v).getTag().equals(""))
		{
			return;
		}

		// if it's player one's turn and they click a button, give the button a text of X or O
		// Also store the change to preferences for state restore
		if (player1Turn)
		{
			// Result from choosepiece activity should be similar to "A|O"
			choosePieceActivity(clsGamePiece.PLAYER_A, v.getId());
		}
		else
		{
			// Result from choosepiece activity should be similar to "A|O"
			choosePieceActivity(clsGamePiece.PLAYER_B, v.getId());
		}
	}

	/**
	 * Start activity to let user pick which piece to play
	 *
	 * @param currentPlayer int
	 */
	private void choosePieceActivity(int currentPlayer, int viewId)
	{
		Intent intent = new Intent(getApplicationContext(), ChoosePieceActivity.class);
		intent.putExtra("currentPlayer", currentPlayer);
		// Use sharedpreferences to remember the view that was pressed
		sharedPreferences.edit().putInt("view", viewId).apply();
		startActivityForResult(intent, 0);
	}

	/**
	 * Process the returned information from choosepiece activity
	 *
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			if (!data.hasExtra("tag"))
				Log.i("====", "Some how a choice wasn't made but the result returned ok");
			else
			{
				if (sharedPreferences.contains("view"))
				{
					// Get ImageView placeholder
					ImageView imageView = findViewById(sharedPreferences.getInt("view", 0));

					// Begin marking the placeholder as played, appropriately
					imageView.setTag(data.getStringExtra("tag"));

					// Update the placeholder with the proper piece that was chosen
					// Split to String array.
					// [0] = Player
					// [1] = Piece
					String[] resultStringArray = imageView.getTag().toString().split("|");
					if (player1Turn)
					{
						if (resultStringArray[1].equals("X"))
						{
							imageView.setImageResource(R.drawable.ic_gamepiece_x_blue);
							// Store the played piece in case screen is rotated
							storePlayedPieceInPreferences(R.drawable.ic_gamepiece_x_blue, imageView.getTag().toString(), imageView);
						}
						else
						{
							imageView.setImageResource(R.drawable.ic_gamepiece_o_blue);
							storePlayedPieceInPreferences(R.drawable.ic_gamepiece_o_blue, imageView.getTag().toString(), imageView);
						}
					}
					else
					{
						if (resultStringArray[1].equals("X"))
						{
							imageView.setImageResource(R.drawable.ic_gamepiece_x_red);
							// Store the played piece in case screen is rotated
							storePlayedPieceInPreferences(R.drawable.ic_gamepiece_x_red, imageView.getTag().toString(), imageView);
						}
						else
						{
							imageView.setImageResource(R.drawable.ic_gamepiece_o_red);
							storePlayedPieceInPreferences(R.drawable.ic_gamepiece_o_red, imageView.getTag().toString(), imageView);
						}
					}

					// Piece was played. Check for a win
					checkForWin();
				}
			}
		}
		else
			Log.i("===", "Result returned other than OK");
	}

	/**
	 * Method to begin the process of checking for a win
	 * This should be called after a user has selected a piece to play
	 */
	private void checkForWin()
	{
		roundCount++;

		// check who wins the game
		if (isRoundWon())
		{
			if (player1Turn)
			{
				markAWin(clsGamePiece.PLAYER_A);
			}
			else
			{
				markAWin(clsGamePiece.PLAYER_B);
			}
			// Clear Preferences
			sharedPreferences.edit().clear().apply();
		}
		else if (roundCount == 9)
		{
			// if no one wins and no more rounds left, it's a draw
			draw();
			// Clear preferences
			sharedPreferences.edit().clear().apply();
		}
		else
		{
			// if no one won and there's no draw, change who's turn it is
			// Pick next player
			if (player1Turn)
				choosePlayer(clsGamePiece.PLAYER_B);
			else
				choosePlayer(clsGamePiece.PLAYER_A);
			sharedPreferences.edit().putBoolean("isPlayer1Turn", player1Turn).apply();
		}
	}

	/**
	 * Check the board to see if either player 1 or player 2 has won
	 * The color of the pieces don't indicate a win. A win is 3 of the same shape piece
	 */
	private boolean isRoundWon()
	{
		String[][] field = new String[3][3];

		// loop through all the imageButtons and save the imageButtons text as a string, either X or O
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				// Extract the piece from the tag
				String[] temp = imageButtons[i][j].getTag().toString().split("|");
				field[i][j] = temp[1];
			}
		}

		// checks each row to make sure each field has the same text, either X or O
		for (int i = 0; i < 3; i++)
		{
			if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
					&& !field[i][0].equals(""))
			{
				return true;
			}
		}

		// checks each column to make sure each field has the same text, either X or O
		for (int i = 0; i < 3; i++)
		{
			if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])
					&& !field[0][i].equals(""))
			{
				return true;
			}
		}

		// checks diagonal from left to right
		if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])
				&& !field[0][0].equals(""))
		{
			return true;
		}

		// checks diagonal from right to left
		if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])
				&& !field[0][2].equals(""))
		{
			return true;
		}

		return false;
	}

	/**
	 * Easy way of processing a win by a specified player.
	 *
	 * @param winningPlayer int Identifying who won the round
	 * @return boolean indicating success or failure
	 */
	private boolean markAWin(int winningPlayer)
	{
		try
		{
			if (winningPlayer == clsGamePiece.PLAYER_A)
			{
				player1Points++;
				Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
			}
			else
			{
				player2Points++;
				Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
			}

			updatePointsText(); // will update points
			resetBoard(); // will reset the board

			return true; // Indicate success
		} catch (Exception e)
		{
			return false;
		}
	}


	/**
	 * Reset the board when there is a draw
	 */
	private void draw()
	{
		Toast.makeText(this, "It's a Draw!", Toast.LENGTH_SHORT).show();
		resetBoard();
	}

	/**
	 * Update the TextView to display points of each player
	 */
	private void updatePointsText()
	{
		textViewPlayer1.setText("Player 1: " + player1Points);
		textViewPlayer2.setText("Player 2: " + player2Points);
	}

	/**
	 * Resets the board so players can play a new game
	 */
	private void resetBoard()
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				imageButtons[i][j].setTag("");
				imageButtons[i][j].setImageResource(0);
			}
		}

		roundCount = 0;

		// Reset preferences for sureity sake
		sharedPreferences.edit().clear().apply();

		// Reset Player
		choosePlayer(clsGamePiece.PLAYER_A);
	}

	/**
	 * Resets points of the players, updates the TextView of the points, and resets the board
	 */
	private void resetGame()
	{
		// reset game and points
		player1Points = 0;
		player2Points = 0;
		updatePointsText();
		resetBoard();
	}

	/**
	 * Saves the state of our variables so it's not affected when orientation changes
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putInt("roundCount", roundCount);
		outState.putInt("player1Points", player1Points);
		outState.putInt("player2Points", player2Points);
		outState.putBoolean("player1Turn", player1Turn);
	}

	/**
	 * Reaches the values from onSaveInstanceState
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		roundCount = savedInstanceState.getInt("roundCount");
		player1Points = savedInstanceState.getInt("player1Points");
		player2Points = savedInstanceState.getInt("player2Points");
		player1Turn = savedInstanceState.getBoolean("player1Turn");
	}

	/**
	 * Simply the process of choosing / changing the current player
	 *
	 * @param player int representing which player to choose (e.g. clsGamePiece.PLAYER_A
	 * @return boolean indicating success or not
	 */
	private boolean choosePlayer(int player)
	{
		switch (player)
		{
			case clsGamePiece.PLAYER_A:
				((ImageView) findViewById(R.id.image_view_currentPlayer)).setImageResource(R.drawable.ic_gamepiece_x_blue);
				player1Turn = true;
				return true;
			case clsGamePiece.PLAYER_B:
				((ImageView) findViewById(R.id.image_view_currentPlayer)).setImageResource(R.drawable.ic_gamepiece_o_red);
				player1Turn = false;
				return true;
			default:
				return false;
		}
	}

	/**
	 * Overloaded chooseplayer method. Set player_A as current player
	 *
	 * @return boolean indicating success or not
	 */
	private boolean choosePlayer()
	{
		try
		{
			((ImageView) findViewById(R.id.image_view_currentPlayer)).setImageResource(R.drawable.ic_gamepiece_x_blue);
			player1Turn = true;
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Store a played piece into sharedpreferences. This restores the board
	 * if the activity is paused for any reason
	 *
	 * @param image int drawable piece
	 * @param tag   string tag
	 * @param v     view to get the imageview id
	 * @return boolean indicating success or not.
	 */
	private boolean storePlayedPieceInPreferences(int image, String tag, View v)
	{
		try
		{
			sharedPreferences.edit().putString(v.getId() + "_tag", tag).apply();
			sharedPreferences.edit().putInt(v.getId() + "_image", image).apply();
			sharedPreferences.edit().putBoolean(v.getId() + "_isPlayed", true).apply();
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

}
