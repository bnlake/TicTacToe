package com.csci4020.tic_tac_toe;
import android.widget.ImageView;
/*
CSCI 4020
Assignment 1
Han Kim
Brian Lake
 */
/**
 * Class (Object) to represent a gamepiece on the board
 */
public class clsGamePiece
{
	/**
	 * Declare some global constants. Makes code readability easier
	 */
	public static final int PIECE_NOTSET = 0xA0;
	public static final int PIECE_X_BLUE = 0xA1;
	public static final int PIECE_X_RED = 0xA2;
	public static final int PIECE_O_BLUE = 0xB1;
	public static final int PIECE_O_RED = 0xB2;
	public static final int PLAYER_A = 0xC1;
	public static final int PLAYER_B = 0xC2;
	/**
	 * Private member variables for "global" scope
	 */
	private int mRow;
	private int mCol;
	private ImageView ivDestination;
	private int gamepieceChoice;

	/**
	 * Constructor
	 * @param mRow int
	 * @param mCol int
	 * @param ivDestination ImageView
	 */
	public clsGamePiece(int mRow, int mCol, ImageView ivDestination)
	{
		this.mRow = mRow;
		this.mCol = mCol;
		this.ivDestination = ivDestination;
	}

	/**
	 * Get piece's ImageView
	 * @return ImageView
	 */
	public ImageView getIvDestination()
	{
		return ivDestination;
	}
	/**
	 * Set Piece's ImageView
	 * @param ivDestination ImageView
	 */
	public void setIvDestination(ImageView ivDestination)
	{
		this.ivDestination = ivDestination;
	}

	/**
	 * Retrieve chosen piece type
	 * @return int
	 */
	public int getGamepieceChoice()
	{
		return gamepieceChoice;
	}
	/**
	 * Set a gamepiece's choice
	 * @param gamepieceChoice int
	 */
	public void setGamepieceChoice(int gamepieceChoice)
	{
		switch (gamepieceChoice)
		{
			case PIECE_NOTSET:
				this.gamepieceChoice = gamepieceChoice;
				this.getIvDestination().setImageResource(R.drawable.ic_gamepiece_placeholder);
				this.getIvDestination().setEnabled(true);
				break;
			case PIECE_O_BLUE:
				this.gamepieceChoice = gamepieceChoice;
				this.getIvDestination().setImageResource(R.drawable.ic_gamepiece_o_blue);
				this.getIvDestination().setEnabled(false);
				break;
			case PIECE_O_RED:
				this.gamepieceChoice = gamepieceChoice;
				this.getIvDestination().setImageResource(R.drawable.ic_gamepiece_o_red);
				this.getIvDestination().setEnabled(false);
				break;
			case PIECE_X_BLUE:
				this.gamepieceChoice = gamepieceChoice;
				this.getIvDestination().setImageResource(R.drawable.ic_gamepiece_x_blue);
				this.getIvDestination().setEnabled(false);
				break;
			case PIECE_X_RED:
				this.gamepieceChoice = gamepieceChoice;
				this.getIvDestination().setImageResource(R.drawable.ic_gamepiece_x_red);
				this.getIvDestination().setEnabled(false);
				break;
				default:
					break;
		}
	}
}
