package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class podcastCartController {

    @FXML
    private Rectangle imagePodcRec;

    @FXML
    private Label podcastName;
    
    @FXML
    void initialize() {

		 Image image = new Image("file:///F:/dowld/Grey Modern Hello Podcast Podcast Cover.png");
		 ImagePattern imageP = new ImagePattern(image);
		 imagePodcRec.setFill(imageP);
	}
}
