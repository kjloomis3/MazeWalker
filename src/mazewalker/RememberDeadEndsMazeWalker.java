package mazewalker;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import maze.MazePanel;
import maze.MazePanel.Direction;

/**
 * This class represents a "walker." The RememberDeadEndsMazeWalker remembers the previous
 * moves that it made, and attempts to avoid retracing its steps. It also remembers portions of
 * the maze that result in dead ends (no solution) and avoids those as well. However, it doesn't
 * detect if it ends of in a cycle. 
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public class RememberDeadEndsMazeWalker extends MazeWalker
{
	/** The moves to make in the future. **/
	private Stack<Direction> futureMoves;
	/** The previous moves made **/
	private Stack<Direction> pastMoves;
	/** Locations that result in a dead end in the maze **/
	private ArrayList<Point> deadEnds;
	
	Direction lastMove;
	
	public RememberDeadEndsMazeWalker()
	{
		this.lastMove = Direction.NONE;

		futureMoves = new Stack<Direction>( );
		futureMoves.push ( Direction.NONE );
		for ( Direction d : Direction.values() )
		{
			if ( d != Direction.NONE )
				futureMoves.push(d);
		}
		pastMoves = new Stack<Direction>( );
		deadEnds = new ArrayList<Point>( );
	}
	
	@Override
	public void Solve( MazePanel maze, boolean delay ) 
	{
		Direction move = futureMoves.pop();
		if ( move == Direction.NONE )
		{
			deadEnds.add( maze.getLocation() );
			move = pastMoves.pop();
		}
		System.out.println( move );
		lastMove = Direction.NONE;
		Point loc = maze.getLocation( );
		
		boolean moved = false;
		switch ( move )
		{
			case UP:
					moved = !deadEnds.contains( new Point(loc.x, loc.y-1) ) && maze.move(move, delay);
					break;
			case DOWN: 
					moved = !deadEnds.contains( new Point(loc.x, loc.y+1) ) && maze.move(move, delay);
					break;
			case LEFT:
					moved = !deadEnds.contains( new Point(loc.x-1, loc.y) ) && maze.move(move, delay);
					break;
			case RIGHT: 
					moved = !deadEnds.contains( new Point(loc.x+1, loc.y) ) && maze.move(move, delay);
					break;
			case NONE:
			default:
				break;		
		}
		if ( moved )
		{
			pastMoves.push( MazePanel.getOppositeDirection( move ) );
			lastMove = move;
			for ( Direction d : Direction.values() )
			{
				if ( d!= Direction.NONE && d != MazePanel.getOppositeDirection( move ) )
					futureMoves.push(d);
			}
		}
	}
	
	@Override
	public String getName ()
	{
		return "Remember Dead-Ends";
	}



}


