package maze;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
	private MazePanel.Direction pastDirection;
	private int distanceToExit;
	private static final Boolean DRAW_DISTANCE = false;
	private static final boolean DRAW_DIRECTION = true;
	
	/**
	 * Creates a room using randomly determined doorways. Each room may open
	 * in the up, down, left, and right directions.
	 */
	public Room ( int dist )
	{
		distanceToExit = dist;
		containsWalker = false;
		pastLocation = false;
		pastDirection = MazePanel.Direction.NONE;
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
	public void removeWalker(MazePanel.Direction direction) 
	{
		if ( this.containsWalker )
		{
			pastLocation = true;
			pastDirection = direction;
		}
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
		else if ( DRAW_DIRECTION)
		{
			Graphics2D g2D = (Graphics2D)  pen; 
			g2D.setStroke(new BasicStroke(3));
			g2D.setColor( Color.BLUE);
			Point top = new Point(x+MazePanel.SQUARE_SIZE/2,  y+MazePanel.SQUARE_SIZE/4 );
			Point right = new Point(x+ MazePanel.SQUARE_SIZE/4*3-1,  y+MazePanel.SQUARE_SIZE/2 );
			Point bottom = new Point(x+MazePanel.SQUARE_SIZE/2,  y+MazePanel.SQUARE_SIZE/4*3-1 );
			Point left = new Point(x+ MazePanel.SQUARE_SIZE/4, y+MazePanel.SQUARE_SIZE/2 );
			switch ( pastDirection)
			{
			case UP:
				g2D.drawLine( top.x, top.y, left.x,left.y );
				g2D.drawLine( top.x, top.y, right.x, right.y );
				g2D.drawLine( top.x, top.y, bottom.x, bottom.y );
				break;
			case RIGHT:
				g2D.drawLine( right.x,right.y, top.x, top.y );
				g2D.drawLine( right.x, right.y, bottom.x, bottom.y );
				g2D.drawLine( right.x, right.y, left.x, left.y );
				break;
			case DOWN:
				g2D.drawLine( bottom.x, bottom.y, left.x,left.y );
				g2D.drawLine( bottom.x, bottom.y, right.x, right.y );
				g2D.drawLine( bottom.x, bottom.y, top.x, top.y );
				break;
			case LEFT:
				g2D.drawLine( left.x,left.y, top.x, top.y );
				g2D.drawLine( left.x, left.y, bottom.x, bottom.y );
				g2D.drawLine( left.x, left.y, right.x, right.y );
				break;
			}
			g2D.setColor( Color.BLACK );
			g2D.setStroke(new BasicStroke(1));
		}
	}
}
