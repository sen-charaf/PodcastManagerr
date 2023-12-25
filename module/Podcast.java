package module;

import java.sql.Date;
import java.sql.Time;

public class Podcast {
	private int id;
	private int playlist_id;
	private String title;
	private String imgsrc;
	private String hosts;
	private String description;
	private String release_scheduele;
	private String genres;
	private Date date;
	private Time duration;
	private String filepath;
	public Podcast() {
		super();
	}
	
	public Podcast(int id, String title, String imgsrc, String hosts, Date date, Time duration) {
		super();
		this.id = id;
		this.title = title;
		this.imgsrc = imgsrc;
		this.hosts = hosts;
		this.date = date;
		this.duration = duration;
	}

	public Podcast(int id, int playlist_id, String title, String imgsrc, String hosts, String description,
			String release_scheduele, String genres, Date date, Time duration,String filepath) {
		this.id = id;
		this.playlist_id = playlist_id;
		this.title = title;
		this.imgsrc = imgsrc;
		this.hosts = hosts;
		this.description = description;
		this.release_scheduele = release_scheduele;
		this.genres = genres;
		this.date = date;
		this.duration = duration;
		this.filepath=filepath;
	}
	@Override
	public String toString() {
		return "Podcast [id=" + id + ", playlist_id=" + playlist_id + ", title=" + title + ", imgsrc=" + imgsrc
				+ ", hosts=" + hosts + ", description=" + description + ", release_scheduele=" + release_scheduele
				+ ", genres=" + genres + ", date=" + date + ", duration=" + duration + "]";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPlaylist_id() {
		return playlist_id;
	}
	public void setPlaylist_id(int playlist_id) {
		this.playlist_id = playlist_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getHosts() {
		return hosts;
	}
	public void setHosts(String hosts) {
		this.hosts = hosts;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRelease_scheduele() {
		return release_scheduele;
	}
	public void setRelease_scheduele(String release_scheduele) {
		this.release_scheduele = release_scheduele;
	}
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getDuration() {
		return duration;
	}
	public void setDuration(Time duration) {
		this.duration = duration;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	
}
