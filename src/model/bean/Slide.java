package model.bean;

public class Slide {
	private int id;
	private String name;
	private String picture;
	private String link;
	private int sort;
	private int active;

	public Slide(int id, String name, String picture, String link, int sort, int active) {
		super();
		this.id = id;
		this.name = name;
		this.picture = picture;
		this.link = link;
		this.sort = sort;
		this.active = active;
	}

	public Slide() {
		super();
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Slide [id=" + id + ", name=" + name + ", picture=" + picture + ", link=" + link + ", sort=" + sort
				+ ", active=" + active + "]";
	}

}
