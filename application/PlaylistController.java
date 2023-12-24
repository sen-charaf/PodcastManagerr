package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
	private Text smalldec;
	
	@FXML 
	private Rectangle imageRec;
	
	@FXML 
	private FontAwesomeIcon backbtn;
	
	@FXML
	private GridPane gridTable;
	
	 @FXML
	void initialize() {
		 
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
		 
	public void setPlaylistInfosToView(int idp) {
		setId_playlist(idp);
		playlistbigName.setText(getPlaylistNameDB(playls.getId()));
		smalldec.setText(getPlaylistSmalldescDB(playls.getId()));
		String imgsrc = getPlaylistImgsrc(playls.getId());
		if (imgsrc == null) imgsrc="file:/F:/dowld/imagenoAvaliable.jpg";
		Image image = new Image(imgsrc.replace('\u00A0', ' '));
		ImagePattern imageP = new ImagePattern(image);
		imageRec.setFill(imageP);
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

	public String getPlaylistSmalldescDB(int idp) {
		
		try {
			Connection connection= MySqlConnector.getDBConnection();
			String sql = "SELECT small_description From playlist WHERE playlist_id = ?";
			PreparedStatement pl= connection.prepareStatement(sql);
			pl.setInt(1, idp);
			ResultSet results = pl.executeQuery();
			results.next();
			return results.getString("small_description");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getPlaylistImgsrc(int idp) {
		
		try {
			Connection connection= MySqlConnector.getDBConnection();
			String sql = "SELECT image From playlist WHERE playlist_id = ?";
			PreparedStatement pl= connection.prepareStatement(sql);
			pl.setInt(1, idp);
			ResultSet results = pl.executeQuery();
			results.next();
			return results.getString("image");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void MouseEnteredOnPodcast (MouseEvent event,int rowIndex) {
		        for (Node node : gridTable.getChildren()) {
		        	
		            if (GridPane.getRowIndex(node) == rowIndex) {
		            	System.out.println(gridTable.getRowConstraints().get(rowIndex).getPrefHeight());
		                node.setStyle("-fx-background-color: rgba(255, 255, 255, 0.06); -fx-cursor:hand;" );
		                if(GridPane.getColumnIndex(node) == 0) {
		                	Parent parent = (Parent) node ;
		                	Node children = parent.getChildrenUnmodifiable().get(0);
		                	Parent subparent = (Parent) children;
		                	ObservableList<Node> subchildren = subparent.getChildrenUnmodifiable();
		                	subchildren.get(0).setVisible(false);
		                	subchildren.get(1).setVisible(true);
		                }
		                
		                
		            }
		        }
	}
	
	public void MouseExitedOnPodcast (MouseEvent event,int rowIndex) {
        for (Node node : gridTable.getChildren()) {
            if (GridPane.getRowIndex(node) == rowIndex) {
                node.setStyle("-fx-background-color: rgba(255, 255, 255, 0.00);" );
                if(GridPane.getColumnIndex(node) == 0) {
                	Parent parent = (Parent) node ;
                	Node children = parent.getChildrenUnmodifiable().get(0);
                	Parent subparent = (Parent) children;
                	ObservableList<Node> subchildren = subparent.getChildrenUnmodifiable();
                	subchildren.get(0).setVisible(true);
                	subchildren.get(1).setVisible(false);
                }
            }
        }
}
	
	public void getPodcastsOfPlaylistFromDB() {
		try {
			gridTable.getChildren().clear();
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
			System.out.println(gridTable.getRowConstraints().size());
			if ( gridTable.getRowConstraints().size() < total_rows ) {
				int rowsToAdd=total_rows-gridTable.getRowConstraints().size();
				for (int i = 0; i < rowsToAdd; i++) {
			        RowConstraints rowConstraint = new RowConstraints();
			        gridTable.getRowConstraints().add(rowConstraint);
			    }
			}
			int row=0;
			for (Podcast podcast : podlist) {
				int localrow=row; // localrow=row are the same just for the eventlisteners
				System.out.println(podcast);
				for (int col = 0; col < 5; col++) {
					 HBox hb = new HBox();
					 hb.setAlignment((col==0 || col==3 || col==4) ? Pos.CENTER : Pos.CENTER_LEFT);
					 switch (col) {
					case 0: {
						StackPane stackp = new StackPane();
						Label label= new Label();
						label.setText(Integer.toString(podcast.getId()));
						label.getStyleClass().add("rowtext");
						FontAwesomeIcon fontPlayicon = new FontAwesomeIcon();
						fontPlayicon.setGlyphName("PLAY");
						fontPlayicon.setFill(Paint.valueOf("white"));
						fontPlayicon.setSize("2em");
						fontPlayicon.setVisible(false);
						stackp.getChildren().add(label);
						stackp.getChildren().add(fontPlayicon);
						hb.getChildren().add(stackp);
						hb.setOnMouseEntered((event)-> MouseEnteredOnPodcast(event,localrow));
						hb.setOnMouseExited((event)-> MouseExitedOnPodcast(event,localrow));
						break;
					}case 1: {
						Rectangle rec = new Rectangle();
						rec.setWidth(70);
						rec.setHeight(70);
						rec.setArcHeight(10);
						rec.setArcWidth(10);
						String imagePathWithNewSpaces = podcast.getImgsrc().replace('\u00A0', ' ');
						
						Image image = new Image(imagePathWithNewSpaces);
			
						ImagePattern imageP = new ImagePattern(image);
						rec.setFill(imageP);
						Label label= new Label();
						label.setText(podcast.getTitle());
						label.getStyleClass().add("rowtext");
						hb.setSpacing(10);
						hb.getChildren().add(rec);
						hb.getChildren().add(label);
						hb.setOnMouseEntered((event)-> MouseEnteredOnPodcast(event,localrow));
						hb.setOnMouseExited((event)-> MouseExitedOnPodcast(event,localrow));
						break;
					}case 2:{
						Label label= new Label();
						label.setText(podcast.getHosts());
						label.getStyleClass().add("rowtext");
						hb.getChildren().add(label);
						hb.setOnMouseEntered((event)-> MouseEnteredOnPodcast(event,localrow));
						hb.setOnMouseExited((event)-> MouseExitedOnPodcast(event,localrow));
						break;
					}case 3:{
						Label label= new Label();
						DateFormat dateformat = DateFormat.getDateInstance(DateFormat.SHORT);
						label.setText(dateformat.format(podcast.getDate()));
						label.getStyleClass().add("rowtext");
						hb.getChildren().add(label);
						hb.setOnMouseEntered((event)-> MouseEnteredOnPodcast(event,localrow));
						hb.setOnMouseExited((event)-> MouseExitedOnPodcast(event,localrow));
						break;
					}case 4:{
						Label label= new Label();
						label.setText(podcast.getDuration().toString());
						label.getStyleClass().add("rowtext");
						hb.getChildren().add(label);
						hb.setOnMouseEntered((event)-> MouseEnteredOnPodcast(event,localrow));
						hb.setOnMouseExited((event)-> MouseExitedOnPodcast(event,localrow));
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + col);
					 }
					 
					 gridTable.add(hb, col, row);
					
					 }
					row++;
					
				}
				
				
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}