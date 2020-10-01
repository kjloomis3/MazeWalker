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
		futureMoves.push ( Direction.LEFT );
		futureMoves.push ( Direction.DOWN );
		futureMoves.push ( Direction.RIGHT );
		futureMoves.push ( Direction.UP );
		pastMoves = new Stack<Direction>( );
		deadEnds = new ArrayList<Point>( );
	}
	
	@Override
	public void Solve( MazePanel maze ) 
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
		switch ( move )
		{
			case UP:
					if ( !deadEnds.contains( new Point(loc.x, loc.y-1) ) && maze.moveUp() )
					{
						pastMoves.push( MazePanel.getOppositeDirection( move ) );
						lastMove = Direction.UP;
						futureMoves.push ( Direction.NONE );
						futureMoves.push ( Direction.LEFT );
						futureMoves.push ( Direction.RIGHT );
						futureMoves.push ( Direction.UP );
					}
					break;
			case DOWN: 
					if ( !deadEnds.contains( new Point(loc.x, loc.y+1) ) && maze.moveDown() )
					{
						pastMoves.push(  MazePanel.getOppositeDirection( move ) );
						lastMove = Direction.DOWN;
						futureMoves.push ( Direction.NONE );
						futureMoves.push ( Direction.LEFT );
						futureMoves.push ( Direction.DOWN );
						futureMoves.push ( Direction.RIGHT );
					}
					break;
			case LEFT:
					if ( !deadEnds.contains( new Point(loc.x-1, loc.y) ) && maze.moveLeft() )
					{
						pastMoves.push( MazePanel. getOppositeDirection( move ) );
						lastMove = Direction.LEFT;
						futureMoves.push ( Direction.NONE );
						futureMoves.push ( Direction.LEFT );
						futureMoves.push ( Direction.DOWN );
						futureMoves.push ( Direction.UP );
					}
					break;
			case RIGHT: 
					if ( !deadEnds.contains( new Point(loc.x+1, loc.y) ) && maze.moveRight() )
					{
						pastMoves.push(  MazePanel.getOppositeDirection( move ) );
						lastMove = Direction.RIGHT;
						futureMoves.push ( Direction.NONE );
						futureMoves.push ( Direction.DOWN );
						futureMoves.push ( Direction.RIGHT );
						futureMoves.push ( Direction.UP );
					}
					break;
			case NONE:
				break;
			default:
				break;		
		}
	}
	
	public String getName ()
	{
		return "Smarter";
	}



}


