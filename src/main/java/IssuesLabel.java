package main.java;

public class IssuesLabel {
	long id;
	String url;
	String name;
	String color;

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public IssuesLabel(long id, String url, String name, String color) {
		this.id = id;
		this.url = url;
		this.name = name;
		this.color = color;
	}

}
