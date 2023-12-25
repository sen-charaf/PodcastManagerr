package module;

public class Playlist {
	private int id;
	private String name;
	private String imgsrc;
	private String smallDescription;
	
	public Playlist() {
		super();
	}
	
	public Playlist(int id, String name, String imgsrc, String smallDescription) {
		this.id = id;
		this.name = name;
		this.imgsrc = imgsrc;
		this.smallDescription = smallDescription;
	}
	
	@Override
	public String toString() {
		return "Playlist [id=" + id + ", name=" + name + ", imgsrc=" + imgsrc + ", smallDescription=" + smallDescription
				+ "]";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getSmallDescription() {
		return smallDescription;
	}
	public void setSmallDescription(String smallDescription) {
		this.smallDescription = smallDescription;
	}
	
}
