package edu.uiowa.icts.sandbox.mote_control_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 
 * 
 * 
 */
public class MoteServer {
	
	private static int PORT=34343; 
	private static String os=null;
    public static void main( String[] args )
    {
    	
    	os = System.getProperty("os.name");
    	System.out.println("OS:"+os);
    	MoteServer server = new MoteServer();
    	server.start();
    	
    }
    
    public void start()
    {
    	System.out.println("Starting Server on port:"+PORT);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Could not listen on port:"+PORT);
            System.exit(1);
        }
        
        
        /*
         * Client acception loops
         * 
         */
        boolean run=true;
        while(run)
        {
        Socket clientSocket = null;
        try {
        	//while(true)
        	clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        
        try {
        	
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(	new InputStreamReader(clientSocket.getInputStream()));
	
		String inputLine="";
		String outputLine="";
		
		//Start communication by saying hello
        out.println("HELLO");
        boolean closed = false;
	
        
        /*
         * 
         * Connection loops
         */
		while ((inputLine = in.readLine()) != null) 
		{
			System.out.println("Input:"+inputLine);
			
			
			/*
			 * Close connection and run sleep command
			 */
			if("SLEEP".equals(inputLine))
			{
				 outputLine = "Bye";
				 out.println(outputLine);
				 if(!closed)
				 {
					 out.close();
					 clientSocket.close();
				 }
			
					  StringBuffer commandResult = new StringBuffer();
					InputStream is= null;
					int readInt;
					 Process p =null ;
					 
						if("Linux".equals(os))
						{
							p = Runtime.getRuntime().exec(new String[] { "/usr/bin/acpitool", "-s" });
						}
						else
						{
							p = Runtime.getRuntime().exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");
						}
							
					 int returnVal = p.waitFor();
					              if (returnVal == 0)               
					                  is = p.getInputStream();
					              else
					                  is = p.getErrorStream();
					             
					             while ((readInt = is.read()) != -1)
					                 commandResult.append((char)readInt);     
			
					             
				 System.out.println("Command SLEEP output = \n" + commandResult.toString());
				 System.out.println("Command SLEEP exit status = " + returnVal); 
				 is.close();
				System.out.println("Sleeping..");
				
			}
			else
			{
			outputLine = "Bye";
            out.println(outputLine);
			}
            
            break;
		}
		 if(!closed)
		 {
			 out.close();
			 clientSocket.close();
		 }
       
		
		
	
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
        }
     	try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




    }
}
