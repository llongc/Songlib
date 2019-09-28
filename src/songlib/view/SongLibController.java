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
	
//	kataomoi,aimer,daydream,2016
//	torches,aimer,torches,2019
	
	public void start(Stage mainStage) throws IOException {
		// create an ObservableList 
		// from an ArrayList  
		List<songs> obs = operation.loadlib();
		
		obsList = FXCollections.observableArrayList(obs); 
		
		listView.setItems(obsList); 
	    
	    if(obs.size() > 0) {
	    	// select the first item
		    selectlist(0);
	    }
	    

	    
	}
	
	public void selectlist(int index) {
		if(index == -1) {
			name.setText("");
			artist.setText("");
			album.setText("");
			year.setText("");
			return;
		}
		listView.getSelectionModel().select(index);
	    
    	name.setText(obsList.get(index).getName());
	    artist.setText(obsList.get(index).getArtist());
	    album.setText(obsList.get(index).getAlbum());
	    year.setText(obsList.get(index).getYear());
	}
	public void display(ActionEvent e) {
		if(obsList.size() == 0) {
			return;
		}
		songs selectedSong = listView.getSelectionModel().getSelectedItem();
		name.setText(selectedSong.getName());
		artist.setText(selectedSong.getArtist());
	    album.setText(selectedSong.getAlbum());
	    year.setText(selectedSong.getYear());
	}
	
	public void addOrEdit(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == add) {
			input.setVisible(true);
			aecheck.setSelected(true);
			nname.clear();
			nartist.clear();
			nalbum.clear();
			nyear.clear();
		} else {
			if(obsList.size() == 0) {
				return;
			}
			input.setVisible(true);
			aecheck.setSelected(false);
			songs selectedSong = listView.getSelectionModel().getSelectedItem();
			nname.setText(selectedSong.getName());
			nartist.setText(selectedSong.getArtist());
		    nalbum.setText(selectedSong.getAlbum());
		    nyear.setText(selectedSong.getYear());
		}
		
		
	}
	
	public void invisible(ActionEvent e) {
		input.setVisible(false);
	}
	
	public void ask(ActionEvent e) {
		if(obsList.size() == 0) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
            alert.setContentText("Nothing can be deleted");
            alert.showAndWait();
            return;
		}
		songs selectedSong = listView.getSelectionModel().getSelectedItem();
		Node source = (Node) e.getSource();
	    Window theStage = source.getScene().getWindow();
	    
	    Dialog<Boolean> dialog = new Dialog<>();
		dialog.initOwner(theStage);
		dialog.setTitle("Warning");
		dialog.setHeaderText("Are you sure you want to delete " + selectedSong.toString() + "?");
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
	    		
				if(obsList.size() == 0){
					selectlist(-1);
				} else if(index != obsList.size()) {
					selectlist(index);
				}
				input.setVisible(false);
	    		return res;
	    	}
	    	return false;
	    });
	}
	
	public void confirmation(ActionEvent e) {
		if(!nyear.getText().isEmpty()) {
    		try {
    			Integer.parseInt(nyear.getText());
    		} catch(Exception exc) {
    			Alert alert = new Alert(Alert.AlertType.WARNING);
    			alert.setTitle("Warning");
                alert.setContentText("You need to enter the integer for year.");
                alert.showAndWait();
                return;
    		}
		}
		if(aecheck.isSelected()) {
			System.out.println("add");
    		if(nname.getText().isEmpty() || nartist.getText().isEmpty()) {
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
			int index = operation.getIndex();
			System.out.println(index);
			selectlist(index);
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
		    }
		    
		}
	}
}
