package game.graphicalUserInterface.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.engine.Engine;

public class EngineJPanel extends JPanel implements Runnable
{
	protected Engine engine;
	protected PropertyLine deltaTime;
	protected PropertyLine addBufferSize;
	protected PropertyLine removeBufferSize;
	protected PropertyLine currentThreadNumber;
	protected PropertyLine actionsSize;
	
	
	public EngineJPanel(Engine engine)
	{
		this.engine = engine;
		
		JLabel test = new JLabel(engine.toString());
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		
		this.setLayout(layout);
		
		deltaTime = new PropertyLine("Delta Time :");
		
		addBufferSize = new PropertyLine("Add Buffer :");
		removeBufferSize = new PropertyLine("Remove Buffer :");
		currentThreadNumber = new PropertyLine("Current threads :");
		actionsSize = new PropertyLine("Actions Size :");
		
		this.setBorder(BorderFactory.createEtchedBorder());
		
		this.add(test);
		this.add(deltaTime);
		this.add(actionsSize);
		this.add(currentThreadNumber);
		this.add(addBufferSize);
		this.add(removeBufferSize);
		this.add(new JPanel());
		
	}
	
	public void update()
	{
		deltaTime.setValue(engine.getDeltaTime() + " ms");
		actionsSize.setValue(engine.getActionsSize() + "");
		addBufferSize.setValue(engine.getAddBufferSize()+ "");
		currentThreadNumber.setValue(engine.getCurrentThreadNumber()+ "");
		removeBufferSize.setValue(engine.getRemoveBufferSize()+ "");
	}

	@Override
	public void run()
	{
		while(true)
		{
			this.update();
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

class PropertyLine extends JPanel
{
	protected JLabel nameLabel;
	protected JTextField value;
	protected JPanel panel;
	
	public PropertyLine(String name)
	{
		panel = new JPanel();
		
		panel.setMaximumSize(new Dimension(500, 40));
		
		nameLabel = new JLabel(name);
		nameLabel.setFont(Font.getFont(Font.MONOSPACED));
				
		panel.setLayout(new GridLayout(1,2));
		
		
		value = new JTextField();
		value.setEditable(false);
		value.setMinimumSize(new Dimension(300, 40));
		value.setBackground(Color.white);
		
		panel.add(this.nameLabel);
		panel.add(value);
		
		panel.setAlignmentX(CENTER_ALIGNMENT);
		panel.setAlignmentY(CENTER_ALIGNMENT);
		
		this.add(panel);
		
		this.setMaximumSize(new Dimension(500, 60));
	}
	
	public void setValue(String value)
	{
		this.value.setText(value);
	}
	
	
}