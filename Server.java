import java.net.*;
import java.io.*;

public class Server {

    ServerSocket server;// this is server socket
    Socket socket;// this will store socket objecr recieved from client

    BufferedReader br;
    // socket returned ka input data isme stored
    PrintWriter out;
    // output stream bhjna h vapas client ko...data stored will be in this
    // client m jaakr write hoga

    // constructor
    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting...");
            socket = server.accept(); // client se returned object idhr socket mai store hoga

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());
            // idhr se client koo data send karenge

            startReading();
            startWriting();
            // reading means client se dta lena
            // writing means client ko data send krna

            // reading writing saath m krna hai.
            // so use concept of multithreading

        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            // System.out.println("connection closed...");
        }
    }

    private void startReading() {
        // thread - > read krke deta rhega
        Runnable r1 = () -> {
            System.out.println(" reader started....");

            try {

                while (!socket.isClosed()) {
                    String msg;

                    msg = br.readLine();

                    if (msg.equals("exits")) {
                        System.out.println("Client Terminated the chat");
                        socket.close();
                        break;
                    }

                    System.out.println("Client : " + msg);
                }
            } catch (Exception e) {
                // TODO: handle exception
                // e.printStackTrace();
                System.out.println("connection closed...");
            }

        };
        // thread r1 start here
        new Thread(r1).start();
    }

    private void startWriting() {
        // thread -> user se data lega and client ko send krega

        Runnable r2 = () -> {
            System.out.println(" writer started...");

            try {
                while (!socket.isClosed()) {

                    // first data console se input krana hoga
                    // msg input krna hoga ...then usko send krna h

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();

                    out.println(content);// imput msg from server now send to client
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("connection closed...");
                // e.printStackTrace();
            }

        };
        // thread r2 start here
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println(" this is server");
        new Server();
    }
}
