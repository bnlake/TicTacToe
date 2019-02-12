package com.csci4020.tic_tac_toe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ChoosePieceActivity extends Activity
		implements View.OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choosepiece);

		//Retrieve the player from intent
		int currentPlayer;
		Intent passedIntent = getIntent();
		if (passedIntent.hasExtra("currentPlayer"))
		{
			currentPlayer = passedIntent.getIntExtra("currentPlayer", clsGamePiece.PLAYER_A);
		}
		else
		{
			currentPlayer = clsGamePiece.PLAYER_A;
		}

		// Change the colors of the imageviews so the player knows who's turn it is
		switch (currentPlayer)
		{
			case clsGamePiece.PLAYER_A:
				setImageViews(R.drawable.ic_gamepiece_x_blue, R.drawable.ic_gamepiece_o_blue, "A");
				break;
			case clsGamePiece.PLAYER_B:
				setImageViews(R.drawable.ic_gamepiece_x_red, R.drawable.ic_gamepiece_o_red, "B");
				break;
			default:
				break;
		}

		findViewById(R.id.image_view_X).setOnClickListener(this);
		findViewById(R.id.image_view_O).setOnClickListener(this);
	}

	/**
	 * Set Images for ImageView and tag
	 *
	 * @param imageResource_X int drawable
	 * @param imageResource_O int drawable
	 * @param playerTag       String Player A or B
	 * @return boolean indicating success or not
	 */
	private boolean setImageViews(int imageResource_X, int imageResource_O, String playerTag)
	{
		try
		{
			((ImageView) findViewById(R.id.image_view_X)).setImageResource(imageResource_X);
			(findViewById(R.id.image_view_X)).setTag(playerTag + "|X");
			((ImageView) findViewById(R.id.image_view_O)).setImageResource(imageResource_O);
			(findViewById(R.id.image_view_O)).setTag(playerTag + "|O");
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * This activity has one purpose. Pick one ImageView and return the choice
	 *
	 * @param view Chosen piece
	 */
	@Override
	public void onClick(View view)
	{
		Intent intent = new Intent();
		intent.putExtra("tag", view.getTag().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
}
