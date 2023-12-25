package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;


public class ControllerCreatePodcast implements Initializable{
	    @FXML
	    private Button ChoseFileButtonID;

	    @FXML
	    private ImageView ArrowLeftID;
	    
	    @FXML
	    private ImageView Image1ID;

	    @FXML
	    private Rectangle RectangleID;
	    
	    @FXML
	    private ImageView Image2ID;

	    @FXML
	    private Pane PaneID;

	    @FXML
	    private Button UploadButtonID;
	    
	    @FXML
	    private ChoiceBox<String> ChoiceBoxID;
	    
	    @FXML
	    private TextField WritePlaylistFieldID;
	    
	    @FXML
	    private ImageView ArrowLeftID2;
	    
	    @FXML
	    private TextField ReleaseScheduleID;
	    
	    @FXML
	    private TextField PodcastTitleID;
	    @FXML
	    
	    private Label LabelPodcastErreurID;
	    @FXML
	    private Label ChosePlaylistErreurID;

	    @FXML
	    private Label DescriptionErreurID;

	    @FXML
	    private TextField DescriptionID;

	    @FXML
	    private Label GenresErreurID;

	    @FXML
	    private TextField GenresID;

	    @FXML
	    private Label HotsErreurID;
   
	    @FXML
	    private Label ImageErreurID;
	    
	    @FXML
	    private TextField HotsID;
	    
	    @FXML
	    private Label ChooseSongErreurID;
	    
	    @FXML
	    private ImageView ExitButtonID;
	    
	    private ImagePattern selectedImagePattern;
	    
	    public List<String> attributeList = new ArrayList<>();
	    
	    private String ImageSelectedURL;
	    
		private String SongPath;
		
		public String selectedOption;
		
		public LocalTime localTime;
	  
