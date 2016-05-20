package game.graphicalUserInterface;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = -6607769263176642079L;

	public MainFrame()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		this.setVisible(true);
		this.setSize(new Dimension(500,500));
		this.repaint();
		
	}

}
