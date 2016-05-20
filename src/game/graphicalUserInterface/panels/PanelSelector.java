package game.graphicalUserInterface.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;

public class PanelSelector extends JPanel implements ListSelectionListener
{
	private static final long serialVersionUID = 6782650573617991709L;
	protected JList<String> list;
	protected JPanel displayPanel;

	protected HashMap<String , JPanel> panels;
	
	public PanelSelector()
	{
		
		list = new JList<String>();
		displayPanel = new JPanel();
		displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
		panels = new HashMap<>();
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JScrollPane scrollPanel = new JScrollPane(list);
		
		scrollPanel.setMinimumSize(new Dimension(500, 9999));
		scrollPanel.setMaximumSize(new Dimension(500, 9999));
		
		this.add(scrollPanel);
		this.add(new JScrollPane(displayPanel));
		
		
		this.setVisible(true);
		
		list.addListSelectionListener(this);
		
		this.repaint();
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		
		this.displayPanel.removeAll();;
		this.setVisible(false);
		
		List<String> keyList = ((JList<String>) list).getSelectedValuesList();
				
		Iterator<String> iter = keyList.iterator();
		
		
		while(iter.hasNext())
		{
			String str = iter.next();
			displayPanel.add(panels.get(str));
		}		
		if(keyList.size() > 3)
			displayPanel.setLayout(new GridLayout(keyList.size() / 2 + 1, 2));
		displayPanel.repaint();
		this.setVisible(true);
	}
	
	public void addPanel(String name, JPanel panel)
	{
		if(panels.containsKey(name))
			panels.remove(name);
		
		panels.put(name, panel);
		
		list.setListData(panels.keySet().toArray(new String[panels.keySet().size()]));
	
	}

}
