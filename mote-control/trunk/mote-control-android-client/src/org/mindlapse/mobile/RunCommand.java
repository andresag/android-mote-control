package org.mindlapse.mobile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class RunCommand {

public static int PORT = 34343;
	

	
	public void start(String ip, String command)
	{
		System.out.println("Starting client");
	
		try {
			
		InetAddress inet = InetAddress.getByName(ip);
		
		
		Socket socket = new Socket(inet,PORT);
		PrintWriter out =  new PrintWriter(socket.getOutputStream(),true);
	    BufferedReader in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));

	    
		System.out.println("talking to server");
	    String fromServer;
	    

	    while ((fromServer = in.readLine()) != null) 
	    {
	          System.out.println("Server: " + fromServer);
	          if (fromServer.equals("Bye"))
	              break;
		    
	          out.println(command);
	    }

	        out.close();
	        in.close();

	        socket.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
