package game.util;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.GridLayout;

import javax.swing.*;

public class WindowLog extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JScrollPane scroll;
	private JTextArea textArea;
	private GridLayout layout;
	private int inputNr;
	
	public WindowLog ()
	{
		inputNr = 0;
		
		panel = new JPanel();
		panel.setVisible(true);
		
		layout = new GridLayout(1, 1);
		
		textArea = new JTextArea();
		textArea.setVisible(true);
		textArea.setEditable(false);
		scroll = new JScrollPane(textArea);
		scroll.setAutoscrolls(true);
		
		panel.add(scroll);
		panel.setLayout(layout);
		
		this.add(panel);
		this.setVisible(true);
		this.setSize(200, 200);
	}
	
	
	public void println(String args)
	{
		textArea.insert("\n", 0);
		textArea.insert(" ", 0);
		textArea.insert(args, 0);
		textArea.insert(inputNr + ": ", 0);
		inputNr++;
	}
	public void clear()
	{
		textArea.setText("");
	}
}
