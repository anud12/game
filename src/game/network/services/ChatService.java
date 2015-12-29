package game.network.services;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import game.network.component.Session;

public class ChatService extends Service
{
	protected HashMap<String, Session> users;
	protected String header;
	
	public ChatService()
	{
		users = new HashMap<String, Session>();
	}
	
	protected void process(byte[] array, Session session)
	{
		
		try 
		{
			String string = new String(array, "ASCII").trim();
			
			System.out.println("CHAT : " + string);
			
			String[] words = string.split(" ");
			String header = words[0];
			
			switch(header)
			{
				case"LOGIN":
				{
					if(words.length > 1)
					{
						String name = words[1];
						if(!users.containsKey(name))
						{
							users.put(words[1], session);
							session.write("Welcome\n");
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
					if(words.length < 2)
					{
						return;
					}
									
					Session target = users.get(words[1]);
															
					target.write(session.getAddress() + ": " + words[2]);
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
	protected byte[] setHead() {
		// TODO Auto-generated method stub
		return "CHAT".getBytes();
	}
}