package iMessageCore.Interfaces;

import iMessageCore.Objects.Message;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Message message);
}
