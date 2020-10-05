package mazewalker;

import java.util.Random;

import maze.MazePanel;
import maze.MazePanel.Direction;

/**
 * Represents a left-turn "walker." This very simplistic  AI attempts to find its way out 
 * of a maze by always making left-hand turns until it finds an outside edge. If a cycle
 * exists in the maze, it may get caught in this cycle.
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public class LeftTurnMazeWalker extends MazeWalker
{
	/** The last immediate move move by the maze walker **/
	private Direction lastMove;
	/** Indicates whether the last immediate move was successful of not. **/
	private boolean lastSuccessful;
	
	public LeftTurnMazeWalker()
	{
		this.lastMove = Direction.LEFT;
		lastSuccessful = true;
	}
	
	@Override
	public void Solve(MazePanel maze, boolean delay )
	{
		Direction move = this.getNextDirection();
		System.out.print(move);
		this.lastSuccessful = maze.move(move, delay);
		this.lastMove = move;

	}
	

	/**
	 * Determines the next direction that the walker should move in.
	 * @return Direction : UP the walker moves in a negative Y direction,
	 *  DOWN the walker moves in a positive Y direction, LEFT the walker
	 *  moves in a negative X direction, RIGHT the walker moves in a 
	 *  positive X direction.
	 */
	private Direction getNextDirection ( )
	{
		Direction move =this.lastMove;
		if ( !this.lastSuccessful )
		{
			switch( this.lastMove )
			{
			case UP: move = Direction.LEFT; break;
			case RIGHT: move = Direction.UP; break;
			case DOWN: move = Direction.RIGHT; break;
			case LEFT: move = Direction.DOWN; break;
			default:
				break;
			}
		}
		return move;
	}
	
	@Override
	public String getName ()
	{
		return "Random";
	}



}


