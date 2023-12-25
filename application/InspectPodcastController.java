package application;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import module.Podcast;

public class InspectPodcastController  {

    @FXML
    private Button DeleteButtonID;
    @FXML
    private Label DescId;

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

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Slider slidedBar;


    private File diractory;
    private File[] files;
    private Media media;
    private MediaPlayer mediaPlayer;
    
    private ArrayList<File> podcasts;
    private int podcastNumber;
    
    private Timer timer;
    private TimerTask task;
    
    private boolean running;
    private double current;
    private double end;
    private boolean changing;
    private int changeStep;
    private ArrayList<Podcast> podcastsFromDb= new ArrayList<Podcast>();
    
    public void getPodcasts(ArrayList<Podcast> podcastsInspect) {
    	
    	podcastsFromDb.addAll(podcastsInspect);
    	System.out.println("hello"+podcastsFromDb);
    	podcasts = new ArrayList<File>();
    	for (Podcast podcast : podcastsFromDb) {
    		if (podcast.getFilepath()!=null) {
    			File file = new File(podcast.getFilepath());
    			if (file.exists()) {
                    podcasts.add(file);
                } else {
                    System.out.println("File not found: " + podcast.getFilepath());
                }
    		}
			
		}
    	System.out.println(podcasts);
    	media = new Media(podcasts.get(podcastNumber).toURI().toString());
    	mediaPlayer = new MediaPlayer(media);
    	setDetailsinfo();
    	slidedBar.valueChangingProperty().addListener((observable, oldValue, newValue)->{
    		
        	changing=true;
        	mediaPlayer.seek(Duration.seconds(slidedBar.getValue() / 100 * end));
        	if(changeStep == 1) {
        		changing=false;
        		changeStep=0;
        		System.out.println("Step2");
        	}else {
        		changeStep=1;
        	}
        	System.out.println(slidedBar.getValue()); 
        
    	});
    }
    
    public void setDetailsinfo() {
    	PodcastTitlaID.setText(podcastsFromDb.get(podcastNumber).getTitle());
    	TitreID.setText(podcastsFromDb.get(podcastNumber).getTitle());
    	Image image = new Image(podcastsFromDb.get(podcastNumber).getImgsrc());
    	//System.out.println(podcastsFromDb.get(podcastNumber).getImgsrc());
		ImagePattern imageP = new ImagePattern(image);
		RectangleID.setFill(imageP);
		HostsID.setText(podcastsFromDb.get(podcastNumber).getHosts());
		DescId.setText(podcastsFromDb.get(podcastNumber).getDescription());
		EpisodeDurationID.setText( podcastsFromDb.get(podcastNumber).getDuration().toString());
		if(podcastsFromDb.get(podcastNumber).getRelease_scheduele() != null) {
			ReleaseScheduleID.setText(podcastsFromDb.get(podcastNumber).getRelease_scheduele());
		}else {
			ReleaseScheduleID.setText("");
		}
		GenreID.setText(podcastsFromDb.get(podcastNumber).getGenres());
    }
    @FXML
    void initialize() {
    	DeleteButtonID.setOnAction(event -> {
    		if (running) {
    			mediaPlayer.stop();
    		}
    		deletePodcast(podcastsFromDb.get(podcastNumber).getId());
            Stage stage = (Stage) DeleteButtonID.getScene().getWindow();
            stage.close();
        });
    	
    }
    public void setCloseMediaOnCloseWindow() {
    	Stage stage = (Stage) PaneID.getScene().getWindow();
    	stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				
				mediaPlayer.stop();
			}	
		});
    }
    public void deletePodcast(int id) {
    	try {
    		Connection connection = MySqlConnector.getDBConnection();
        	String sql = "DELETE FROM podcasts Where pod_id = ?";
			PreparedStatement ps=connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void switchingToPlayPauseBtns(String btn) {
    	if (btn.equals("play")) {
        	PlayButtonID.setVisible(true);
        	PauseButtonID.setVisible(false);
    	}else if (btn.equals("pause")) {
    		PlayButtonID.setVisible(false);
        	PauseButtonID.setVisible(true);
    	}else System.err.println("Argrument btn isn't play or pause");
    }

    
    @FXML
    void pausePodcast(MouseEvent event) {
    	cancelTimer();
    	switchingToPlayPauseBtns("play");
    	mediaPlayer.pause();
    }

    @FXML
    void playPodcast(MouseEvent event) {
    	launchTimer();
    	switchingToPlayPauseBtns("pause");
    	mediaPlayer.play();
    }
    
    @FXML
    void nextPodcast(MouseEvent event) {
    	switchingToPlayPauseBtns("play");
    	progressBar.setProgress(0);
    	slidedBar.setValue(0);
    	if (podcastNumber < podcasts.size()-1) {
    		podcastNumber ++;

    	}else {
    		podcastNumber = 0;
    	}
    	mediaPlayer.stop();
    	if (running) {
    		cancelTimer();
    	}
		media = new Media(podcasts.get(podcastNumber).toURI().toString());
    	mediaPlayer = new MediaPlayer(media);
    	setDetailsinfo();
    }
    
    @FXML
    void previousPodcast(MouseEvent event) {
    	switchingToPlayPauseBtns("play");
    	progressBar.setProgress(0);
    	slidedBar.setValue(0);
    	if (podcastNumber > 0) {
    		podcastNumber --;

    	}else {
    		podcastNumber = podcasts.size()-1;
    	}
    	
    	mediaPlayer.stop();
    	if (running) {
    		cancelTimer();
    	}
		media = new Media(podcasts.get(podcastNumber).toURI().toString());
    	mediaPlayer = new MediaPlayer(media);
    	setDetailsinfo();
    }
    
    @FXML
    void swirchtoProgressBar(MouseEvent event) {
    	progressBar.setVisible(true);
    	slidedBar.setVisible(false);
    }

    @FXML
    void switchToSlideBar(MouseEvent event) {
    	progressBar.setVisible(false);
    	slidedBar.setVisible(true);
    }
    
    public void launchTimer () {
    	timer = new Timer();
    	task =new TimerTask() {
    		public void run() {
    			running = true;
    			current = mediaPlayer.getCurrentTime().toSeconds();
    			end = media.getDuration().toSeconds();
    			//System.out.println(Math.round(current/end * 100) +"%");
    			progressBar.setProgress(current/end);
    			if (changing == false) {
    				slidedBar.setValue(current/end * 100);	
    			}
    			
    			if (current==end)
    				cancelTimer();
    		}
    	};
    	timer.scheduleAtFixedRate(task, 0, 500);
    }
    
    public void cancelTimer() {
    	running=false;
    	timer.cancel();
    }
}
