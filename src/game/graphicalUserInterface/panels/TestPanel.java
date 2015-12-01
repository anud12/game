package game.graphicalUserInterface.panels;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestPanel extends JPanel
{

	public TestPanel(Color color)
	{
		this.setBackground(color);
				
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.setSize(500, 500);
		this.setVisible(true);
	}

}
