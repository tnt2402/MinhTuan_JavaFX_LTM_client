package DAO;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.fxml.Initializable;

//import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;

public class ServerConnection implements Initializable {
    Logger logger = Logger.getLogger(ServerConnection.class.getName());
    private static String SERVER_ADDRESS = "localhost";
    private static int SERVER_PORT = 2402;

    public static ServerConnection conn;

    Socket socket;
    BufferedWriter out;
    BufferedReader in;

    public ServerConnection() {
        Socket socket = new Socket();
        BufferedWriter out = null;
        BufferedReader in = null;
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() {
        try {
            String tmp = conn.in.readLine();
            return tmp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String message) {
        try {
            conn.out.write(message);
            conn.out.newLine();
            conn.out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean loginUser(String username, String password) {
        try {
            write("GET /login");
            in.readLine();

            // Send the username and password to the server
            write(username);
            write(password);

            System.out.println(username + "||" + password);

            // Wait for the server's response
            String response = new String();
            response = in.readLine();
            System.out.println(response);


        } catch (IOException e) {
            logger.log(Level.WARNING, "An error occurred during the connection", e);
            return false; // An error occurred during the connection
        }
        return false;
    }

    public ServerConnection connect() {
        String address = SERVER_ADDRESS;
        Integer port = SERVER_PORT;

        ServerConnection serverConnection = new ServerConnection();

        if (serverConnection.socket == null) {
            try {
                serverConnection.socket = new Socket(address, port);
                serverConnection.out = new BufferedWriter(new OutputStreamWriter(serverConnection.socket.getOutputStream()));
                serverConnection.in = new BufferedReader(new InputStreamReader(serverConnection.socket.getInputStream()));
                System.out.println("Connected to server successfully!");
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cannot connect to the server!");
                alert.showAndWait();
            }
        }

        //
        conn = serverConnection;
        return serverConnection;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}