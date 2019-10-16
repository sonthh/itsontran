package model.bean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Dell
 *
 */
public class News {
	private int id;
	private String name;
	private String preview;
	private String detail;
	private Timestamp dateCreate;
	private int createBy;
	private String picture;
	private int views;
	private Category category;
	private int active;

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy", Locale.US);
		Date d = new Date();
		System.out.println(sdf.format(d));
	}
	
	public News(int id, String name, String preview, String detail, Timestamp dateCreate, int createBy, String picture,
			int views, Category category, int active) {
		super();
		this.id = id;
		this.name = name;
		this.preview = preview;
		this.detail = detail;
		this.dateCreate = dateCreate;
		this.createBy = createBy;
		this.picture = picture;
		this.views = views;
		this.category = category;
		this.active = active;
	}

	public News(int id, String name, String preview, String detail, Timestamp dateCreate, int createBy, String picture,
			Category category, int active) {
		super();
		this.id = id;
		this.name = name;
		this.preview = preview;
		this.detail = detail;
		this.dateCreate = dateCreate;
		this.createBy = createBy;
		this.picture = picture;
		this.category = category;
		this.active = active;
	}

	public News() {
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

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Timestamp getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Timestamp dateCreate) {
		this.dateCreate = dateCreate;
	}

	public int getCreateBy() {
		return createBy;
	}

	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", name=" + name + ", preview=" + preview + ", detail=" + detail + ", dateCreate="
				+ dateCreate + ", createBy=" + createBy + ", picture=" + picture + ", views=" + views + ", category="
				+ category + ", active=" + active + "]";
	}

	

}
