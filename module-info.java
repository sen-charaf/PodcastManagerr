module Podcast_GS {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires fontawesomefx;
	requires javafx.base;
	requires java.sql;
	requires javafx.media;
	
	
	opens application to javafx.graphics, javafx.fxml;
}
