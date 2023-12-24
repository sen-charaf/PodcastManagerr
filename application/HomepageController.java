package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

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
	private TilePane podcastContainer;
	@FXML
	private TextField searchBar;
	
	
	@FXML 
	void initialize() {
		anchorContainer.setDisable(false);
		podcastContainer.getChildren().clear();
		getAllPodcastFromDb(null);
		
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
	
	public void searchOnpodcastBytitle(ActionEvent e) {
		
		
		
			String title = searchBar.getText();
			System.out.println(title.replace(' ', '_'));
			podcastContainer.getChildren().clear();
			getAllPodcastFromDb(title);
			System.out.println(title);
		
		
		
	}
	
	
	public void getAllPodcastFromDb(String p_title) {
		try {
			Connection connection = MySqlConnector.getDBConnection();
			ResultSet results ;
			if(p_title==null) {
				String sql="SELECT image,title From podcasts ORDER BY date_added DESC";
				PreparedStatement ps = connection.prepareStatement(sql);
				results = ps.executeQuery();
			}else {
				String sql="SELECT image,title From podcasts Where title Like ? ORDER BY date_added DESC ";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, "%"+p_title+"%");
				results = ps.executeQuery();
			}
			
			while (results.next()) {
				String ptitle = results.getString("title");
				System.out.println("44 "+ptitle.replace(' ', '_'));
				String pImgsrc= results.getString("image").replace('\u00A0', ' ');
				Pane pane= new Pane();
				pane.setPrefHeight(254);
				pane.setPrefWidth(230);
				pane.getStylesheets().add("file:/F:/.javaprojects/Podcast_GS/src/application/podcast.css");
				pane.setStyle("-fx-background-radius: 20; ");
				Rectangle rec= new Rectangle();
				Image image = new Image(pImgsrc);
				ImagePattern imageP = new ImagePattern(image);
				rec.setFill(imageP);
				rec.setArcHeight(20);
				rec.setArcWidth(20);
				rec.setWidth(180);
				rec.setHeight(180);
				rec.setLayoutX(25);
				rec.setLayoutY(14);
				rec.getStyleClass().add("imagePod");
				Label label = new Label();
				label.setText(ptitle);
				label.setTextFill(Paint.valueOf("white"));
				label.setLayoutX(0);
				label.setLayoutY(209);
				label.setPrefWidth(230);
				label.setPrefHeight(25);
				label.setAlignment(Pos.CENTER);
				label.setId("podcastName");
				pane.getChildren().add(rec);
				pane.getChildren().add(label);
				podcastContainer.getChildren().add(pane);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
