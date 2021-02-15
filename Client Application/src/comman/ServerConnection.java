package comman;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection {
    private static ServerConnection serverConnection;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private ServerConnection() {
    }

    public static ServerConnection getInstance() {
        return serverConnection == null ? serverConnection = new ServerConnection() : serverConnection;
    }

    public void start(String host, int port) throws IOException {
        //Open Socket/Connection
        socket = new Socket(host, port);

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
