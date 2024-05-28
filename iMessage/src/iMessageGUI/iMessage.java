package iMessageGUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class iMessage extends Application {
	PageChatRoom pageChatRoom = null;
	
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Set up chat UI
        pageChatRoom = new PageChatRoom(root);
        Scene scene = new Scene(root, 400, 400);
        
        
        primaryStage.setTitle("iMessage (beta)");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    @Override
    public void stop(){
        System.exit(0);
    }
}
