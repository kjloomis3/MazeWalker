package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


/**
 * Represents a room that belongs to a maze. Each room will have doors that
 * open into adjoining rooms at the top, bottom, left, and right walls of the
 * room.
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 *
 */
public class Room {

	private boolean doorUp, doorDown, doorLeft, doorRight;
	private boolean containsWalker;
	private boolean pastLocation;
	private int distanceToExit;
	private static final Boolean DRAW_DISTANCE = false;
	
	/**
	 * Creates a room using randomly determined doorways. Each room may open
	 * in the up, down, left, and right directions.
	 */
	public Room ( int dist )
	{
		distanceToExit = dist;
		containsWalker = false;
		pastLocation = false;
		doorUp = doorDown = doorLeft = doorRight = false;
		Random rdm = new Random( );
		if ( rdm.nextInt( 3 ) > 0 )
			 doorUp = true;
		if ( rdm.nextInt( 3 ) > 0 )
			 doorDown = true;
		if ( rdm.nextInt( 3 ) > 0 )
			 doorLeft = true;
		if ( rdm.nextInt( 3 ) > 0 )
			 doorRight = true;
	}
	
	public int getDistanceToExit() 
	{
		return distanceToExit;
	}

	public void setDistanceToExit(int distanceToExit) 
	{
		this.distanceToExit = distanceToExit;
	}

	/**
	 * Sets the room to not contain a walker. This affects how
	 * the room will be drawn. 
	 */
	public void removeWalker() 
	{
		if ( this.containsWalker )
			pastLocation = true;
		this.containsWalker = false;
	}

	/**
	 * Sets the room to contain a walker. This affects how
	 * the room will be drawn. 
	 */
	public void InsertWalker ( ) 
	{
		this.containsWalker = true;
	}

	/**
	 * Determines if the room is open in the up direction.
	 * @return boolean : True if there is a door open in the up
	 * direction, false otherwise.
	 */
	public boolean isOpenUp ( )
	{
		return doorUp;
	}
	
	/**
	 * Determines if the room is open in the down direction.
	 * @return boolean : True if there is a door open in the down
	 * direction, false otherwise.
	 */
	public boolean isOpenDown ( )
	{
		return doorDown;
	}
	
	/**
	 * Determines if the room is open in the left direction.
	 * @return boolean : True if there is a door open in the left
	 * direction, false otherwise.
	 */
	public boolean isOpenLeft( )
	{
		return doorLeft;
	}
	
	/**
	 * Determines if the room is open in the right direction.
	 * @return boolean : True if there is a door open in the right
	 * direction, false otherwise.
	 */
	public boolean isOpenRight( )
	{
		return doorRight;
	}
	
	/**
	 * Draws a single room with doorways.
	 * @param x (int) : The x coordinate of the top-left corner of the room
	 * @param y (int) : The y coordinate of the top-left corner of the room
	 * @param pen (Graphics) : The graphics pen used to draw the map.
	 */
	public void draw ( int x, int y, Graphics pen )
	{
		// Draw the top
		if ( !doorUp )
		{
			pen.drawRect( x, y, MazePanel.SQUARE_SIZE, 0 );
		}
		else
		{
			pen.drawRect( x, y, MazePanel.SQUARE_SIZE/4, 0 );
			pen.drawRect( x + 3*MazePanel.SQUARE_SIZE/4, y, MazePanel.SQUARE_SIZE/4, 0 );
		}
		// Draw the bottom
		if ( !doorDown )
		{
			pen.drawRect( x, y+MazePanel.SQUARE_SIZE, MazePanel.SQUARE_SIZE, 0 );
		}
		else
		{
			pen.drawRect( x, y+MazePanel.SQUARE_SIZE, MazePanel.SQUARE_SIZE/4, 0 );
			pen.drawRect( x + 3*MazePanel.SQUARE_SIZE/4, y+MazePanel.SQUARE_SIZE, MazePanel.SQUARE_SIZE/4, 0 );
		}
		// Draw the left
		if ( !doorLeft )
		{
			pen.drawRect( x, y, 0, MazePanel.SQUARE_SIZE );
		}
		else
		{
			pen.drawRect( x, y, 0, MazePanel.SQUARE_SIZE/4 );
			pen.drawRect( x, y + 3*MazePanel.SQUARE_SIZE/4, 0, MazePanel.SQUARE_SIZE/4 );
		}		
		// Draw the left
		if ( !doorRight )
		{
			pen.drawRect( x+MazePanel.SQUARE_SIZE, y, 0, MazePanel.SQUARE_SIZE );
		}
		else
		{
			pen.drawRect( x+MazePanel.SQUARE_SIZE, y, 0, MazePanel.SQUARE_SIZE/4 );
			pen.drawRect( x+MazePanel.SQUARE_SIZE, y + 3*MazePanel.SQUARE_SIZE/4, 0, MazePanel.SQUARE_SIZE/4 );
		}
		// Draw the walker
		if ( containsWalker )
		{
			pen.setColor( Color.RED );
			pen.fillRect( x+MazePanel.SQUARE_SIZE/4, y+MazePanel.SQUARE_SIZE/4, 
						  MazePanel.SQUARE_SIZE/2, MazePanel.SQUARE_SIZE/2 );
			pen.setColor( Color.BLACK );
		}
		else if ( pastLocation )
		{
			pen.setColor( Color.LIGHT_GRAY );
			pen.fillRect( x+MazePanel.SQUARE_SIZE/4, y+MazePanel.SQUARE_SIZE/4, 
						  MazePanel.SQUARE_SIZE/2, MazePanel.SQUARE_SIZE/2 );
			pen.setColor( Color.BLACK );
		}
		// Draw distance
		if ( DRAW_DISTANCE )
		{
			pen.drawString(Integer.toString(distanceToExit), 
					x+MazePanel.SQUARE_SIZE/4, y+MazePanel.SQUARE_SIZE * 3/4 );
		}
	}
}
