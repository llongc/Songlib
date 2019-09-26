package songlib.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import Operations.operation;
import Song.songs;
import java.io.IOException;
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
	@FXML AnchorPane input;
	@FXML TextField nname;
	@FXML TextField nartist;
	@FXML TextField nalbum;
	@FXML TextField nyear;
	@FXML CheckBox aecheck;
	
	
	private ObservableList<songs> obsList;
//	private List<songs> obs;
	
	
	public void start(Stage mainStage) throws IOException {
		// create an ObservableList 
		// from an ArrayList  
		List<songs> obs = operation.loadlib();
		
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
	
	public void addOrEdit(ActionEvent e) {
		input.setVisible(true);
		Button b = (Button)e.getSource();
		if(b == add) {
			aecheck.setSelected(true);
			nname.clear();
			nartist.clear();
			nalbum.clear();
			nyear.clear();
		} else {
			aecheck.setSelected(false);
			songs selectedSong = listView.getSelectionModel().getSelectedItem();
			nname.setText(selectedSong.getName());
			nartist.setText(selectedSong.getArtist());
		    nalbum.setText(selectedSong.getAlbum());
		    nyear.setText(""+selectedSong.getYear());
		}
		
		
	}
	
	public void invisible(ActionEvent e) {
		input.setVisible(false);
	}
	
	public void ask(ActionEvent e) {
		Node source = (Node) e.getSource();
	    Window theStage = source.getScene().getWindow();
	    
	    Dialog<Boolean> dialog = new Dialog<>();
		dialog.initOwner(theStage);
		dialog.setTitle("Warning");
		dialog.setHeaderText("Are you sure you want to delete it?");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialog.show();
		
	    dialog.setResultConverter((ButtonType button) -> {
	    	if(button == ButtonType.OK) {
	    		int index = listView.getSelectionModel().getSelectedIndex();
	    		boolean res = operation.delete(index);
	    		if(res) {
	    			obsList.remove(index);
	    		}
	    		return res;
	    	}
	    	return false;
	    });
	}
	
	public void confirmation(ActionEvent e) {
		if(aecheck.isSelected()) {
			System.out.println("add");
    		if(nname.getText().isEmpty() || nname.getText().isEmpty()) {
    			Alert alert = new Alert(Alert.AlertType.WARNING);
    			alert.setTitle("Warning");
                alert.setHeaderText("Required Fields Empty");
                alert.setContentText("You need to fill out name and artist at least.");
                alert.showAndWait();
                return;
    		}
			List<songs> result = operation.add(nname.getText(), 
					nartist.getText(), nalbum.getText(), nyear.getText());
			if(result == null) {
	    		Alert alert = new Alert(Alert.AlertType.WARNING);
	    		alert.setContentText("This song has already inside the database.");
	    		alert.showAndWait();
	    		return;
			}
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Successfully add a new song.");
			alert.showAndWait();
			obsList = FXCollections.observableArrayList(result);
			listView.setItems(obsList); 
			input.setVisible(false);
		} else {
			System.out.println("edit");
		    int index = listView.getSelectionModel().getSelectedIndex();
		    List<songs> res = operation.edit(index, nname.getText(), 
					nartist.getText(), nalbum.getText(), nyear.getText());
		    if(res == null) {
		    	Alert alert = new Alert(Alert.AlertType.WARNING);
	    		alert.setContentText("Please make sure no conflicts in editing");
	    		alert.showAndWait();
	    		return;
		    } else {
		    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Successfully edit a song.");
				alert.showAndWait();
				obsList = FXCollections.observableArrayList(res);
				listView.setItems(obsList); 
		    	input.setVisible(false);
		    }
		    
		}
	}
//	public void addSong(ActionEvent e) {
//		Node source = (Node) e.getSource();
//	    Window theStage = source.getScene().getWindow();
//	    
//	    Dialog<songs> dialog = new Dialog<>();
//		dialog.initOwner(theStage);
//		dialog.setTitle("NEW SONG");
//		dialog.setHeaderText("Please specify…");
//		DialogPane dialogPane = dialog.getDialogPane();
//		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
//		
//		
//	    TextField nname = new TextField();
//	    nname.setPromptText("Name: ");
//	    TextField nartist = new TextField();
//	    nartist.setPromptText("Artist: ");
//	    TextField nalbum = new TextField();
//	    nalbum.setPromptText("Album: ");
//	    TextField nyear = new TextField();
//	    nyear.setPromptText("Year: ");
//	    dialogPane.setContent(new VBox(5, nname, nartist, nalbum, nyear));
//	    Platform.runLater(nname::requestFocus);
//
//	    dialog.setResultConverter((ButtonType button) -> {
//	    	if(button == ButtonType.OK) {
//	    		if(nname.getText().isEmpty() || nname.getText().isEmpty()) {
//	    			Alert alert = new Alert(Alert.AlertType.WARNING);
//	    			alert.setTitle("Warning");
//	                alert.setHeaderText("Required Fields Empty");
//	                alert.setContentText("You need to fill out name and artist at least.");
//	                alert.showAndWait();
//	                return null;
//	    		}
//	    		songs result = operation.add(nname.getText(), 
//	    				nartist.getText(), nalbum.getText(), nyear.getText());
//	    		if(result == null) {
//		    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
//		    		alert.setContentText("This song has already inside the database.");
//		    		alert.showAndWait();
//		    		return null;
//	    		} else {
//	    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
//		    		alert.setContentText("Successfully add new song.");
//		    		alert.showAndWait();
//	    			return result;
//	    		}
//	    	}
//	    	return null;
//	    });
//	    Optional<songs> optionalResult = dialog.showAndWait();
//	    if (optionalResult.isPresent()) { obsList.add(optionalResult.get()); }
//		
//	    
//	}
}
