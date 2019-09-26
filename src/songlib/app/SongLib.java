package songlib.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import songlib.view.SongLibController;;

public class SongLib extends Application{
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/songlib/view/songlib.fxml"));
		AnchorPane root = (AnchorPane)loader.load();


		SongLibController listController = loader.getController();
		listController.start(primaryStage);

		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show(); 
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
