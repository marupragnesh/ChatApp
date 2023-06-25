package ChatApp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Server {

    ServerSocket serverSocket; // this is server side class
    Socket socket; // this is client side class

    BufferedReader br;
    PrintWriter out;
    public Server()
    {
        try {
            serverSocket =  new ServerSocket(7777); // portnumber , client calling this portnumber of many ports
            System.out.println("Ready to connection");
            System.out.println("Waiting");
            socket = serverSocket.accept(); // accept the connection of client and store in socket
            // we need object of client (which is socket) in server that's why we store in socket.

            // socket have 2 values inputstream - readdata , and outputsteram - write data




       br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       // getting data from socket inputstream in byte , convert into char using in inputsteamreader
            // take on the data to bufferreader.

       out = new PrintWriter(socket.getOutputStream());

            // stream are unidirection in java that's why we use different steam @Param getInputerStream like
            // getInputStream is like pipe which fetch the data from otherside , and outputsteam is opposite.

       startReading();
       startWriting();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startReading()
    {
        // msg coming from client to server
        Runnable r1 = ()-> {
            System.out.println("Reader Started");
            while(true)
            {
                try {
                    String msg = br.readLine();
                    if(msg.equals("exit"))
                    {
                        System.out.println("Client terminated the Chat");
                        break;
                    }
                    System.out.println("Client :) " + msg);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        new Thread(r1).start();

    }
    private void startWriting() {
        // server responce to client
        Runnable r2 = ()-> {
            System.out.println("Writer started or server started in writing method");
            while(true)
            {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                try {
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        };

        new Thread(r2).start();
    }


    public static void main(String[] args) {
        System.out.println("Server is started");
        new Server();

    }

}
