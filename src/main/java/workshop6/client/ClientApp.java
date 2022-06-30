package workshop6.client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientApp {
    //create a main method for client side 
    public static void main(String[] args) {
        System.out.println("Cookie Client");
        //take in the argument from the server side args[0] because 
        // java -cp fortunecookie.jar fc.Client locahost:12345
        // we split by ":"
        String[] arr = args[0].split(":");
        boolean stop = false; 
        InputStream is = null;
        DataInputStream dis = null; 
        OutputStream os = null; 
        DataOutputStream dos = null;
        Socket sock = null; 
        
        // create try catch block to receive input from server side 
        try {
            //set up console to prompt for command 
            Console cons = System.console(); 
            while(!stop) {
                String response = null; 
                String input = cons.readLine("Send command to server > "); 
                // socket connects to the <host>:<port> at arr[0] and arr[1]
                sock = new Socket(arr[0], Integer.parseInt(arr[1]));
                //setup IO network 
                is = sock.getInputStream(); 
                dis = new DataInputStream(is); 
                os = sock.getOutputStream(); 
                dos = new DataOutputStream(os); 

                if(input.equals("exit")) {
                    stop = true; 
                    dos.writeUTF(input);
                    //remember to flush to allow subsequent commands to come in
                    dos.flush();
                }
                
                if(!stop) {
                    try {
                        response = dis.readUTF(); 
                    } catch (EOFException e) {
                        // suppress if the reading is called twice 
                    }
                }

                if(response != null) {
                    if(response.contains("cookie-text") || response.contains("error")) {
                        System.out.println(response);
                        String[] cookieValue = response.split(","); 
                        if(response.contains("error")) 
                            System.out.printf("Error from server >> %s\n", cookieValue[1]);
                        if(response.contains("cookie-text")) 
                            System.out.printf("Cookies from server >> %s\n", cookieValue[1]);
                    }
                }
            }
            //close all required 
            System.out.println("Closing ... ");
            is.close();
            os.close();
            sock.close();

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
