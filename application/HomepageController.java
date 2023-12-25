package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import module.Podcast;

public class HomepageController {

	@FXML
	private Parent createplaylistBtn;
	@FXML
	private Parent createPodcastOverlay;
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
	
	private ArrayList<Podcast> podcastlist;
	
	
	@FXML 
	void initialize() {
		podcastlist=new ArrayList<Podcast>();
		anchorContainer.setDisable(false);
		podcastContainer.getChildren().clear();
		getAllPodcastFromDb(null);
		
	}
	public void handelOverlay(int mode,int overlaytype) {
		if (overlaytype==2) {
			if (mode == 1) {
				anchorContainer.setDisable(true);
				createPlaylistOverlay.setVisible(true);
				
				System.out.println("1 "+createPlaylistOverlay);
			}else if (mode == 2) {
				anchorContainer.setDisable(false);
		}
		}else if(overlaytype==1) {
			if (mode == 1) {
				anchorContainer.setDisable(true);
				createPodcastOverlay.setVisible(true);
				
				System.out.println("1 "+createPlaylistOverlay);
			}else if (mode == 2) {
				anchorContainer.setDisable(false);
		}
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
				String sql="SELECT * From podcasts ORDER BY date_added DESC";
				PreparedStatement ps = connection.prepareStatement(sql);
				results = ps.executeQuery();
			}else {
				String sql="SELECT * From podcasts Where title Like ? ORDER BY date_added DESC ";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, "%"+p_title+"%");
				results = ps.executeQuery();
			}
			
			while (results.next()) {
				Podcast pod = new Podcast();
				pod.setId(results.getInt("pod_id"));
				pod.setTitle(results.getString("title"));
				pod.setPlaylist_id(results.getInt("playlist_id"));
				pod.setImgsrc(results.getString("image").replace('\u00A0', ' '));
				pod.setHosts(results.getString("hosts"));
				pod.setDescription(results.getString("description"));
				System.out.println("4");
				pod.setRelease_scheduele(results.getString("release_schedule"));
				pod.setGenres(results.getString("genres"));
				pod.setDate(results.getDate("date_added"));
				pod.setDuration(results.getTime("duration"));
				if (results.getString("song") != null) {
					pod.setFilepath(results.getString("song").replace('\u00A0', ' '));
				}else {
					pod.setFilepath(results.getString("song"));
				}
				
				
				podcastlist.add(pod);
				
				String ptitle = results.getString("title");
				
				String pImgsrc= results.getString("image").replace('\u00A0', ' ');
				Pane pane= new Pane();
				pane.setPrefHeight(254);
				pane.setPrefWidth(230);
				pane.getStylesheets().add("file:/F:/.javaprojects/Podcast_GS/src/application/podcast.css");
				pane.setStyle("-fx-background-radius: 10; ");
				Rectangle rec= new Rectangle();
				Image image = new Image(pImgsrc);
				ImagePattern imageP = new ImagePattern(image);
				rec.setFill(imageP);
				rec.setArcHeight(10);
				rec.setArcWidth(10);
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
				pane.setCursor(Cursor.HAND);
				pane.setOnMouseClicked((event)->showInspectPodcast(event , podcastlist.indexOf(pod)));
				podcastContainer.getChildren().add(pane);
				
			}
		} catch (Exception e) {
			System.err.println("error");
		}
	}

	public void showInspectPodcast(MouseEvent event , int indxp) {
		try {
			ArrayList<Podcast> podcastnewList = shiftListFromIndex(indxp);
			System.out.println(podcastnewList);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("InspecterPodcast.fxml"));
			Parent root=loader.load();
			InspectPodcastController inspectC = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			inspectC.getPodcasts(podcastnewList);
			inspectC.setCloseMediaOnCloseWindow();
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Podcast> shiftListFromIndex(int indx) {
		
		ArrayList<Podcast> podcastShiftedList = new ArrayList<Podcast>();
		for (int i = indx; i < podcastlist.size(); i++) {
			podcastShiftedList.add(podcastlist.get(i));
		}
		for (int i = 0; i < indx; i++) {
			podcastShiftedList.add(podcastlist.get(i));
		}
		
		
		return podcastShiftedList;
		
	}

}
