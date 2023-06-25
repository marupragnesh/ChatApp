package ChatApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;
    Client()
    {
  /*      Link-local IPv6 Address . . . . . : fe80::5914:1f24:7c99:4b62%8
        IPv4 Address. . . . . . . . . . . : 192.168.43.107
        Subnet Mask . . . . . . . . . . . : 255.255.255.0
        Default Gateway . . . . . . . . . : 192.168.43.1
        157.32.72.110
    */
        System.out.println("Sending request to server");
        try {
            socket = new Socket("127.0.0.1",7777);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connection done");

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
                        System.out.println("Server terminated the Chat");
                        break;
                    }
                    System.out.println("Server :)  " + msg);
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
        System.out.println("This is Client");
        new Client();

    }
}
