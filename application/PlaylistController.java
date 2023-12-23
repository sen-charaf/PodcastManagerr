package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import module.Playlist;
import module.Podcast;

public class PlaylistController{
	private Playlist playls;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
    private Label playlistbigName;
	
	@FXML 
	private Rectangle imageRec;
	
	@FXML 
	private FontAwesomeIcon backbtn;
	
	@FXML
	private GridPane gridTable;
	
	 @FXML
	void initialize() {
		 Image image = new Image("file:/F:/dowld/chris-lynch-Qruwi3Ur3Ak-unsplash.jpg");
		 System.out.println("image");
		 ImagePattern imageP = new ImagePattern(image);
		 imageRec.setFill(imageP);
	}
	 
	public void backToHomePage(MouseEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
			scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	 
	public void setId_playlist(int id_playlist) {
			playls=new Playlist();
			playls.setId(id_playlist);
		}
		 
	public void setLabelPlaylistName(int idp) {
		setId_playlist(idp);
		playlistbigName.setText(getPlaylistNameDB(playls.getId()));
	}
	
	public String getPlaylistNameDB(int idp) {
		
		try {
			Connection connection= MySqlConnector.getDBConnection();
			String sql = "SELECT name From playlist WHERE playlist_id = ?";
			PreparedStatement pl= connection.prepareStatement(sql);
			pl.setInt(1, idp);
			ResultSet results = pl.executeQuery();
			results.next();
			return results.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	public void hoverOnPodcast (MouseEvent event) {
		/*gridTable.getChildren().forEach(child -> {
		    child.setOnMouseEntered(eve -> {
		        // Get the row index of the hovered child
		        int rowIndex = GridPane.getRowIndex(child);

		        // Change the background color of all elements in that row
		        for (Node node : gridPane.getChildren()) {
		            if (GridPane.getRowIndex(node) == rowIndex) {
		                node.setStyle("-fx-background-color: " + hoverColor.toString().replace("0x", "#"));
		            }
		        }
		    });
		});
		gridPane.getChildren().forEach(child -> {
		    child.setOnMouseExited(event -> {
		        // Restore the original background color for all elements in the row
		        int rowIndex = GridPane.getRowIndex(child);
		        for (Node node : gridPane.getChildren()) {
		            if (GridPane.getRowIndex(node) == rowIndex) {
		                node.setStyle("");  // Remove any applied background color
		            }
		        }
		    });
		});
		*/
	}
	
	public void getPodcastsOfPlaylistFromDB() {
		try {
			ArrayList<Podcast>podlist=new ArrayList<Podcast>();
			int total_rows=0;
			Connection connection = MySqlConnector.getDBConnection();
			String sql="Select * From podcasts WHERE playlist_id=?";
			PreparedStatement pl=connection.prepareStatement(sql);
			pl.setInt(1, playls.getId());
			ResultSet results =pl.executeQuery();
			while (results.next()) {
				int row_id=results.getInt("pod_id");
				String row_title=results.getString("title");
				String row_imgsrc=results.getString("image");
				String row_hosts=results.getString("hosts");
				Date row_dateAdded=results.getDate("date_added");
				Time row_duration=results.getTime("duration");
				Podcast pod = new Podcast(row_id,row_title,row_imgsrc,row_hosts,row_dateAdded,row_duration);
				podlist.add(pod);
				total_rows++;
			}
			int row=0;
			for (Podcast podcast : podlist) {
				System.out.println(podcast);
				for (int col = 0; col < 5; col++) {
					 HBox hb = new HBox();
					 if (col==0 || col==3 || col==4) {
						 hb.setAlignment(Pos.CENTER);
					 }else {
						 hb.setAlignment(Pos.CENTER_LEFT);
					 }
				}
				
				 row++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}