package game.network.protocol;

import java.io.UnsupportedEncodingException;

import game.geom.classes.PointF;
import game.library.pawn.Pawn;
import game.library.pawn.order.Move;
import game.library.pawn.order.None;
import game.library.world.IWorld;
import game.network.component.Session;

public class EngineProtocol extends Protocol{

	protected IWorld world;
	public EngineProtocol(IWorld world)
	{
		super();
		this.world = world;
	}
	@Override
	protected void process(byte[] array, Session session) 
	{
		try 
		{
			String[] words = new String(array, "ASCII").trim().split(" ");
			
			switch(words[0])
			{
				case"MOVE":
				{
					int x = Integer.parseInt(words[2]);
					int y = Integer.parseInt(words[3]);
					PointF point = new PointF(x , y);
					String id = words[1];
					
					Pawn pawn = (Pawn)world.getEntityByID(id);
					if(pawn == null)
					{
						return;
					}
					pawn.getController().setOrder(new Move(pawn, point));
					break;
				}
				case"STOP":
				{
					String id = words[1];
					
					Pawn pawn = (Pawn)world.getEntityByID(id);

					if(pawn == null)
					{
						return;
					}
					
					pawn.getController().setOrder(new None());
					break;
				}
			}
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDisconnect(Session session) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected byte[] setHead() {
		// TODO Auto-generated method stub
		return "ENGINE".getBytes();
	}

}
