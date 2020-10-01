package mazewalker;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import maze.MazePanel;

/**
 * This class represents a "walker." This is a randomized very simplistic
 * AI that attempts to find its way out of a maze by randomly choosing directions
 * until it finds an outside edge.
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public abstract class MazeWalker 
{
	
	/**
	 * Attempts to solve the maze by making what the walker perceives as the best
	 * next move towards the goal (finding an exit). This move may not actually be the
	 * best possible move, only the one perceived to be the best based upon the
	 * "intelligence" of the walker.  
	 * @param maze: a MazePanel object
	 */
	public abstract void Solve ( MazePanel maze );
	
	/**
	 * Produces the name of the walker.
	 * @return name: a string
	 */
	public abstract String getName ();

}
