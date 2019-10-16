package model.bean;

import java.sql.Timestamp;

public class Comment {
	private int id;
	private String content;
	private int userId;
	private Timestamp dateCreate;
	private int parentId;
	private int newsId;
	private int active;

	public Comment(int id, String content, int userId, Timestamp dateCreate, int parentId, int newsId) {
		super();
		this.id = id;
		this.content = content;
		this.userId = userId;
		this.dateCreate = dateCreate;
		this.parentId = parentId;
		this.newsId = newsId;
	}

	public Comment(int id, String content, int userId, Timestamp dateCreate, int parentId, int newsId, int active) {
		super();
		this.id = id;
		this.content = content;
		this.userId = userId;
		this.dateCreate = dateCreate;
		this.parentId = parentId;
		this.newsId = newsId;
		this.active = active;
	}

	public Comment() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Timestamp dateCreate) {
		this.dateCreate = dateCreate;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", userId=" + userId + ", dateCreate=" + dateCreate
				+ ", parentId=" + parentId + ", newsId=" + newsId + ", active=" + active + "]";
	}

}
