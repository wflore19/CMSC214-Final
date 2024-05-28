package iMessageCore.Objects;

public class Message {
	private int id;
	private String message;

	public Message(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Client " + id + ": " + message;
	}

}
