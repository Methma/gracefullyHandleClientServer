package ClientServer;

/**
 * Created by methma on 7/25/17.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class WorkerThreadServer {

    public static int SERVICE_PORT = 9898;
    public static int MAX_SIZE = 3;
    public static int MIN_SIZE = 2;
    public static int QUEUE_SIZE = 2;


    public static void main(String[] args) throws Exception {

        ExecutorService executorService;
        //Create a bounded queue
        final BlockingQueue<Runnable> queue = new ArrayBlockingQueue(QUEUE_SIZE);

        //Pass created queue with min max thread pool sizes to executor service
        executorService = new ThreadPoolExecutor(MIN_SIZE, MAX_SIZE,0L, TimeUnit.MILLISECONDS, queue);


        System.out.println("The worker thread server is running.");
        int clientNumber = 1;
        ServerSocket listener = new ServerSocket(SERVICE_PORT);
        try {
            while (true) {

                executorService.execute(new Capitalizer(listener.accept(),clientNumber++));

            }
        } finally {
            executorService.shutdown();
        }
    }


    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);


        }


        public void run() {
            try {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hello, you are client #" + clientNumber + ".");
                out.println("Enter a line with only a period to quit\n");

                // Get messages from the client, line by line; return them
                // capitalized
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }

        private void log(String message) {
            System.out.println(message);
        }

    }

}
