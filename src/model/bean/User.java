package model.bean;

public class User {
	private int id;
	private String username;
	private String password;
	private String fullname;
	private int active;
	private UserPermissions userPermissions;

	public User(int id, String username, String password, String fullname, int active) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.active = active;
	}

	public User(int id, String username, String password, String fullname, int active,
			UserPermissions userPermissions) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.active = active;
		this.userPermissions = userPermissions;
	}

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public UserPermissions getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(UserPermissions userPermissions) {
		this.userPermissions = userPermissions;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", fullname=" + fullname
				+ ", active=" + active + ", userPermissions=" + userPermissions + "]";
	}

}
