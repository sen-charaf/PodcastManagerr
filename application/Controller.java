package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Controller {
    @FXML
    private TextField PlaylistNameID;
    @FXML
    private Button CreateButtonID;
    @FXML
    private TextField DescriptionID;
    @FXML
    private Pane PaneID;
    @FXML
    private ImageView image1ID;
    @FXML
    private ImageView image2ID;
    @FXML
    private Rectangle RectangleID;
    @FXML
    private Rectangle Rectangle2ID;
    @FXML
    private ImageView LeftArrowID;
    
    @FXML
    private Label ErreurDescription;

    @FXML
    private Label ErreurImage;

    @FXML
    private Label ErreurPlaylistName;
    
    @FXML
    private ImageView CloseID;
    
    private ImagePattern selectedImagePattern;
    
    private String imageUrl;
 
    public void initialize() {
        image2ID.setVisible(false);
        LeftArrowID.setVisible(false);
        image1ID.setOnMouseEntered(event -> {
            image1ID.setVisible(false);
            image2ID.setVisible(true);
        });

        image2ID.setOnMouseExited(event -> {
            image2ID.setVisible(false);
            image1ID.setVisible(true);
        });

        image2ID.setOnMouseClicked(event -> {
            // Open file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
            );

            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
            	image1ID.setVisible(false);
            	
            	
                // Load the selected image
                Image selectedImage = new Image(selectedFile.toURI().toString());
                setImageUrl(selectedFile.toURI().toString());
                selectedImagePattern = new ImagePattern(selectedImage);

                // Set the selected image as the background of the Rectangle
                RectangleID.setFill(selectedImagePattern);
                LeftArrowID.setVisible(true);
                ErreurImage.setText("");
            }
            
        });
        
        LeftArrowID.setOnMouseClicked(e->{
        	RectangleID.setFill(Color.web("#757575"));
        	image1ID.setVisible(true);
        	LeftArrowID.setVisible(false);
        	setImageUrl(null);
        });
        
        CloseID.setOnMouseClicked(e->{
        	System.out.print("Close Clicked");
        	PaneID.setVisible(false);
        	
        });
    }
    
    
    public void CreateElement(ActionEvent e) {
    try {
       final String PlaylistName =  PlaylistNameID.getText();
       final String Description = DescriptionID.getText();
       final String URL = getImageUrl();
       if (PlaylistName.isEmpty()) {
           throw new IllegalArgumentException("Playlist Name cannot be empty!");
       }

       if (Description.isEmpty()) {
           throw new IllegalArgumentException("Description cannot be empty!");
       }
       if (URL == null) {
    	   System.out.println(URL);
           throw new IllegalArgumentException("No image selected!");
       }
       
       PlaylistNameID.setText("");
       DescriptionID.setText("");
       RectangleID.setFill(Color.web("#757575"));
   	   image1ID.setVisible(true);
   	   LeftArrowID.setVisible(false);
   	   
   	   ErreurDescription.setText("");
       ErreurImage.setText("");
       ErreurPlaylistName.setText("");
       
       
   	   insert(PlaylistName,Description,URL);
    } catch (IllegalArgumentException e1) {
        // Handle specific error messages for each field
    	 if (e1.getMessage().contains("Playlist Name")) {
             ErreurPlaylistName.setText(e1.getMessage());
             ErreurDescription.setText("");
             ErreurImage.setText("");
         } else if (e1.getMessage().contains("Description")) {
             ErreurDescription.setText(e1.getMessage());
             ErreurPlaylistName.setText("");
             ErreurImage.setText("");
         } else if (e1.getMessage().contains("image")) {
        	 ErreurImage.setText(e1.getMessage());
        	 ErreurPlaylistName.setText("");
        	 ErreurDescription.setText("");
         }
        // You can add code here to display an error message to the user or log the error.
    } catch (Exception e1) {
        // Handle other unexpected exceptions
        System.out.println("An unexpected error occurred: " + e1.getMessage());
    }
       
    }

    public static void insert(String PlaylistName ,String Description , String ImageUrl) {
		try {
			
			//Connection connection = MysqlConnection.getDBConnection();
			// mohim rani bdlt hadi 7it kant 3tatni erreur
			Connection connection = MySqlConnector.getDBConnection();
			
			String sql = "INSERT INTO `playlist` (`name`, `small_description`, `image`) VALUES (?,?,?)"; 
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, PlaylistName);
			ps.setString(2, Description);
			ps.setString(3, ImageUrl);
			ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
   
	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
    
}
