package game.network.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

public class ClientListener implements Runnable
{
	protected Socket socket;
	protected InputStream in;
	
	public ClientListener(Socket socket)
	{
		this.socket = socket;
		try 
		{
			in = socket.getInputStream();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() 
	{
		try 
		{
			while(true)
			{
				
				String text = null;
				text = new String(read(), "ASCII");
				
				synchronized(System.out)
				{
					System.out.print(text);
				}
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected synchronized byte[] read() throws IOException
	{
		LinkedList<Byte> message = new LinkedList<Byte>();
		int readValue;
		boolean loop = false;
		do
		{
			loop = false;
			readValue = in.read();
			
			if(readValue != 0 && readValue != -1)
			{
				loop = true;
				message.add((byte)(readValue));
				
			}
			
		}
		while(loop);
		
		byte[] returnValue = new byte[message.size()]; 
		
		Iterator<Byte> iterator = message.iterator();
		int i = 0;
		while(iterator.hasNext())
		{
			returnValue[i] = iterator.next();
			i++;
		}
		return returnValue;
	}

}
