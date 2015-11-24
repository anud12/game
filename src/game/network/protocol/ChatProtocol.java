package game.network.protocol;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import game.network.Server;
import game.network.component.Session;

public class ChatProtocol extends Protocol
{
	protected HashMap<String, Session> users;
	protected String header;
	
	public ChatProtocol()
	{
		header = "CHAT";
		users = new HashMap<String, Session>();
	}
	
	public void interpret(byte[] array, Session session)
	{
		
		try 
		{
			String string = new String(array, "ASCII");
			String[] words = string.split(" ");
			String header = words[1];
			
			switch(header)
			{
				case"LOGIN":
				{
					if(words.length > 2)
					{
						String name = words[2];
						if(!users.containsKey(name))
						{
							users.put(words[2], session);
							session.write("Welcome");
						}						
					}
					break;
				}
				case"LIST_ALL":
				{
					StringBuilder response = new StringBuilder();
					response.append("Currently logged in :\n");
					int i = 0;
					for(Iterator<String> iterator = users.keySet().iterator() ; iterator.hasNext();)
					{
						
						String name = iterator.next();
						response.append(i+1);
						response.append(") [");
						response.append(name);
						response.append("] - ");
						response.append(users.get(name).getAddress());
						
						if(iterator.hasNext())
						{
							response.append("\n");
						}
						i++;
					}
					session.write(response.toString());
					break;
				}
				case"WHISPER":
				{
					if(words.length < 3)
					{
						return;
					}
									
					Session target = users.get(words[2]);
															
					target.write(session.getAddress() + ": " + words[3]);
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
		users.values().remove(session);
		
	}

	@Override
	public boolean isApplicable(byte[] array) {
		
		String[] words = null;
		try 
		{
			words = (new String(array, "ASCII")).split(" ");
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ( words[0].equals(header));
	}
}