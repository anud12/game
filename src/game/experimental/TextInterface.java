package game.experimental;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.util.concurrent.ExecutorService;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

import game.library.Entity;
import game.library.Pawn;
import game.library.interfaces.IWorld;
import javafx.scene.text.Font;

public class TextInterface extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public JTextField textField;
	private JPanel panel;
	private JScrollPane scroll;
	private JTextArea textArea;
	private int inputNr;
	
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
		
	}

	@Override
	public void run() {
		while(true)
		{
			textArea.setText("");
			textArea.append(">Name = " + target + "\n");
			textArea.append("->Location\n");
			textArea.append("-->X = " + target.getCenter().x + "\n");
			textArea.append("-->Y = " + target.getCenter().y + "\n");
			textArea.append("->Speed = " + target.getMovementSpeed() + "\n");
			textArea.append("->Order = " + target.getController().getFirst() + "\n");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
