package workshop6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NetworkIO {
    //create private input/output streams 
    private InputStream is;
    private DataInputStream dis; 
    private OutputStream os; 
    private DataOutputStream dos; 

    // create public network IOs
    public NetworkIO (Socket sock) throws IOException {
        is = sock.getInputStream(); 
        dis = new DataInputStream(is); 
        os = sock.getOutputStream(); 
        dos = new DataOutputStream(os); 
    }

    // create read/write/close 
    public String read() throws IOException {
        return dis.readUTF(); 
    }

    public void write(String msg) throws IOException {
        dos.writeUTF(msg);
        dos.flush();
    }

    public void close() { 
        try {
            is.close();
            dis.close();
            os.close();
            dos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
