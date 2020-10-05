package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

/**
 * Represents a graphics object that will contain the drawing
 * of the maze.
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 *
 */
public class MazePanel extends JPanel 
{
	
	/** Represents a movement direction **/
	public enum Direction {UP, RIGHT, DOWN, LEFT, NONE};
	
	@Deprecated
	/** The maximum number of steps that a maze walker should make before "giving up." **/
	static final int NUM_STEPS = 50;
	
	/** The length/width of the room in pixels (used for drawing) **/ 
	public static final int SQUARE_SIZE = 25;
	/** The width of the left/right border around in pixels (used for drawing) **/
	public static final int BORDER_X = 20;
	/** The width of the top/bottom border around in pixels (used for drawing) **/
	public static final int BORDER_Y = 20;
	
	/** The maximum number of rooms in the maze **/
	public static final int MAX_SIZE = 30;
	/** The minimum number of rooms in the maze **/
	public static final int MIN_SIZE = 8;
	
	/** The dimensions of the maze: the number of rooms in each row and column. **/
	private int size;
	/** The 2-d array of rooms representing the maze **/ 
	private Room [][] maze;
	/** The current row location of the walker **/
	private int currentRow;
	/** The current column location of the walker **/
	private int currentCol;

	/**
	 * Instantiates the MazePanel with the
	 * size given as a parameter. Here size refers to the number of
	 * squares that appear in each row and column in the maze.
	 * @param size : int (Assumed to be an odd number).
	 */
	public MazePanel(int size) 
	{
		this.size = size;
		this.setPreferredSize( getSize() );
		Reset ( );
	}

	/**
	 * Returns  the dimensions of the maze panel in pixels.
	 */
	public Dimension getSize ( )
	{
		return new Dimension ( BORDER_X*2 + SQUARE_SIZE*size, BORDER_Y*2 + SQUARE_SIZE*size );
	}
	
	/**
	 * Resets the Maze Panel to be a new random maze with at least one
	 * possible exit point. The walker will be assigned to the center position
	 * of the maze.
	 */
	public void Reset ( ) 
	{
		currentCol = size / 2;
		currentRow = size / 2;
		
		int totalDistance;
		do {
		int dist = size*size+1;
		maze = new Room [size] [size];
		for ( int row = 0; row < size; row++ )
			for ( int col = 0; col < size; col++ )
				maze [row] [col] = new Room ( dist);
		totalDistance = CalcDistance();
		} while ( totalDistance >= size*size );
		
		maze [currentRow] [currentCol].InsertWalker ( );
	}
	
	/**
	 * Calculates the distance to the nearest exit. 
	 * @return int: the distance to the nearest exit.
	 */
	public int CalcDistance ( )
	{
		for ( int i = 0; i<size; i++ )
		{
			for ( int offR = 0; offR <= size/2; offR++ )
			{
				for ( int c = 0; c <= size/2; c++ )
				{
					if ( offR == 0 && maze [offR] [c].isOpenUp() )
					{
						maze [offR] [c].setDistanceToExit( 1 );
					}
					else if ( maze [offR] [c].isOpenUp() &&  maze [offR-1] [c].isOpenDown() )
					{
						maze [offR] [c].setDistanceToExit( 
								Math.min( maze [offR-1] [c].getDistanceToExit()+1,
										  maze [offR] [c].getDistanceToExit() ) );
					}
					if ( c == 0 && maze [offR] [c].isOpenLeft() )
					{
						maze [offR] [c].setDistanceToExit( 1 );
					}
					else if ( maze [offR] [c].isOpenLeft() &&  maze [offR] [c-1].isOpenRight() )
					{
						maze [offR] [c].setDistanceToExit( 
								Math.min( maze [offR] [c-1].getDistanceToExit()+1,
										  maze [offR] [c].getDistanceToExit() ) );
					}
				}
				for ( int c = size-1; c >= size/2; c-- )
				{
					if ( offR == 0 && maze [offR] [c].isOpenUp() )
					{
						maze [offR] [c].setDistanceToExit( 1 );
					}
					else if ( maze [offR] [c].isOpenUp() &&  maze [offR-1] [c].isOpenDown() )
					{
						maze [offR] [c].setDistanceToExit( 
								Math.min( maze [offR-1] [c].getDistanceToExit()+1,
										  maze [offR] [c].getDistanceToExit() ) );
					}
					if ( c == size-1 && maze [offR] [c].isOpenRight() )
					{
						maze [offR] [c].setDistanceToExit( 1 );
					}
					else if ( maze [offR] [c].isOpenRight() &&  maze [offR] [c+1].isOpenLeft() )
					{
						maze [offR] [c].setDistanceToExit( 
								Math.min( maze [offR] [c+1].getDistanceToExit()+1,
										  maze [offR] [c].getDistanceToExit() ) );
					}
				}
			}
			for ( int offR = size-1; offR >= size/2; offR-- )
			{
				for ( int c = 0; c <= size/2; c++ )
				{
					if ( offR == size-1 && maze [offR] [c].isOpenDown() )
					{
						maze [offR] [c].setDistanceToExit( 1 );
					}
					else if ( maze [offR] [c].isOpenDown() &&  maze [offR+1] [c].isOpenUp() )
					{
						maze [offR] [c].setDistanceToExit( 
								Math.min( maze [offR+1] [c].getDistanceToExit()+1,
										  maze [offR] [c].getDistanceToExit() ) );
					}
					if ( c == 0 && maze [offR] [c].isOpenLeft() )
					{
						maze [offR] [c].setDistanceToExit( 1 );
					}
					else if ( maze [offR] [c].isOpenLeft() &&  maze [offR] [c-1].isOpenRight() )
					{
						maze [offR] [c].setDistanceToExit( 
								Math.min( maze [offR] [c-1].getDistanceToExit()+1,
										  maze [offR] [c].getDistanceToExit() ) );
					}
				}
				for ( int c = size-1; c >= size/2; c-- )
				{
					if ( offR == size-1 && maze [offR] [c].isOpenDown() )
					{
						maze [offR] [c].setDistanceToExit( 1 );
					}
					else if ( maze [offR] [c].isOpenDown() &&  maze [offR+1] [c].isOpenUp() )
					{
						maze [offR] [c].setDistanceToExit( 
								Math.min( maze [offR+1] [c].getDistanceToExit()+1,
										  maze [offR] [c].getDistanceToExit() ) );
					}
					if ( c == size-1 && maze [offR] [c].isOpenRight() )
					{
						maze [offR] [c].setDistanceToExit( 1 );
					}
					else if ( maze [offR] [c].isOpenRight() &&  maze [offR] [c+1].isOpenLeft() )
					{
						maze [offR] [c].setDistanceToExit( 
								Math.min( maze [offR] [c+1].getDistanceToExit()+1,
										  maze [offR] [c].getDistanceToExit() ) );
					}
				}
			}
		}
		return maze [currentRow] [currentCol].getDistanceToExit();
	}
	
