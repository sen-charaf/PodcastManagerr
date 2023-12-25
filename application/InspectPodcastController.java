package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class InspectPodcastController  {

    @FXML
    private Button DeleteButtonID;

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
    @FXML
    void initialize() {
    	System.out.println("here");
    	podcasts = new ArrayList<File>();
    	diractory = new File("F:\\.javaprojects\\Podcast_GS\\src\\podcastsFiles"); // hadi atbdl
    	files = diractory.listFiles();
    	System.out.println(diractory.canRead());
    	if (files!=null) {
    		for (File file : files) {
				podcasts.add(file);
				System.out.println(file);
			}
    	}
    	media = new Media(podcasts.get(podcastNumber).toURI().toString());
    	mediaPlayer = new MediaPlayer(media);
    	PodcastTitlaID.setText(podcasts.get(podcastNumber).getName()); // hadi atbdl
    	TitreID.setText(podcasts.get(podcastNumber).getName());
    	slidedBar.valueChangingProperty().addListener((observable, oldValue, newValue)->{
    		System.out.println(observable+","+oldValue+","+newValue);
    		System.out.println("Start Dragging");
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
    	PodcastTitlaID.setText(podcasts.get(podcastNumber).getName()); // hadi atbdl
    	TitreID.setText(podcasts.get(podcastNumber).getName());
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
    	PodcastTitlaID.setText(podcasts.get(podcastNumber).getName()); // hadi atbdl
    	TitreID.setText(podcasts.get(podcastNumber).getName());
    }
    @FXML
    void changePodcastTimeline(Event event) {
    	
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
    	timer.scheduleAtFixedRate(task, 1000, 500);
    }
    
    public void cancelTimer() {
    	running=false;
    	timer.cancel();
    }
}
