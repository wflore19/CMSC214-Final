package iMessageGUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PageChatRoom {
	private int id = new Random().nextInt(1000);
    private TextArea chatArea;
    private TextField inputField;
    private Button sendButton;
    private DataOutputStream out;

    public PageChatRoom(BorderPane root) {
    	new Thread(() -> connect()).start();

    	// Chat area for reading messages
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);

        // Input field for writing messages
        inputField = new TextField();
        inputField.setPromptText("Type your message...");
        inputField.setOnAction(event -> sendMessage());

        // Send button
        sendButton = new Button("Send");
        sendButton.setOnAction(event -> sendMessage());
        sendButton.setMinWidth(60.0);

        HBox hBox = new HBox(1, inputField, sendButton);
        HBox.setHgrow(inputField, Priority.ALWAYS);
        hBox.setPadding(new Insets(15.0));

        VBox vBox = new VBox(2, chatArea, hBox);
        VBox.setVgrow(chatArea, Priority.ALWAYS);
        root.setCenter(vBox);
    }
    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
			try {
				inputField.clear();
				chatArea.requestFocus();
				out.writeUTF(message);
				out.flush();
			} catch (ConnectException e) {
				showMessage("Failed to send message. Connection error.");
			} catch (UnknownHostException e) {
				showMessage("Failed to send message. Unknown host.");
			} catch (IOException e) {
				showMessage("Failed to send message. I/O error.");
			}
			
        }
    }
    public void showMessage(String message) {
        if (!message.isEmpty()) {
            chatArea.appendText(message + "\n");            
        }
    }
    public void connect() {
    	int _port = 3000;
    	try (Socket socket = new Socket("localhost", _port)) {
    		DataInputStream in = new DataInputStream(socket.getInputStream());
    		out = new DataOutputStream(socket.getOutputStream());
    		
    		out.writeInt(id);
    		out.flush();
    		
    		Platform.runLater(() -> showMessage("Connected to iMessage Servers." + '\n'));
    		
			while (true) {
				int id = in.readInt();
				String message = in.readUTF();
				
				if (id == 0)
					Platform.runLater(() -> showMessage("Server: " + message));
				else
					Platform.runLater(() -> showMessage("Client " + id + ": " + message));
			}
    		
    	} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}

