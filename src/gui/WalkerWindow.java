package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This is a program that will draw a graphic depiction of an avatar (mazewalker) attempting to
 * find its way out of the maze. The size of the maze is determined by user input.
 * 
 * @author Ken Loomis (https://github.com/kjloomis3)
 */
public class WalkerWindow 
{

	/**
	 * Sets up the user interface for the program.
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame window = new JFrame ( );
		window.setTitle ( "Maze Walker" );
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		int size = 13;
		do 
		{
			String strSize = JOptionPane.showInputDialog( "Please enter the size of the maze as an odd integer: ");
			size = Integer.parseInt( strSize );
		} while ( size % 2 == 0 );
		
		UserInterface gui = new UserInterface ( size );
		//window.setPreferredSize( new Dimension( gui.getWidth( ), gui.getHeight( ) ) );
	
		window.add( gui );
		window.pack();
		
		window.setResizable( false );
		window.setVisible( true );
		
	}

}
