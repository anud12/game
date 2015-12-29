package game.network.services;

import java.io.UnsupportedEncodingException;

import game.engine.Engine;
import game.network.component.Session;

public class EngineControlService extends Service
{
	private Engine engine;
	public EngineControlService(Engine engine)
	{
		super();
		this.engine = engine;
	}

	@Override
	protected void process(byte[] array, Session session)
	{
		try 
		{
			String[] words = new String(array, "ASCII").trim().split(" ");
						
			switch(words[0])
			{
				case"RESUME":
				{
					engine.pause(false);
					break;
				}
				case"STOP":
				{					
					engine.pause(true);
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
	public void onDisconnect(Session session)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected byte[] setHead()
	{
		return "ENGINE".getBytes();
	}

}
