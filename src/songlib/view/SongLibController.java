package songlib.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import Operations.operation;
import Song.songs;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class SongLibController {
	@FXML
	ListView<songs> listView;
	@FXML Text name;
	@FXML Text artist;
	@FXML Text album;
	@FXML Text year;
	@FXML Button play;
	@FXML Button add;
	
	private ObservableList<songs> obsList;
	private List<songs> obs;
	
	
	public void start(Stage mainStage) throws IOException {
		// create an ObservableList 
		// from an ArrayList  
		obs = operation.loadlib();
		
		obsList = FXCollections.observableArrayList(obs); 
		
		listView.setItems(obsList); 
		
		// select the first item
	    listView.getSelectionModel().select(0);
	    
	    name.setText(obs.get(0).getName());
	    artist.setText(obs.get(0).getArtist());
	    album.setText(obs.get(0).getAlbum());
	    year.setText(""+obs.get(0).getYear());
	    

	    
	}
	
	public void display(ActionEvent e) {
		songs selectedSong = listView.getSelectionModel().getSelectedItem();
		name.setText(selectedSong.getName());
		artist.setText(selectedSong.getArtist());
	    album.setText(selectedSong.getAlbum());
	    year.setText(""+selectedSong.getYear());
	}
	
	public void addSong(ActionEvent e) {
		Node source = (Node) e.getSource();
	    Window theStage = source.getScene().getWindow();
	    
	    Dialog<songs> dialog = new Dialog<>();
		dialog.initOwner(theStage);
		dialog.setTitle("NEW SONG");
		dialog.setHeaderText("Please specify…");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		
	    TextField nname = new TextField();
	    nname.setPromptText("Name: ");
	    TextField nartist = new TextField();
	    nartist.setPromptText("Artist: ");
	    TextField nalbum = new TextField();
	    nalbum.setPromptText("Album: ");
	    TextField nyear = new TextField();
	    nyear.setPromptText("Year: ");
	    dialogPane.setContent(new VBox(5, nname, nartist, nalbum, nyear));
	    Platform.runLater(nname::requestFocus);
	    dialog.setResultConverter((ButtonType button) -> {
	    	if(button == ButtonType.OK) {
	    		if(nname.getText().isEmpty() || nname.getText().isEmpty()) {
	    			Alert alert = new Alert(Alert.AlertType.WARNING);
	    			alert.setTitle("Warning");
	                alert.setHeaderText("Required Fields Empty");
	                alert.setContentText("You need to fill out name and artist at least.");

	                alert.showAndWait();
	                return null;
	    		}
	    	}
	    	return null;
	    });
		dialog.show();
		
		
	    
	}
}
