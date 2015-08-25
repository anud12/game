package util;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import java.awt.GridLayout;

import javax.swing.*;

public class WindowLog extends JFrame 
{
	private JPanel panel;
	private JScrollPane scroll;
	private JTextArea textArea;
	private GridLayout layout;
	
	public WindowLog ()
	{
		panel = new JPanel();
		panel.setVisible(true);
		
		layout = new GridLayout(1, 1);
		
		textArea = new JTextArea();
		textArea.setVisible(true);
		textArea.setEditable(false);
		scroll = new JScrollPane(textArea);
		
		panel.add(scroll);
		panel.setLayout(layout);
		panel.setAutoscrolls(true);
		
		this.add(panel);
		this.setVisible(true);
		this.setSize(300, 300);
	}
	
	
	public void println(String args)
	{
		textArea.insert("\n", 0);
		textArea.insert(args, 0);
	}
	public void clear()
	{
		textArea.setText("");
	}
}
