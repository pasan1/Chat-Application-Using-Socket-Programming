package controller;

import com.jfoenix.controls.JFXButton;
import comman.ServerConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChatWindowFormController {
    public TextField txtMessage;
    public JFXButton btnSend;
    public ListView viewMessage;

    private ServerConnection connection;

    public void initialize() {
        txtMessage.requestFocus();

        connection = ServerConnection.getInstance();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connection.start("localhost",5050);

                    String messageIn = "";

                    while (!messageIn.equals("end")) {
                        messageIn = connection.getMessage();
                        updateMessageArea("Server : ", messageIn);
                    }

                    connection.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateMessageArea(String message) {
        viewMessage.getItems().add("Me : " + message);
    }

    private void updateMessageArea(String user, String message) {
        viewMessage.getItems().add(user + " : " + message);
    }

    public void txtMessageOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            send(txtMessage.getText());
        }
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        send(txtMessage.getText());
    }

    private void send(String message) {
        try {
            connection.sendMessage(message);
            updateMessageArea(message);
            txtMessage.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
