package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Button createplaylistBtn;

    @FXML
    private Button uploadBtn;
    @FXML
    private VBox vboxPlaylist;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void ClickedOnCreatePlaylist(ActionEvent event) {
    	try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomePage.fxml"));
	
			Parent root2=loader.load();
			HomepageController hc = loader.getController();
			System.out.println(hc);
			hc.handelOverlay(1,2);
			scene = new Scene(root2);
	        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    	public void ClickedOnUpload(ActionEvent event) {
        	try {

    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomePage.fxml"));
    	
    			Parent root2=loader.load();
    			HomepageController hc = loader.getController();
    			System.out.println(hc);
    			hc.handelOverlay(1,1);
    			scene = new Scene(root2);
    	        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	        stage.setScene(scene);
    	        stage.show();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    }
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
    	insertPlaylist();
    	vboxPlaylist.getChildren().clear();
    	getAllPlaylist();
        System.out.println("gg");
    }
    

    @FXML
    void initialize() {
    	vboxPlaylist.getChildren().clear();
    	getAllPlaylist();
        
    }
    
    public void getAllPlaylist() {
    	try {
			Connection connection = MySqlConnector.getDBConnection();
			
			String sql="SELECT * FROM `playlist`"; 
			PreparedStatement ps=connection.prepareStatement(sql);
			
			ResultSet results = ps.executeQuery();
			while (results.next()) {
				int play_id=results.getInt("playlist_id");
				String play_name=results.getString("name");
				String displayed_playName=play_name.concat(Integer.toString(play_id));
				Label label = new Label(displayed_playName);
		        label.getStyleClass().add("playlistName");
		        label.setPrefHeight(36.0);
		        label.setPrefWidth(400);
		        label.setOnMouseClicked((event)-> showPlaylist(event,play_id));
		        Line line = new Line(-100,0,300,0);
		        line.setStroke(Paint.valueOf("#3e3e3e"));
		      
		        vboxPlaylist.getChildren().add(label);
		        vboxPlaylist.getChildren().add(line);
				System.out.println(results.getString("name")+""+results.getInt("playlist_id"));
				
			}
		} catch (Exception e) {
			System.out.println("chi7aja mahiach alm3lm f l9raya");
		}
    	
    }
    
    public void insertPlaylist() {
    	try {
        	Connection connection = MySqlConnector.getDBConnection();
        	String sql="INSERT INTO `playlist` (`name`) VALUES (?)"; 
    		PreparedStatement pl=connection.prepareStatement(sql);
    		pl.setString(1, "playlist from db number");
    		
    		pl.execute();
        	}catch(Exception e) {
        		System.out.println("chi7aja mahiach alm3lm f lktba");
        	}
    }
    
    public void showPlaylist(MouseEvent  event,int idplaylist)  {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlaylistPage.fxml"));
    	
    	try {
			root=loader.load();
			/*Parent subRoot = FXMLLoader.load(getClass().getResource("HelloFx.fxml"));
			AnchorPane mainroot = (AnchorPane) root;
			mainroot.getChildren().add(subRoot);
			mainroot.setTopAnchor((Node) subRoot, 48.5);
			mainroot.setBottomAnchor((Node) subRoot, 48.5);
			mainroot.setLeftAnchor((Node) subRoot, 648.5);
			mainroot.setRightAnchor((Node) subRoot, 648.5);*/
			PlaylistController pc = loader.getController();
	    	pc.setPlaylistInfosToView(idplaylist);
	    	pc.getPodcastsOfPlaylistFromDB();
	    	
	        scene = new Scene(root);
	     
	        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        
	        stage.setScene(scene);
	      
	        stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
     
    }
}
    


