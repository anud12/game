package game.experimental;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.concurrent.ExecutorService;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import game.library.pawn.Pawn;

import javax.swing.BoxLayout;

public class TextInterface extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public JTextField textField;
	private JPanel panel;
	private JScrollPane scroll;
	private JTextArea textArea;
	private int inputNr;
	private StringBuffer buffer;
	
	private Pawn target;
	
	private ExecutorService executor;
	
	public TextInterface (Pawn target, ExecutorService executor)
	{
		this.target = target;
		this.executor = executor;
		
		inputNr = 0;
		
		panel = new JPanel();
		panel.setVisible(true);
		
		
		textArea = new JTextArea();
		textArea.setVisible(true);
		textArea.setEditable(false);
		textArea.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 12));
		scroll = new JScrollPane(textArea);
		scroll.setAutoscrolls(true);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setMaximumSize(new Dimension(99999, 150));
		inputPanel.setLayout(new GridLayout(1, 1));
		textField = new JTextField();
		inputPanel.add(textField);
		
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.add(scroll);
		panel.add(inputPanel);
		panel.setLayout(layout);
		
		this.add(panel);
		this.setVisible(true);
		this.setSize(200, 200);
		textField.addKeyListener(new InputReader(textField, target, target.getWorld(), executor));
		
		buffer = new StringBuffer();
		
	}

	@Override
	public void run() {
		while(true)
		{
			buffer = buffer.delete(0, buffer.length());
			
			buffer.append(">Name = ");
			buffer.append((target.toString()));
			buffer.append("\n");
			
			buffer.append("->Location\n");
			
			buffer.append("-->X = ");
			buffer.append(Float.toString(target.getCenter().x));
			buffer.append("\n");
			
			buffer.append("-->Y = ");
			buffer.append(Float.toString(target.getCenter().y));
			buffer.append("\n");
			
			buffer.append("->Speed = ");
			buffer.append(Float.toString(target.getMovementSpeed()));
			buffer.append("\n");
			
			buffer.append("->Order = ");
			if(target.getController().getFirst() == null)
			{
				buffer.append("none");
			}
			else
			{
				buffer.append(target.getController().getFirst().toString());
			}
			buffer.append("\n");
			
			buffer.append("->Order size = ");
			buffer.append(Integer.toString(target.getController().size()));
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
