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
//import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class WorkerThreadServer {

    public static int SERVICE_PORT = 9898;
    public static int MAX_SIZE = 2;


    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(MAX_SIZE);

//        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Orders-%d").setDaemon(true).build();
////        final ExecutorService executorService = Executors.newFixedThreadPool(10, threadFactory);
//        ExecutorService executorService2 = Executors.newFixedThreadPool(MAX_SIZE);
//        final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);

//        ListeningExecutorService producerExecutorService = MoreExecutors.listeningDecorator(newFixedThreadPoolWithQueueSize(5, 20));
//        ListeningExecutorService consumerExecutorService = MoreExecutors.listeningDecorator(newFixedThreadPoolWithQueueSize(5, 20));



        System.out.println("The worker thread server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(SERVICE_PORT);
        try {
            while (true) {
//                new Capitalizer(listener.accept(), clientNumber++).start();
                executorService.execute(new Capitalizer(listener.accept(),clientNumber++));

//                executorService = new ThreadPoolExecutor(n, n,0L, TimeUnit.MILLISECONDS,
//                        queue);

            }
        } finally {
//            listener.close();
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

        //TODO Simple app server with a thread queue on Monday. Thread pool can be configured min & max. If exceeded can queue. but cannot infinitely queue.build client file. Shell script. Even for client side. Pass min max as parameters.
        //TODO Client shell script number of clients. Put logs on how many clients waiting.
        //TODO Gracefully handle multiple clients.

    }

}
