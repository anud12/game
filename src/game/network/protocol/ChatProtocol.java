package game.network.protocol;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import game.network.Server;
import game.network.component.Session;

public class ChatProtocol extends IProtocol
{
	protected HashMap<String, Session> users;
	
	public ChatProtocol()
	{
		users = new HashMap<String, Session>();
	}
	
	public void interpret(String string, Session session)
	{
		String header;
		
		String[] words = string.split(" ");
					
		header = words[1];
		
		
		switch(header)
		{
			case"LOGIN":
			{
				if(words.length > 2)
				{
					String name = words[2];
					
					users.put(words[2], session);
					
					session.write("Welcome " + name);						
				}
				break;
			}
			case"LIST_ALL":
			{
				for(Iterator<String> iterator = users.keySet().iterator() ; iterator.hasNext();)
				{
					StringBuilder response = new StringBuilder();
					String name = iterator.next();
					response.append(name);
					response.append(" - ");
					response.append(users.get(name).getAddress());
					
					session.write(response.toString());
				}
				break;
			}
			case"WHISPER":
			{
				if(words.length < 3)
				{
					return;
				}
								
				Session target = users.get(words[2]);
														
				target.write(session.getAddress() + words[3]);
				break;
			}
		}
	}

	@Override
	public void onDisconnect(Session session) {
		users.values().remove(session);
		
	}

	@Override
	public boolean isApplicable(byte[] array) {
		
		String[] words = null;
		try {
			words = (new String(array, "ASCII")).split(" ");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(words[0]);
		return ( words[0].equals("CHAT"));
	}
}