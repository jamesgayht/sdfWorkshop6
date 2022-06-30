package workshop6.server;

import java.io.IOException;
import java.net.Socket;

public class CookieClientHandler implements Runnable{

    //create private socket & pricate cookiefile 
    private Socket sock; 
    private String cookieFile; 

    // create new public class for cookieclienthandler
    public CookieClientHandler(Socket s, String cookieFile) {
        this.sock = s; 
        this.cookieFile = cookieFile; 
    }

    //override - i think its because u need to create a new networkIO each time for new connections 
    @Override
    public void run() {
        System.out.println("Starting a client thread ... ");
        NetworkIO netIO = null; 

        try {
            netIO = new NetworkIO(sock); 
            String req = ""; 
            String randomCookieResp = ""; 
            while(true) { 
                req = netIO.read(); 
                System.out.printf("[client] %s\n", req);
                if(req.trim().equals("exit")) {
                    System.out.printf("file -> %s\n", this.cookieFile);
                randomCookieResp = Cookie.getRandomCookie(this.cookieFile); 
                netIO.write("cookie-text, " + randomCookieResp); 
                break;
                } else { 
                    netIO.write("error, invalid command ");
                    break; 
                }
            }
            netIO.close();
            sock.close();

            System.out.println("Exiting the thread. ");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }  
    }
}

    

