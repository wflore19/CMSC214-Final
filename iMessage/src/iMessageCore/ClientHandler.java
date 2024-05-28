package iMessageCore;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Random;

import iMessageCore.Interfaces.Observer;
import iMessageCore.Objects.Message;

public class ClientHandler implements Observer, Runnable {
    private Socket socket;
    private Server server;
    private int id;
    private DataOutputStream out;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.server.registerObserver(this);
    }
    
    @Override
    public void run() {
        try(DataInputStream in = new DataInputStream(socket.getInputStream())) {
            out = new DataOutputStream(socket.getOutputStream());
            id = in.readInt();
            System.out.println("Client " + id + " connected.");
            server.emitMessage(new Message(0, "Client " + id + " connected."));
            
            loadChatHistory();

            String message;
            while ((message = in.readUTF()) != null) {
            	Message incomingMessage = new Message(id, message);
                server.emitMessage(incomingMessage);
                server.saveMessage(incomingMessage);
            }
        } catch (IOException e) {
            System.err.println("Client disconnected: " + e.getMessage());
            server.emitMessage(new Message(0, "Client disconnected: " + id));
        } finally {
            server.removeObserver(this);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void update(Message message) {
        if (out != null) {
            	try {
					out.writeInt(message.getId());
					out.writeUTF(message.getMessage());
				} catch (IOException e) {
					
				}
        }
    }
    private List<Message> getChatHistory() {
    	return MessageService.getInstance().getChatHistory(this);
    }
    private void loadChatHistory() {
    	List<Message> chatHistory = getChatHistory();
    	if (chatHistory.size() > 0) {
    		for (Message message : chatHistory) {
    			update(message);
    		}    		
    	}
    }

}
