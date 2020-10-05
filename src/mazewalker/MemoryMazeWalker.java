package mazewalker;

import java.util.Stack;

import maze.MazePanel;
import maze.MazePanel.Direction;

/**
 * This class represents a "walker." The MemoryWalker remembers the previous
 * moves that it made, and attempts to avoid retracing its steps by not backtracking
 * to the immediate last move,
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public class MemoryMazeWalker extends MazeWalker
{
	/** The moves to make in the future. **/
	private Stack<Direction> futureMoves;
	/** The previous moves made **/
	private Stack<Direction> pastMoves;
	
	public MemoryMazeWalker()
	{

		futureMoves = new Stack<Direction>( );
		futureMoves.push ( Direction.NONE );
		for ( Direction d : Direction.values() )
		{
			if ( d != Direction.NONE )
				futureMoves.push(d);
		}
		pastMoves = new Stack<Direction>( );
	}
	
	@Override
	public void Solve( MazePanel maze, boolean delay ) 
	{
		Direction move = futureMoves.pop();
		if ( move == Direction.NONE )
		{
			move = pastMoves.pop();
		}
		System.out.println( move );
		if (maze.move(move, delay))
		{
			pastMoves.push( MazePanel.getOppositeDirection( move ) );
			
			futureMoves.push ( Direction.NONE );
			for ( Direction d : Direction.values() )
			{
				if ( d != Direction.NONE && d != move )
					futureMoves.push(d);
			}
		}
	}
	
	public String getName ()
	{
		return "Memory";
	}



}