     @Override		
	 public void initialize(URL arg0 , ResourceBundle arg1) {
    	 
		 Image2ID.setVisible(false);
		 ArrowLeftID.setVisible(false);
		 ArrowLeftID2.setVisible(false);
		 WritePlaylistFieldID.setVisible(false);
		 
		 
		 getAll();
		 ChoiceBoxID.getItems().addAll(attributeList);
		 
		 
		 
		 Image1ID.setOnMouseEntered(e->{
			 Image1ID.setVisible(false);
			 Image2ID.setVisible(true);
		 });
		 
		 
		 Image2ID.setOnMouseExited(e->{
			 Image2ID.setVisible(false);
			 Image1ID.setVisible(true);
		 });
		 
		 
		 Image2ID.setOnMouseClicked(e -> {
	            // Open file chooser
	            FileChooser fileChooser = new FileChooser();
	            fileChooser.setTitle("Select Image File");
	            fileChooser.getExtensionFilters().addAll(
	                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
	            );

	            File selectedFile = fileChooser.showOpenDialog(null);
	            if (selectedFile != null) {
	            	Image1ID.setVisible(false);
	            	ArrowLeftID.setVisible(true);
	            	ImageErreurID.setText("");
	                // Load the selected image
	                Image selectedImage = new Image(selectedFile.toURI().toString());
	                
	                setImageSelectedURL(selectedFile.toURI().toString());
	                System.out.println("ImageURL : "+getImageSelectedURL());
	                
	                //setImageUrl(selectedFile.toURI().toString());
	                selectedImagePattern = new ImagePattern(selectedImage);
	                // Set the selected image as the background of the Rectangle
	                RectangleID.setFill(selectedImagePattern);
	            }
	            
	        });
		 
		 
		 ArrowLeftID.setOnMouseClicked(e->{
	        	RectangleID.setFill(Color.web("#757575"));
	        	Image1ID.setVisible(true);
	        	ArrowLeftID.setVisible(false);
	        	setImageSelectedURL(null);
	        });
		 
		 
		 ChoseFileButtonID.setOnAction(e -> {
	            // Open file chooser
	            FileChooser fileChooser = new FileChooser();
	            fileChooser.setTitle("Choose MP3 File");
	            
	            // Set a filter for MP3 files
	            ExtensionFilter extFilter = new ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
	            fileChooser.getExtensionFilters().add(extFilter);

	            File selectedFile = fileChooser.showOpenDialog(null);

	            if (selectedFile != null) {
	            	 ChooseSongErreurID.setText("");
	                // Process the selected MP3 file
	                String filePath = selectedFile.getAbsolutePath();
	                setSongPath(filePath);
	                System.out.println("Selected MP3 file: " + getSongPath());
	                
	            }
	        });
		 
		 
		 ChoiceBoxID.setOnAction(e -> {
	             selectedOption = ChoiceBoxID.getValue();
	            if (selectedOption.equals("Write Playlist")) {
	                // Replace choiceBox with textField
	            	ChoiceBoxID.setVisible(false);
	            	WritePlaylistFieldID.setVisible(true);
	            	ArrowLeftID2.setVisible(true);
	              }
	        });
		 
		 
		 ArrowLeftID2.setOnMouseClicked(e ->{
			ChoiceBoxID.setVisible(true);
         	WritePlaylistFieldID.setVisible(false);
         	ArrowLeftID2.setVisible(false);
         	ChoiceBoxID.setValue("");
		 });
		 
		 
		 
		 ExitButtonID.setOnMouseClicked(e->{
			 try {
					PaneID.getScene();
					System.out.print("Close Clicked");
		        	PaneID.setVisible(false);
		        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomePage.fxml"));
					System.out.println(loader);
					Parent root2;
					root2 = loader.load();
					HomepageController hc = loader.getController();
					System.out.println(hc);
					hc.handelOverlay(2,2);
					Scene scene = new Scene(root2);
			        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			        stage.setScene(scene);
			        stage.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 });
	 }
	 
	 
	 public void getAll() {
	    	try {
				Connection connection = MySqlConnector.getDBConnection();
				 
				String sql="SELECT name FROM `playlist`"; 
				PreparedStatement ps=connection.prepareStatement(sql);
				
				ResultSet results = ps.executeQuery();
				
				attributeList.add("Write Playlist");
				
				while (results.next()) {
					String NamePlaylist = results.getString("name");
					attributeList.add(NamePlaylist);
				}
			} catch (Exception e) {
				System.out.println("I have error in Method getAll()");
			}
	    }
	 
	 
	 

	 
public void UploadPodcast(ActionEvent e) {
		    try {
		       final String PodcastName =  PodcastTitleID.getText().trim();
		       final String Hots =  HotsID.getText().trim();
		       final String Description = DescriptionID.getText().trim();
		       final String ReleaseSchedule =  ReleaseScheduleID.getText().trim();
		       final String Genre =  GenresID.getText().trim();
		       final String ImageUrl = getImageSelectedURL();
		       final String SongPath = getSongPath();
		       if(WritePlaylistFieldID.isVisible())
		    	   selectedOption = WritePlaylistFieldID.getText();
		    	  
		       final String PlaylistName = selectedOption;
		       
		       if (PodcastName.equals("")) {
		           throw new IllegalArgumentException("Podcast Name cannot be empty!");
		       }

		       if (Hots.equals("")) {
		           throw new IllegalArgumentException("Hots cannot be empty!");
		       }
		       if (Description.equals("")) {
		           throw new IllegalArgumentException("Description cannot be empty!");
		       }
		       if (Genre.equals("")) {
		           throw new IllegalArgumentException("Genre cannot be empty!");
		       }
		       if (ImageUrl == null) {
		    	   System.out.println("UploadPodcast : " +ImageUrl);
		           throw new IllegalArgumentException("No image selected!");
		       }
		       if (!attributeList.contains(PlaylistName)) {
		           throw new IllegalArgumentException("Playlist doesn't exist!");
		       }
		       if (SongPath == null) {
		    	   System.out.println("UploadPodcast : " +SongPath );
		           throw new IllegalArgumentException("No song selected!");
		       }
		       
		       PodcastTitleID.setText("");
		       HotsID.setText("");
		       DescriptionID.setText("");
		       ReleaseScheduleID.setText("");
		       GenresID.setText("");
		       setImageSelectedURL(null);
		       setSongPath(null);
		       
		       
		       RectangleID.setFill(Color.web("#757575"));
		       Image1ID.setVisible(true);
		       ArrowLeftID.setVisible(false);
		       ArrowLeftID2.setVisible(false);
		       ChoiceBoxID.setVisible(true);
           	   WritePlaylistFieldID.setVisible(false);
		   	   
		       LabelPodcastErreurID.setText("");
		       ChosePlaylistErreurID.setText("");
		       DescriptionErreurID.setText("");
		       GenresErreurID.setText("");
		       HotsErreurID.setText("");
		       ChooseSongErreurID.setText("");
		       ImageErreurID.setText("");
		       
		       ChoiceBoxID.setValue("");
		       
		       Media media = new Media(new File(SongPath).toURI().toString());
		       MediaPlayer mediaPlayer = new MediaPlayer(media);
		       mediaPlayer.setOnReady(() -> {
		            // Duration En seconde
		            long durationInSeconds = (long)media.getDuration().toSeconds();
		            localTime = LocalTime.ofSecondOfDay(durationInSeconds);
		            // ITSNA BIMA YSALI MN LHSSAB T DURATION HAD Y9D I INSERER
		            Platform.runLater(() -> {
		            	insert(PodcastName,Hots,Description , ReleaseSchedule , Genre , PlaylistName , SongPath ,ImageUrl,localTime);
		            });
		       });
		       
		    } catch (IllegalArgumentException e1) {
		    	 if (e1.getMessage().contains("Podcast")) {
		    		   LabelPodcastErreurID.setText(e1.getMessage());
				       ChosePlaylistErreurID.setText("");
				       DescriptionErreurID.setText("");
				       GenresErreurID.setText("");
				       HotsErreurID.setText("");
				       ChooseSongErreurID.setText("");
				       ImageErreurID.setText("");
		         } else if (e1.getMessage().contains("Hots")) {
		        	   LabelPodcastErreurID.setText("");
				       ChosePlaylistErreurID.setText("");
				       DescriptionErreurID.setText("");
				       GenresErreurID.setText("");
				       HotsErreurID.setText(e1.getMessage());
				       ChooseSongErreurID.setText("");
				       ImageErreurID.setText("");
		         } else if (e1.getMessage().contains("Description")) {
		        	   LabelPodcastErreurID.setText("");
				       ChosePlaylistErreurID.setText("");
				       DescriptionErreurID.setText(e1.getMessage());
				       GenresErreurID.setText("");
				       HotsErreurID.setText("");
				       ChooseSongErreurID.setText("");
				       ImageErreurID.setText("");
		         } else if (e1.getMessage().contains("Genre")) {
		        	   LabelPodcastErreurID.setText("");
				       ChosePlaylistErreurID.setText("");
				       DescriptionErreurID.setText("");
				       GenresErreurID.setText(e1.getMessage());
				       HotsErreurID.setText("");
				       ChooseSongErreurID.setText("");
				       ImageErreurID.setText("");
		         }else if (e1.getMessage().contains("No image")) {
		        	   LabelPodcastErreurID.setText("");
				       ChosePlaylistErreurID.setText("");
				       DescriptionErreurID.setText("");
				       GenresErreurID.setText("");
				       HotsErreurID.setText("");
				       ChooseSongErreurID.setText("");
				       ImageErreurID.setText(e1.getMessage());
		         }
		         else if (e1.getMessage().contains("Playlist")) {
		        	   LabelPodcastErreurID.setText("");
				       ChosePlaylistErreurID.setText(e1.getMessage());
				       DescriptionErreurID.setText("");
				       GenresErreurID.setText("");
				       HotsErreurID.setText("");
				       ChooseSongErreurID.setText("");
				       ImageErreurID.setText("");
		         }else if (e1.getMessage().contains("No song")) {
		        	   LabelPodcastErreurID.setText("");
				       ChosePlaylistErreurID.setText("");
				       DescriptionErreurID.setText("");
				       GenresErreurID.setText("");
				       HotsErreurID.setText("");
				       ChooseSongErreurID.setText(e1.getMessage());
				       ImageErreurID.setText("");
		         }
		    
		    } catch (Exception e1) {
		     
		        System.out.println("Exception " + e1.getMessage());
}
		       
}

	 
public static void insert(String PodcastTitle,String Hots , String Description ,String ReleaseSchedule , String Genre , String PlaylistName , String SongPath ,String ImageUrl,LocalTime Duration) {
		 try {
			    Connection connection = MySqlConnector.getDBConnection();
			    
			    LocalDate currentDate = LocalDate.now();
			    Date sqlDate = Date.valueOf(currentDate);
			    Time DurationSongTime = Time.valueOf(Duration);
			    
			    System.out.println(DurationSongTime +"  "+ sqlDate);
			    
			    String playlistIdQuery = "SELECT playlist_id FROM `playlist` WHERE name = ?";
			    PreparedStatement playlistIdStatement = connection.prepareStatement(playlistIdQuery);
			    playlistIdStatement.setString(1, PlaylistName);
			    ResultSet results = playlistIdStatement.executeQuery();

			    // Move the cursor to the first row
			    if (results.next()) {
			        int playlistId = results.getInt("playlist_id");

			        String sql = "INSERT INTO `podcasts` (`playlist_id`,`title`, `image`, `hosts`,`description`,`release_schedule`,`genres`,`date_added`,`duration`,`song`) VALUES (?,?,?,?,?,?,?,?,?,?)";
			        PreparedStatement ps = connection.prepareStatement(sql);
			        ps.setInt(1, playlistId);
			        ps.setString(2, PodcastTitle);
			        ps.setString(3, ImageUrl);
			        ps.setString(4, Hots);
			        ps.setString(5, Description);
			        ps.setString(6, ReleaseSchedule);
			        ps.setString(7, Genre);
			        ps.setDate(8,sqlDate);
			        ps.setTime(9,DurationSongTime);
			        ps.setString(10, SongPath);
			        ps.execute();
			    } else {
			        
			        System.out.println("Playlist not found for name: " + PlaylistName);
			    }

			} catch (Exception e) {
			    e.printStackTrace();
			}
}
	 

	public String getImageSelectedURL() {
		return ImageSelectedURL;
	}


	public void setImageSelectedURL(String imageSelectedURL) {
		ImageSelectedURL = imageSelectedURL;
	}


	public String getSongPath() {
		return SongPath;
	}


	public void setSongPath(String songPath) {
		SongPath = songPath;
	}
	 
	 
}
