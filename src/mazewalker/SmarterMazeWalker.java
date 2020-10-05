package mazewalker;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import maze.MazePanel;
import maze.MazePanel.Direction;

/**
 * This class represents a "walker." The smarter walker remembers the previous
 * moves that it made, and attempts to avoid retracing its steps. 
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public class SmarterMazeWalker extends MazeWalker
{
	
	private Stack<Direction> futureMoves;
	private Stack<Direction> pastMoves;
	private ArrayList<Point> deadEnds;
	
	Direction lastMove;
	
	public SmarterMazeWalker()
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
				if ( d!= Direction.NONE && d != move )
					futureMoves.push(d);
			}
		}
	}
	
	public String getName ()
	{
		return "Smarter";
	}



}


