package mazewalker;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import maze.MazePanel;

/**
 * This class represents a "walker." The brilliant walker remembers the previous
 * moves that it made, and attempts to avoid retracing its steps. It can jump back 
 * to a previous position if it finds only dead ends in its current moves. 
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public class BrilliantMazeWalker extends MazeWalker
{
	
	private Stack<Direction> futureMoves;
	private Stack<Point> pastMoves;
	private ArrayList<Point> deadEnds;
	
	Direction lastMove;
	
	public BrilliantMazeWalker()
	{
		this.lastMove = Direction.NONE;

		futureMoves = new Stack<Direction>( );
		futureMoves.push ( Direction.NONE );
		futureMoves.push ( Direction.LEFT );
		futureMoves.push ( Direction.DOWN );
		futureMoves.push ( Direction.RIGHT );
		futureMoves.push ( Direction.UP );
		pastMoves = new Stack<Point>( );
		deadEnds = new ArrayList<Point>( );
	}
	
	@Override
	public void Solve( MazePanel maze ) 
	{
		Direction move = futureMoves.pop();
		if ( move == Direction.NONE )
		{
			deadEnds.add( maze.getLocation() );
			Point lstLoc = pastMoves.pop();
			maze.jumpLocation( lstLoc );
			System.out.println( "Jump back" );
		}
		else
		{
			System.out.println( move );
			lastMove = Direction.NONE;
			Point loc = maze.getLocation( );
			switch ( move )
			{
				case UP:
						if ( !pastMoves.contains( new Point(loc.x, loc.y-1) ) &&
								!deadEnds.contains( new Point(loc.x, loc.y-1) ) && maze.moveUp() )
						{
							pastMoves.push( loc );
							lastMove = Direction.UP;
							futureMoves.push ( Direction.NONE );
							futureMoves.push ( Direction.LEFT );
							futureMoves.push ( Direction.RIGHT );
							futureMoves.push ( Direction.UP );
						}
						break;
				case DOWN: 
						if ( !pastMoves.contains( new Point(loc.x, loc.y+1) ) &&
								!deadEnds.contains( new Point(loc.x, loc.y+1) ) && maze.moveDown() )
						{
							pastMoves.push( loc );
							lastMove = Direction.DOWN;
							futureMoves.push ( Direction.NONE );
							futureMoves.push ( Direction.LEFT );
							futureMoves.push ( Direction.DOWN );
							futureMoves.push ( Direction.RIGHT );
						}
						break;
				case LEFT:
						if ( !pastMoves.contains( new Point(loc.x-1, loc.y) ) &&
								!deadEnds.contains( new Point(loc.x-1, loc.y) ) && maze.moveLeft() )
						{
							pastMoves.push( loc );
							lastMove = Direction.LEFT;
							futureMoves.push ( Direction.NONE );
							futureMoves.push ( Direction.LEFT );
							futureMoves.push ( Direction.DOWN );
							futureMoves.push ( Direction.UP );
						}
						break;
				case RIGHT: 
						if ( !pastMoves.contains( new Point(loc.x+1, loc.y) ) &&
								!deadEnds.contains( new Point(loc.x+1, loc.y) ) && maze.moveRight() )
						{
							pastMoves.push( loc );
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
	}
	
	private Direction getOppositeDirection ( Direction d ) 
	{
		switch ( d )
		{
			case UP: return Direction.DOWN; 
			case DOWN: return Direction.UP; 
			case LEFT: return Direction.RIGHT; 
			case RIGHT: return Direction.LEFT; 
		}
		return Direction.NONE;
	}

	
	public String getName ()
	{
		return "Smarter";
	}



}

