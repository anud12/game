package game.experimental;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import game.library.attribute.AttributeSelector;
import game.library.entity.Entity;
import game.library.entity.removalCondition.RemovalCondition;
import game.library.inventory.item.Item;
import game.library.world.IWorld;

import javax.swing.BoxLayout;

public class TextInterface extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public JTextField textField;
	private JPanel panel;
	private JScrollPane scroll;
	private JTextArea textArea;
	private int inputNr;
	private StringBuffer buffer;
	
	private Entity target;
	
	private ExecutorService executor;
	
	public TextInterface (Entity target, ExecutorService executor)
	{
		this.target = target;
		this.executor = executor;
		
		inputNr = 0;
		
		panel = new JPanel();
		panel.setVisible(true);
		
		this.setLayout(new FlowLayout());
		
		textArea = new JTextArea();
		textArea.setVisible(true);
		textArea.setEditable(false);
		textArea.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 12));
		scroll = new JScrollPane(textArea);
		scroll.setAutoscrolls(true);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(1, 1));
		textField = new JTextField();
		inputPanel.add(textField);
		
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.add(scroll);
		panel.add(inputPanel);
		panel.setLayout(layout);
		
		this.add(panel);
		this.setVisible(true);
		textField.addKeyListener(new InputReader(textField, target, target.getWorld(), executor));
		
		buffer = new StringBuffer();
		
	}

	@Override
	public void run() 
	{
		IWorld world = target.getWorld();
		
		
		while(true)
		{
			buffer = buffer.delete(0, buffer.length());
			
			buffer.append("Name : ");
			buffer.append(target.get(AttributeSelector.ID()));
			buffer.append("\n");
			buffer.append("\n");
			
			buffer.append("Alive : ");
			buffer.append(target.isAlive());
			buffer.append("\n");
			buffer.append("\n");
			
			buffer.append("Location :\n");
			
			buffer.append("	X : ");
			buffer.append(Float.toString(target.getCenter().x));
			buffer.append("\n");
			
			buffer.append("	Y : ");
			buffer.append(Float.toString(target.getCenter().y));
			buffer.append("\n");
			buffer.append("\n");
			
			buffer.append("Speed : ");
			buffer.append(target.get(AttributeSelector.movementSpeed()).toString());
			buffer.append("\n");
			
			buffer.append("\n");
			
			buffer.append("Inventory :");
			buffer.append("\n");
			buffer.append("	type : ");
			buffer.append(target.getInventory());
			buffer.append("\n");
			
			Iterator<Item> iter = world.getItemList().iterator();
			while(iter.hasNext())
			{
				Item item = iter.next();
				
				buffer.append("	");
				buffer.append(item.getName());
				buffer.append(" : ");
				buffer.append(target.getInventory().getQuantity(item));
				buffer.append("\n");
			}
			buffer.append("\n");
			
			buffer.append("Removal conditions :");
			buffer.append("\n");
			Iterator<RemovalCondition> iterator = target.getRemovalChecker().getConditionList().iterator();
			while(iterator.hasNext())
			{
				buffer.append("	");
				buffer.append(iterator.next().toString());
				buffer.append("\n");
			}
			
			buffer.append("\n");
			buffer.append("Behaviour : ");
			buffer.append(target.getController().getBehaviour().toString());
			buffer.append("\n");
			buffer.append("\n");
			
			buffer.append("Order : ");
			if(target.getController().getFirst() == null)
			{
				buffer.append("none");
			}
			else
			{
				buffer.append(target.getController().getFirst().toString());
			}
			buffer.append("\n");
			buffer.append("\n");
			
			buffer.append("Order size : ");
			buffer.append(Integer.toString(target.getController().size()));
			buffer.append("\n");
			buffer.append("\n");
			
			
			textArea.setText(buffer.toString());
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
