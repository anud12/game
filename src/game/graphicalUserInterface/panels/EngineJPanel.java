package game.graphicalUserInterface.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.engine.Engine;

@SuppressWarnings("serial")
public class EngineJPanel extends JPanel implements Runnable
{
	protected Engine engine;
	protected PropertyLine deltaTime;
	protected PropertyLine executeAddBufferSize;
	protected PropertyLine executeRemoveBufferSize;
	protected PropertyLine executeCurrentThreadNumber;
	protected PropertyLine executeActionsSize;
	
	protected PropertyLine planAddBufferSize;
	protected PropertyLine planRemoveBufferSize;
	protected PropertyLine planCurrentThreadNumber;
	protected PropertyLine planActionsSize;
	
	
	public EngineJPanel(Engine engine)
	{
		this.engine = engine;
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		
		this.setLayout(layout);
		
		deltaTime = new PropertyLine("Delta Time :");
				
		
		JLabel executeLabel = new JLabel("Execute Manager");
		
		executeAddBufferSize = new PropertyLine("Add Buffer :");
		executeRemoveBufferSize = new PropertyLine("Remove Buffer :");
		executeCurrentThreadNumber = new PropertyLine("Current threads :");
		executeActionsSize = new PropertyLine("Actions Size :");
		
		this.setBorder(BorderFactory.createEtchedBorder());
		
		
		JLabel planLabel = new JLabel("Plan Manager");
				
		this.setLayout(layout);
		
		deltaTime = new PropertyLine("Delta Time :");
		
		planAddBufferSize = new PropertyLine("Add Buffer :");
		planRemoveBufferSize = new PropertyLine("Remove Buffer :");
		planCurrentThreadNumber = new PropertyLine("Current threads :");
		planActionsSize = new PropertyLine("Actions Size :");
		
		
		this.add(deltaTime);

		this.add(planLabel);
		this.add(planActionsSize);
		this.add(planCurrentThreadNumber);
		this.add(planAddBufferSize);
		this.add(planRemoveBufferSize);
		
		this.add(executeLabel);
		this.add(executeActionsSize);
		this.add(executeCurrentThreadNumber);
		this.add(executeAddBufferSize);
		this.add(executeRemoveBufferSize);
		
		this.add(new JPanel());
	}
	
	public void update()
	{
		deltaTime.setValue(engine.getDeltaTime() + " ms");
		
		executeActionsSize.setValue(engine.getActionsSizeExecute() + "");
		executeAddBufferSize.setValue(engine.getAddBufferSizeExecute()+ "");
		executeCurrentThreadNumber.setValue(engine.getCurrentThreadNumberExecute()+ "");
		executeRemoveBufferSize.setValue(engine.getRemoveBufferSizeExecute()+ "");
		
		planActionsSize.setValue(engine.getActionsSizePlan() + "");
		planAddBufferSize.setValue(engine.getAddBufferSizePlan()+ "");
		planCurrentThreadNumber.setValue(engine.getCurrentThreadNumberPlan()+ "");
		planRemoveBufferSize.setValue(engine.getRemoveBufferSizePlan()+ "");
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

@SuppressWarnings("serial")
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