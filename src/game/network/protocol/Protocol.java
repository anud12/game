package game.network.protocol;

import game.network.component.Session;

public abstract class Protocol 
{
	protected String header;
	
	public abstract void interpret(byte[] array, Session session);
	public abstract void onDisconnect(Session session);
	public abstract boolean isApplicable(byte[] array);
	
}
