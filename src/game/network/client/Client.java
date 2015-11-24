package game.network.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Client 
{
	public static void main(String args[]) throws IOException, InterruptedException
	{
		Socket socket = null;
		
		{
			System.out.print("Arument is: ");
			System.out.println(args[0]);
			socket = new Socket(args[0], 35000);
		}
		
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		
		BufferedReader textResponse = new BufferedReader(new InputStreamReader(in, "ASCII"));
		
		BufferedWriter textOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "ASCII"));
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		executor.execute(new ClientListener(socket));
		
		while(true)
		{				
			String text = null;
			synchronized(System.out)
			{
				System.out.print("");
			}
			BufferedReader consoleReader = new BufferedReader( new InputStreamReader(System.in));
			
			text = consoleReader.readLine();
			
			textOutput.write(text);
			
			textOutput.flush();
			out.write(0);
		}
	}
}
