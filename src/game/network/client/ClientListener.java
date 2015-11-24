package game.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientListener implements Runnable
{
	protected Socket socket;
	
	public ClientListener(Socket socket)
	{
		this.socket = socket;
	}
	@Override
	public void run() 
	{
		try 
		{
			InputStream in;
			in = socket.getInputStream();

			BufferedReader textResponse = new BufferedReader(new InputStreamReader(in, "ASCII"));
			while(true)
			{				
				String text = null;
				text = textResponse.readLine();
				synchronized(System.out)
				{
					System.out.print(">>");
					System.out.println(text);					
				}
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
