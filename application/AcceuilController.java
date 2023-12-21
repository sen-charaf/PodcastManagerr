package application;


import java.io.IOException;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AcceuilController {

    @FXML
    private FontAwesomeIcon arrowID;

    @FXML
    private AnchorPane welcomeBG;

    @FXML
    private Circle welcomeCirclBTN;

    @FXML
    private Label wolcomeLabel;

    @FXML
    private Label wolcomeLabel1;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void Enter_changetohoveredCircle(MouseEvent  event) {
    	welcomeCirclBTN.setStyle("-fx-fill: #8A62C9E5;");
 
    }
    public void Leave_changetohoveredCircle(MouseEvent  event) {
    	welcomeCirclBTN.setStyle("-fx-fill: #783ed4;");
    }
    public void ClickStartBTN(MouseEvent  event) throws IOException {
    	
    	Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
    	
        Scene scene = new Scene(root);
       
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
       
        stage.show();
    }
    

}
