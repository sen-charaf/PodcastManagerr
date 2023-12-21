package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class HomepageController {

    @FXML
    private Button createplaylistBtn;

    @FXML
    private Button uploadBtn;
    @FXML
    private VBox playlistVcontainer;

    @FXML
    public void mouseEnterUpload(MouseEvent  event) {
    	uploadBtn.setStyle("-fx-background-color:  #8550F7B8;-fx-background-radius: 54;");
    }
    public void mouseLeaveUpload(MouseEvent  event) {
    	uploadBtn.setStyle("-fx-background-color: #8550F7;-fx-background-radius: 54;");
    }
    public void mouseEnterCreatePlaylist(MouseEvent  event) {
    	createplaylistBtn.setStyle("-fx-background-color: #5C5C5CB8;-fx-background-radius: 54;");
    }
    public void mouseLeaveCreatePlaylist(MouseEvent  event) {
    	createplaylistBtn.setStyle("-fx-background-color: #5C5C5C;-fx-background-radius: 54;");
    }
    
    public void createLabel() {
    	System.out.println("ccc");
        Label label = new Label("New Label");
        label.getStyleClass().add("playlistName");
        playlistVcontainer.getChildren().add(label);
        System.out.println("gg");
    }

}