	/**
	 * Produces the maze of rooms.
	 * @return Room[][]: Returns a 2D array representing the
	 * maze of rooms.
	 */
	public Room[][] getMaze ( )
	{
		return maze;
	}
	
	/**
	 * Draws the components of the MazePanel. It creates a maze
	 * with squares equal to the size defined in the constructor.
	 * And shows the path taken by the random maze walker when it
	 * reaches the outside edge.
	 * @param pen : Graphics (draws the graphic components of the maze)
	 */
	public void paintComponent(Graphics pen) 
	{
		/*
		 * Draws a white dark grey rectangle to represent the total space that will be 
		 * taken up by the maze panel. This will be the background that the maze panel 
		 * will be drawn over.
		 */
		Dimension dim = getSize();
		pen.setColor( Color.LIGHT_GRAY );
		pen.fillRect( 0, 0, dim.width, dim.height );
		pen.setColor( Color.BLACK );
		/*
		 * Draws a white rectangle to represent the total space that will be 
		 * taken up by the maze. This will be the background that the maze 
		 * will be drawn over.
		 */
		pen.setColor( Color.WHITE );
		pen.fillRect( BORDER_X, BORDER_Y, SQUARE_SIZE * size , SQUARE_SIZE * size );
		pen.setColor( Color.BLACK );
		
		/** Draws the maze as a number  of equal-sized squares. **/
		for ( int row = 0; row < size; row++ )
			for ( int col = 0; col < size; col++ )
				maze [ row ] [ col ].draw ( BORDER_X + SQUARE_SIZE * col,
						BORDER_Y + SQUARE_SIZE * row, pen);
	}

