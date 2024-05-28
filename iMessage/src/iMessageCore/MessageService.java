package iMessageCore;

import java.util.LinkedList;
import java.util.List;

import iMessageCore.Interfaces.Observer;
import iMessageCore.Objects.Message;

public class MessageService {
    private List<Message> chatHistory = new LinkedList<>();
    
    private static MessageService instance;
 // Private constructor to prevent instantiation
    private MessageService() {
    }
 // Public method to get the singleton instance
    public static synchronized MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }
    public void addMessageToHistory(Message message) {
        chatHistory.add(message);
    }
    public List<Message> getChatHistory(Observer observer) {
//    	observer.update(new Message(0, "Retrieved chat history."));
        return chatHistory;
    }

}
