package ClientServer;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.InputMismatchException;

/**
 * Created by methma on 7/31/17.
 */
public class RequestProcessorThread implements Runnable {

    private Socket clientSocket;
    public RequestProcessorThread (Socket socket){
        clientSocket = socket;
    }

    public void run() {
        System.out.println(this.toString()+" : Thread started. Processing client "+ clientSocket);
        Date today = new Date();
        PrintWriter out = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            out.println(today);
            /*wait till client sends some data back to the server*/
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String receivedData = bufferedReader.readLine();
            System.out.println("received from client :"+ receivedData);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.toString()+ " : Thread exiting... ");

    }
}
