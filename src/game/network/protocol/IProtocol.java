package game.network.protocol;

import game.network.component.Session;

public abstract class IProtocol 
{
	protected String header;
	
	public abstract void interpret(String string, Session session);
	public abstract void onDisconnect(Session session);
	public abstract boolean isApplicable(byte[] array);
	
}
