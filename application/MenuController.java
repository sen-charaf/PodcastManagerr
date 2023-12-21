package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MenuController {

    @FXML
    private Button createplaylistBtn;

    @FXML
    private Button uploadBtn;
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

}
