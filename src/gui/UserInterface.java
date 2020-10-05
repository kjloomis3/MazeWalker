package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import maze.MazePanel;
import mazewalker.BrilliantMazeWalker;
import mazewalker.GeniusMazeWalker;
import mazewalker.LeftTurnMazeWalker;
import mazewalker.MazeWalker;
import mazewalker.RandomMazeWalker;
import mazewalker.MemoryMazeWalker;
import mazewalker.SmarterMazeWalker;

/**
 * Creates the graphic user interface for the program. It includes
 * 2 buttons (reset and step) and a graphic map that represents the
 * maze. 
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public class UserInterface extends JPanel
{
	private static final String [] walkerList = { "Random", "Left-Turn", "Memory", "Smarter", "Brilliant", "Genius" };
	private MazePanel maze;
	private JComboBox<String> walkerChoice;
	private JButton reset;
	private JButton step;
	private JButton step5;
	private JButton step10;
	private MazeWalker walker;
	private boolean done;

	/**
	 * Creates  the graphic user interface by creating the 2 buttons and 
	 * creating the  underlying graphic representation of the maze. The size of
	 * the maze (the number of rooms in each row and column) is given as
	 * an argument.. 
	 * 
	 * @param size: the number of rooms in each row and column of the maze
	 */
	public UserInterface ( int size )
	{
		walker = null;
		maze = new MazePanel ( size );

		this.add( maze );
		this.setSize ( maze.getSize() );
		
		InterfaceHandler listener = new InterfaceHandler();
		this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));
		this.done = false;

		JPanel topButtonPanel = new JPanel();
		topButtonPanel.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
		topButtonPanel.setLayout( new BoxLayout(topButtonPanel, BoxLayout.LINE_AXIS));

		walkerChoice = new JComboBox<String>( walkerList );
		topButtonPanel.add(walkerChoice);
		walkerChoice.setSelectedIndex(0);
		walkerChoice.addActionListener ( listener );
		
		this.add( topButtonPanel );
		
		JPanel bottomButtonPanel = new JPanel();
		bottomButtonPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		bottomButtonPanel.setLayout( new BoxLayout(bottomButtonPanel, BoxLayout.LINE_AXIS));
		
		reset = new JButton ( "Reset" );
		bottomButtonPanel.add(reset);
		reset.setActionCommand( "RESET" );
		reset.addActionListener( listener );
		bottomButtonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		step = new JButton ( "Step" );
		bottomButtonPanel.add(step);
		step.setActionCommand( "STEP" );
		step.addActionListener( listener );
		bottomButtonPanel.add(Box.createRigidArea(new Dimension(5, 0)));

		step5 = new JButton ( "Step+5" );
		bottomButtonPanel.add(step5);
		step5.setActionCommand( "STEP+5" );
		step5.addActionListener( listener );
		bottomButtonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		
		step10 = new JButton ( "Step+10" );
		bottomButtonPanel.add(step10);
		step10.setActionCommand( "STEP+10" );
		step10.addActionListener( listener );
		
		this.add( bottomButtonPanel );

	}

	private void ResetWalker ( int choice )
	{
		switch ( walkerList[choice] )
		{
			case "Genius": walker = new GeniusMazeWalker(); break;
			case "Brilliant": walker = new BrilliantMazeWalker(); break;
			case "Smarter": walker = new SmarterMazeWalker(); break;
			case "Memory": walker = new MemoryMazeWalker(); break;
			case "Left-Turn": walker = new LeftTurnMazeWalker(); break;
			case "Random":
			default: walker = new RandomMazeWalker(); break;
		}
	}

	/**
	 * Listens for all user interaction and process the button
	 * clicks when they occur. This should move your maze walker through
	 * the maze by drawing a line to represent the movement of the walker.
	 */
	public class InterfaceHandler implements ActionListener {

		/**
		 * Processes the actions performed by the user. When the
		 * user presses a button, an action is performed. This processes
		 * all button click actions for this user interface. 
		 */
		@Override
		public void actionPerformed ( ActionEvent event ) 
		{		
			String action = event.getActionCommand();

			switch ( action )
			{
			case "RESET":
				maze.Reset();
				step.setEnabled(true);
				step5.setEnabled(true);
				step10.setEnabled(true);
				walkerChoice.setEnabled(true);
				walker = null;
				break;
			case "STEP":
				if ( walker == null )
				{
					ResetWalker ( walkerChoice.getSelectedIndex() );
					walkerChoice.setEnabled(false);
				}
				walker.Solve ( maze, false );
				break;
			case "STEP+5":
				if ( walker == null )
				{
					ResetWalker ( walkerChoice.getSelectedIndex() );
					walkerChoice.setEnabled(false);
				}
				for ( int i=0; i< 5 && !maze.foundGoal() ; i++ )
				{
					walker.Solve(maze, false);
				}
			case "STEP+10":
				if ( walker == null )
				{
					ResetWalker ( walkerChoice.getSelectedIndex() );
					walkerChoice.setEnabled(false);
				}
				for ( int i=0; i< 10 && !maze.foundGoal() ; i++ )
				{
					walker.Solve(maze, false);
				}
			}
			//System.out.println( action );

			maze.repaint();
			
			if ( maze.foundGoal() )
			{
				JOptionPane.showMessageDialog ( null, 
						"You have found your way out of the maze!" );
				done = true;
				step.setEnabled(false);
				step5.setEnabled(false);
				step10.setEnabled(false);
			}

		}
	}

	/**
	 * Paints the drawn graphic components (the maze) of the
	 * User interface.
	 */
	public void paintComponent(Graphics pen) 
	{
		Dimension dimension = this.getSize();
		pen.setColor ( Color.DARK_GRAY );
		pen.drawRect( 0, 0, dimension.width, dimension.height );
		pen.setColor ( Color.BLACK );
		maze.paintComponent( pen );
	}
}
