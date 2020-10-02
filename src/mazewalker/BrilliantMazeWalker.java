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
	public void Solve( MazePanel maze, boolean delay ) 
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
								!deadEnds.contains( new Point(loc.x, loc.y-1) ) && maze.move(move, delay) )
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
								!deadEnds.contains( new Point(loc.x, loc.y+1) ) && maze.move(move, delay) )
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
								!deadEnds.contains( new Point(loc.x-1, loc.y) ) && maze.move(move, delay) )
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
								!deadEnds.contains( new Point(loc.x+1, loc.y) ) && maze.move(move, delay) )
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
	
	public String getName ()
	{
		return "Brilliant";
	}



}


