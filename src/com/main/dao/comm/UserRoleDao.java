package com.main.dao.comm;

public class UserRoleDao {
	private String name;
	private String id;
	private String setting;
	private String menutype;
	
	public UserRoleDao() {
		super();
	}
	public UserRoleDao(String name, String id, String setting, String menutype) {
		super();
		this.name = name;
		this.id = id;
		this.setting = setting;
		this.menutype = menutype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMenutype() {
		return menutype;
	}
	public void setMenutype(String menutype) {
		this.menutype = menutype;
	}
	
	public String getSetting() {
		return setting;
	}
	public void setSetting(String setting) {
		this.setting = setting;
	}
	@Override
	public String toString() {
		return "UserRoleDao [name=" + name + ", id=" + id + ", category="
				+ setting + ", menutype=" + menutype + "]";
	}
	
}
