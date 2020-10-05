package mazewalker;

import java.util.Stack;

import maze.MazePanel;
import maze.MazePanel.Direction;

/**
 * This class represents a "walker." The smart walker remembers the previous
 * moves that it made, and attempts to avoid retracing its steps. 
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public class MemoryMazeWalker extends MazeWalker
{
	
	private Stack<Direction> futureMoves;
	private Stack<Direction> pastMoves;
	
	Direction lastMove;
	
	public MemoryMazeWalker()
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
			lastMove =  move;
			
			futureMoves.push ( Direction.NONE );
			for ( Direction d : Direction.values() )
			{
				if ( d != Direction.NONE && d != move )
					futureMoves.push(d);
			}
		}
		/*
		
		switch ( move )
		{
			case UP:
					if ( maze.moveUp() )
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
					if ( maze.moveDown() )
					{
						pastMoves.push( MazePanel.getOppositeDirection( move ) );
						lastMove = Direction.DOWN;
						futureMoves.push ( Direction.NONE );
						futureMoves.push ( Direction.LEFT );
						futureMoves.push ( Direction.DOWN );
						futureMoves.push ( Direction.RIGHT );
					}
					break;
			case LEFT:
					if ( maze.moveLeft() )
					{
						pastMoves.push(  MazePanel.getOppositeDirection( move ) );
						lastMove = Direction.LEFT;
						futureMoves.push ( Direction.NONE );
						futureMoves.push ( Direction.LEFT );
						futureMoves.push ( Direction.DOWN );
						futureMoves.push ( Direction.UP );
					}
					break;
			case RIGHT: 
					if ( maze.moveRight() )
					{
						pastMoves.push(  MazePanel.getOppositeDirection( move ) );
						lastMove = Direction.RIGHT;
						futureMoves.push ( Direction.NONE );
						futureMoves.push ( Direction.DOWN );
						futureMoves.push ( Direction.RIGHT );
						futureMoves.push ( Direction.UP );
					}
					break;		
		}
		*/
	}
	
	public String getName ()
	{
		return "Memory";
	}



}


