package ClientServer;

/**
 * Created by methma on 7/25/17.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WorkerThreadClient {
    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Worker thread Client");
    private JTextField dataField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 60);

    public static int SEVICE_PORT = 9898;

    public WorkerThreadClient() {

        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");

        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield
             * by sending the contents of the text field to the
             * server and displaying the response from the server
             * in the text area.  If the response is "." we exit
             * the whole application, which closes all sockets,
             * streams and windows.
             */
            public void actionPerformed(ActionEvent e) {
                out.println(dataField.getText());
                String response;
                try {
                    response = in.readLine();
                    if (response == null || response.equals("")) {
                        System.exit(0);
                    }
                } catch (IOException ex) {
                    response = "Error: " + ex;
                }
                messageArea.append(response + "\n");
                dataField.selectAll();
            }
        });
    }

    public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
        String serverAddress = JOptionPane.showInputDialog(
                frame,
                "Enter IP Address of the Server:",
                "Welcome to the Worker Thread Program",
                JOptionPane.QUESTION_MESSAGE);

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, SEVICE_PORT);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Consume the initial welcoming messages from the server
        for (int i = 0; i < 3; i++) {
            messageArea.append(in.readLine() + "\n");
        }
    }


    public static void main(String[] args) throws Exception {
        WorkerThreadClient client = new WorkerThreadClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);
        client.connectToServer();
    }
}
