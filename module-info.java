module Podcast_GS {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires fontawesomefx;
	requires javafx.base;
	
	
	opens application to javafx.graphics, javafx.fxml;
}
