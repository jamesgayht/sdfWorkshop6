package workshop6.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp 
{
    public static void main( String[] args ) throws IOException
    {
        // set up the default port number
        int port = 3000; 
        // if user inputs a port number, fetch the number from the input 
        if(args.length > 0) { 
            port = Integer.parseInt(args[0]); 
        }
        // the file will be in the [1] position - print out the message for users with the new port number 
        String cookieFile = args[1]; 
        System.out.printf("Server App started at %s\n", port);
        //executor service 
        ExecutorService threadPool = Executors.newFixedThreadPool(2); 
        //set up new server socket 
        ServerSocket server = new ServerSocket(); 
        // set up while loop 
        while(true) {
            System.out.println("Waiting for client connection ... ");
            Socket sock = server.accept(); 
            System.out.println("Connected! ");
            CookieClientHandler thr = new CookieClientHandler(sock, cookieFile);
            threadPool.submit(thr); 
            System.out.println("Submitted to threadpool");
        }  
    }
}
