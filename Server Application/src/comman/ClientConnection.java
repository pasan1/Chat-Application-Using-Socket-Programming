package comman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnection {
    private static ClientConnection clientConnection;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private ClientConnection() {
    }

    public static ClientConnection getInstance() {
        return clientConnection == null ? clientConnection = new ClientConnection() : clientConnection;
    }

    public void start(int port) throws IOException {
        //Open Socket/Connection
        serverSocket = new ServerSocket(port);

        //Connect with Client
        socket = serverSocket.accept();

        //For Data In and Out
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void close() throws IOException {
        serverSocket.close();
        socket.close();
        dataInputStream.close();
        dataOutputStream.close();
    }

    public String getMessage() throws IOException {
        return getDataInputStream().readUTF();
    }

    public void sendMessage(String message) throws IOException {
        getDataOutputStream().writeUTF(message);
    }
}