	/**
	 * This method can be used to cause a delay when moving through
	 * the maze if done using automation.
	 */
	public void moveDelay ( )
	{
		try {
			Thread.sleep( 1000 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves the MazeWalker in the direction specified if possible.
	 * @param delay 
	 * @param direction: the direction in which to move
	 * @return true of the walker was able to move in the given direction,
	 * otherwise returns false.
	 */
	public boolean move ( Direction direction, boolean delay )
	{
		if ( delay )
			moveDelay();
		switch ( direction )
		{
		case UP: 		return moveUp();
		case LEFT: 	return moveLeft();
		case DOWN:	return moveDown();
		case RIGHT:	return moveRight();
		default:			return false;
		}

	}
	
	/**
	 * Moves the maze walker up into the adjoining room if it is possible
	 * to do so.
	 * @return boolean: true if the walker was able to move, false otherwise.
	 */
	public boolean moveUp() 
	{
		boolean moved = false;
		if ( maze [currentRow] [currentCol].isOpenUp() )
		{
			maze [currentRow] [currentCol].removeWalker(Direction.UP);
			if ( currentRow-1 < 0 || maze [currentRow-1] [currentCol].isOpenDown()  )
			{
				currentRow--;
				moved = true;
			}
			if ( currentRow >= 0 )
				maze [currentRow] [currentCol].InsertWalker();
		}
		System.out.println( "Move Up: " + moved );
		return moved;
	}

	/**
	 * Moves the maze walker down into the adjoining room if it is possible
	 * to do so.
	 * @return boolean: true if the walker was able to move, false otherwise.
	 */
	public boolean moveDown() 
	{
		boolean moved = false;
		if ( maze [currentRow] [currentCol].isOpenDown() )
		{
			maze [currentRow] [currentCol].removeWalker(Direction.DOWN);
			if (  currentRow+1 >= size || maze [currentRow+1] [currentCol].isOpenUp() )
			{
				currentRow++;
				moved = true;
			}
			if ( currentRow < size )
				maze [currentRow] [currentCol].InsertWalker();
		}
		System.out.println( "Move Down: " + moved );
		return moved;
		
	}

	/**
	 * Moves the maze walker left  into the adjoining room if it is possible
	 * to do so.
	 * @return boolean: true if the walker was able to move, false otherwise.
	 */
	public boolean moveLeft() 
	{
		boolean moved = false;
		if ( maze [currentRow] [currentCol].isOpenLeft() )
		{
			maze [currentRow] [currentCol].removeWalker(Direction.LEFT);
			if ( currentCol-1 < 0 || maze [currentRow] [currentCol-1].isOpenRight() )
			{
				currentCol--;
				moved = true;
			}
			if ( currentCol >= 0 )
				maze [currentRow] [currentCol].InsertWalker();
		}
		System.out.println( "Move Left: " + moved );
		return moved;
	}

	/**
	 * Moves the maze walker right  into the adjoining room if it is possible
	 * to do so.
	 * @return boolean: true if the walker was able to move, false otherwise.
	 */
	public boolean moveRight() 
	{
		boolean moved = false;
		if ( maze [currentRow] [currentCol].isOpenRight() )
		{
			maze [currentRow] [currentCol].removeWalker(Direction.RIGHT);
			if ( currentCol+1 >= size || maze [currentRow] [currentCol+1].isOpenLeft() )
			{
				currentCol++;
				moved = true;
			}
			if ( currentCol < size )
				maze [currentRow] [currentCol].InsertWalker();
		}
		System.out.println( "Move Right: " + moved );
		return moved;
	}

	/**
	 * Determines if the maze walker has successfully moved out of the maze.
	 * (i.e. has found one of the exits)
	 * @return boolean: 
	 */
	public boolean foundGoal() 
	{
		// Write the method that determines if the walker has found a way out of the maze.
		return ( currentCol < 0 || currentRow < 0 || 
				 currentCol >= size || currentRow >= size );
	}

	/**
	 * Produces the current location of the walker.
	 * @return Dimension: The current location of the
	 * walker on the map using (x, y) coordinates.
	 */
	public Point getLocation ( )
	{
		return new Point ( currentCol, currentRow );
	}
	
	/**
	 * Produces the current location of the walker. The x coordinate
	 * is the current column and the y coordinate is the current row.
	 * @return Dimension: The current location of the
	 * walker on the map using (x, y) coordinates.
	 */
	public void jumpLocation ( Point loc )
	{
		if ( loc.x < 0 || loc.y < 0 || 
				 loc.x >= size || loc.y >= size )
		{
			return;
		}
		maze [currentRow] [currentCol].removeWalker(Direction.NONE);
		currentRow = loc.y;
		currentCol = loc.x;
		maze [currentRow] [currentCol].InsertWalker();
	}
	
	/**
	 * Produces the size of the maze: the number of rooms
	 * that are in each row and column.
	 * @return int : the number of rooms in each row and column.
	 */
	public int getMazeSize() 
	{
		return this.size;
	}
	
	/**
	 * Produces the room that the MazeWalker is currently in.
	 * @return The current room if the MazeWalker is within the map,
	 * otherwise returns null.
	 */
	public Room getCurrentRoom ( )
	{
		if ( currentRow < 0 || currentCol < 0 || 
				currentRow >= size || currentCol >= size )
		{
			return null;
		}
		return maze[currentRow][currentCol];
	}
	
	/**
	 * Produce the opposite direction from the given direction.
	 * @param direction: the direction to determine the opposite
	 * @return the opposite direction of the given one. The opposite of
	 * NONE is NONE.
	 */
	public static Direction getOppositeDirection ( Direction direction ) 
	{
		switch ( direction )
		{
			case UP: return Direction.DOWN; 
			case DOWN: return Direction.UP; 
			case LEFT: return Direction.RIGHT; 
			case RIGHT: return Direction.LEFT; 
		}
		return Direction.NONE;
	}
	
}
