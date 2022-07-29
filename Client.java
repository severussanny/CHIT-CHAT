import java.io.BufferedReader;
import java.net.Socket;
import java.io.*;


public class Client {

    Socket socket;
    BufferedReader br;
    // socket returned ka input data isme stored
    PrintWriter out;
    // output stream bhjna h vapas server ko...data stored will be in this
    // server m jaakr write hoga

    public Client(){
       try {
        socket= new Socket("127.0.0.1", 7777);
        // server ka ip address nd host idhr denge for connection request

        System.out.println("connection done...");
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out = new PrintWriter(socket.getOutputStream());
        // idhr se server koo data send karenge

        startReading();
        startWriting();
        // reading means server se dta lena
        // writing means server ko data send krna

        // reading writing saath m krna hai.
        // so use concept of multithreading
            
       } catch (Exception e) {
        //TODO: handle exception
        // e.printStackTrace();
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
                        System.out.println("Server Terminated the chat");
                        socket.close();
                        break;
                    }

                    System.out.println("Server : " + msg);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println("connection closed...");
        }
        };
        // thread r1 start here
        new Thread(r1).start();
    }

    private void startWriting() {
        // thread -> user se data lega and server ko send krega

        Runnable r2 = () -> {
            System.out.println(" writer started...");

            try {
            while (!socket.isClosed()) {
                    // first data console se input krana hoga
                    // msg input krna hoga ...then usko send krna h

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();

                    out.println(content);// imput msg from client now send to server
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
        System.out.println("this is client side");
        new Client();
    }
}
