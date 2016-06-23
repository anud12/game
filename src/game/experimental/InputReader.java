package game.experimental;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;

import javax.swing.JTextField;

import game.geom.classes.PointF;
import game.library.entity.Entity;
import game.library.entity.behaviour.Harvest;
import game.library.entity.behaviour.Idle;
import game.library.entity.order.None;
import game.library.world.IWorld;

public class InputReader extends KeyAdapter
{
	JTextField field;
	Entity entity;
	IWorld world;
	ExecutorService executor;
	
	
	public InputReader (JTextField field, Entity entity, IWorld world, ExecutorService executor)
	{
		this.field = field;
		this.entity = entity;
		this.world = world;
		
		this.executor = executor;
	}
	 @Override
     public void keyPressed(KeyEvent event) 
	 {
		 
		 if(event.getKeyCode() == KeyEvent.VK_ENTER)
		 {
			 
			 String text[] = this.field.getText().split(" ");
			 switch(text[0])
				{
					case "stop":
					{
						entity.getController().getOrderInterface().stop();
						entity.getController().setBehaviour(new Idle());
						field.setText("");
						break;
					}
					case "move":
					{
						if(text.length > 2)
						{
							float x = Float.parseFloat(text[1]);
							float y = Float.parseFloat(text[2]);
							
							PointF point2d = new PointF(x, y);
							entity.getController().getOrderInterface().move(point2d);
							field.setText("");
						}
						break;
						
					}
					case "harvest":
					{
						entity.getController().setBehaviour(new Harvest(entity));
						field.setText("");
						break;
					}
				}
		 }
     }
}
