package org.microbridge.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import android.util.Log;

/**
 * Client class
 */
public class Client
{
	
	private Socket socket;
	
	private final Server server;
	
	private final InputStream input;
	private final OutputStream output;
	
	private boolean keepAlive = true;
	
	/**
	 * Constructs a Client object
	 * @param server the server
	 * @param socket the connected socket
	 * @throws IOException if the socket cannot return input and output streams
	 */
	public Client(Server server, Socket socket) throws IOException
	{
		this.server = server;
		this.socket = socket;
		socket.setKeepAlive(true);
		
		this.input = this.socket.getInputStream();
		this.output = this.socket.getOutputStream();

		startCommunicationThread();
	}	
	
	/**
	 * Starts the communication thread
	 */
	public void startCommunicationThread()
	{
		(new Thread() {
			public void run()
			{
				while (keepAlive)
				{
					try
					{
						
						// Check for input
						if (input.available()>0)
						{
						
							int bytesRead;
							byte buf[] = new byte[input.available()];
							bytesRead = input.read(buf);
							
							if (bytesRead==-1)
								keepAlive = false;
							else
								server.receive(Client.this, buf);
						}
						
					} catch (IOException e)
					{
						keepAlive = false;
						Log.d("microbridge", "IOException: " + e);
					}
				}
				
				// Client exited, notify parent server
				server.disconnectClient(Client.this);
			}
		}).start();
	}
	
	/**
	 * Closes the socket
	 */
	public void close()
	{
		keepAlive = false;
		
		// Close the socket, will throw an IOException in the listener thread.
		try
		{
			socket.close();
		} catch (IOException e)
		{
			Log.e("microbridge", "error while closing socket", e);
		}
	}
	
	/**
	 * Sends data through the ADB
	 * @param data the data to send through ADB
	 * @throws IOException if data cannot be successfully sent
	 */
	public void send(byte[] data) throws IOException
	{
		try {
			output.write(data);
			output.flush();
		} catch (SocketException ex)
		{
			// Broken socket, disconnect
			close();
			server.disconnectClient(this);
		}
	}

	/**
	 * Sends a string through the ADB
	 * @param command the command to send
	 * @throws IOException if it cannot successfully send the data
	 */
	public void send(String command) throws IOException
	{
		send(command.getBytes());
	}

}
