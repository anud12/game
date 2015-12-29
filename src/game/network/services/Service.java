package game.network.services;

import game.network.component.Session;

public abstract class Service 
{
	// Abstract //
		
	protected abstract void process(byte[] array, Session session);
	public abstract void onDisconnect(Session session);
	protected abstract byte[] setHead();
	
	// Implemented //
	
	protected byte[] header;
	
	protected Service()
	{
		//set the header
		header = this.setHead();
	}
	
	public final void interpret(byte[] array, Session session)
	{
		process(this.removeHeader(array), session);
	}
	//Check if the header matches
	public final boolean isApplicable(byte[] array)
	{
		
		if(header == null)
		{
			return false;
		}
		if(array.length < header.length)
		{
			return false;
		}
		for(int i = 0 ; i < header.length ; i++)
		{
			if (array[i] != header[i])
			{
				return false;
			}
		}
		return true;
	}
	//Remove just the header part of the byte array
	protected final byte[] removeHeader (byte[] array)
	{
		byte[] returnValue = new byte[array.length - header.length];
		
		for(int i = header.length ; i < array.length ; i++)
		{
			returnValue[i - header.length] = array[i];
		}
		
		return returnValue;
	}
	
}
