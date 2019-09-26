package songlib.view;

//import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
//import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class SongLibController {
	@FXML
	ListView<String> listView;
	
	private ObservableList<String> obsList;
	
	public void start(Stage mainStage) {
		// create an ObservableList 
		// from an ArrayList  
		obsList = FXCollections.observableArrayList(                               
				"Giants",                               
				"Patriots",
				"49ers",
				"Rams",
				"Packers",
				"Colts",
				"Cowboys",
				"Broncos",
				"Vikings",
				"Dolphins",
				"Titans",
				"Seahawks",
				"Steelers",
				"Jaguars"); 
		
		listView.setItems(obsList); 
		
		// select the first item
	    listView.getSelectionModel().select(0);
	}
}
