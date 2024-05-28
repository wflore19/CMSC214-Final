package iMessageCore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import iMessageCore.Interfaces.Observer;
import iMessageCore.Interfaces.Subject;
import iMessageCore.Objects.Message;

public class Server implements Subject {
    private static Server instance;
	private List<Observer> observers = new ArrayList<>();
    private MessageService messageService;
    private static boolean isConnected = false;
    
    public Server(MessageService messageService) {
        this.messageService = messageService;
    }
    
    public static void main(String[] args) throws IOException {
    	instance = new Server(MessageService.getInstance());
        
        Server.start(3000);

    }
    public static Server getInstance() {
        return instance;
    }
	public static void start(int port) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            isConnected = true;
            
            while (isConnected) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, Server.getInstance());
                new Thread(clientHandler).start();
            }
        }
    }
	public boolean isRunning() {
		return isConnected;
	}
    public void emitMessage(Message message) {
        notifyObservers(message);
    }
    public void saveMessage(Message message) {
    	messageService.addMessageToHistory(message);    	
    }
	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
	@Override
	public void removeObserver(Observer observer) {
        observers.remove(observer);		
	}
	@Override
	public void notifyObservers(Message message) {
		for (Observer observer : observers) {
            observer.update(message);
        }
	}	
	public MessageService getMessageService() {
		return messageService;
	}
}
