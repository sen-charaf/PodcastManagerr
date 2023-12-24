package application;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HomepageController {

	@FXML
	private Parent createplaylistBtn;
	@FXML
	private AnchorPane anchorContainer;
	@FXML
	private Button uploadBtn;
	@FXML
	private VBox playlistVcontainer;
	@FXML
	private Parent createPlaylistOverlay;

	
	
	@FXML 
	void initialize() {
		anchorContainer.setDisable(false);
		
	}
	public void handelOverlay(int mode) {
		if (mode == 1) {
			anchorContainer.setDisable(true);
			createPlaylistOverlay.setVisible(true);
			
			System.out.println("1 "+createPlaylistOverlay);
		}else if (mode == 2) {
			anchorContainer.setDisable(false);
		}
		
	}
	

}
