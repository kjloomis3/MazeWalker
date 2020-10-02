package mazewalker;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import maze.MazePanel;
import maze.MazePanel.Direction;

/**
 * This class represents a "walker." The brilliant walker remembers the previous
 * moves that it made, and attempts to avoid retracing its steps. It can jump back 
 * to a previous position if it finds only dead ends in its current moves. 
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public class GeniusMazeWalker extends MazeWalker
{
	
	private Stack<Direction> futureMoves;
	private Direction goback;
	int lastDistance;
	boolean makingProgress;
	
	public GeniusMazeWalker()
	{
		futureMoves = new Stack<Direction>( );
		goback = Direction.NONE;
		makingProgress = true;
		lastDistance = MazePanel.MAX_SIZE*2+1; 
	}
	
	@Override
	public void Solve( MazePanel maze, boolean delay ) 
	{
		int currentDistance = maze.getCurrentRoom().getDistanceToExit();
		if ( currentDistance > lastDistance )
		{
			makingProgress = false;
			maze.move(goback, delay);
		}
		else
		{
			if ( makingProgress )
			{
				for ( Direction d : Direction.values() )
				{
					if ( d != MazePanel.getOppositeDirection(goback) && d != Direction.NONE  )
						futureMoves.push(d);
				}
			}
			makingProgress= true;
			lastDistance = currentDistance;
			Direction dir = futureMoves.pop();
			goback =  MazePanel.getOppositeDirection(dir);
			maze.move ( dir, delay );	
		}
	}
	
	public String getName ()
	{
		return "Genius";
	}

}


