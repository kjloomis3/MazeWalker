package mazewalker;

import java.util.Random;

import maze.MazePanel;
import maze.MazePanel.Direction;

/**
 * Represents a random "walker." This is a randomized, very simplistic
 * AI that attempts to find its way out of a maze by randomly choosing directions
 * until it finds an outside edge.
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public class RandomMazeWalker extends MazeWalker
{
	
	private static Random randomizer = new Random ( );
	
	Direction lastMove;
	
	public RandomMazeWalker()
	{
		this.lastMove = Direction.NONE;
	}
	
	@Override
	public void Solve(MazePanel maze ) 
	{
		Direction move = getRandomDirection();
		switch ( move )
		{
			case UP: maze.moveUp(); break;
			case DOWN: maze.moveDown(); break;
			case LEFT: maze.moveLeft(); break;
			case RIGHT: maze.moveRight(); break;
		}
	}
	

	/**
	 * Randomly determines a direction that the walker can move in.
	 * @return Direction : UP the walker moved in a negative Y direction,
	 *  DOWN the walker moves in a positive Y direction, LEFT the walker
	 *  moves in a negative X direction, RIGHT the walker moves in a 
	 *  positive X direction.
	 */
	private Direction getRandomDirection ( )
	{
		Direction move = Direction.values()[randomizer.nextInt ( 4 )];
		while ( move == MazePanel.getOppositeDirection ( lastMove ) )
			move = Direction.values()[randomizer.nextInt ( 4 )];
		lastMove = move;
		return move;
	}
	
	public String getName ()
	{
		return "Random";
	}



}


