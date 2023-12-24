package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class InspectPodcastController implements Initializable{

	    @FXML
	    private Button DeleteButtonID;

	    @FXML
	    private Label EpisodeDurationID;

	    @FXML
	    private ImageView ExitButtonID;

	    @FXML
	    private Label GenreID;

	    @FXML
	    private Label HostsID;

	    @FXML
	    private Pane InformationContainerID;

	    @FXML
	    private ImageView NextButtonID;

	    @FXML
	    private Pane PaneID;

	    @FXML
	    private ImageView PauseButtonID;

	    @FXML
	    private ImageView PlayButtonID;

	    @FXML
	    private Label PodcastTitlaID;

	    @FXML
	    private ImageView PreviousButtonID;

	    @FXML
	    private Rectangle RectangleID;

	    @FXML
	    private Label ReleaseScheduleID;

	    @FXML
	    private Label TitreID;

  
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			PauseButtonID.setVisible(false);
		}
	    
	    
}
