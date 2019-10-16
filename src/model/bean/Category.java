package model.bean;

public class Category {
	private int id;
	private String name;
	private int parentCategoryId;
	//2.
//	private Category parentCategory;

	public Category(int id, String name, int parentCategoryId) {
		super();
		this.id = id;
		this.name = name;
		this.parentCategoryId = parentCategoryId;
	}

	public Category() {
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

	public int getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(int parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", parentCategoryId=" + parentCategoryId + "]";
	}

}
