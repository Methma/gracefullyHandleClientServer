package ClientServer;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by methma on 7/31/17.
 */
public class ThreadPooledServer {
    public static int SERVICE_PORT = 9898;
    public static int MAX_SIZE = 10;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_SIZE);
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(SERVICE_PORT);
            for(;;){
                clientSocket = serverSocket.accept();
                RequestProcessorThread requestProcessorThread = new RequestProcessorThread(clientSocket);

                System.out.println("Request processor created and handed over the connection. "+ "Thread ["+ requestProcessorThread.toString() +"] Socket ["+clientSocket.toString()+"]");
                executorService.execute(requestProcessorThread);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        while(!executorService.isTerminated()){
            System.out.println("Finished all threads.");
        }
    }
}
