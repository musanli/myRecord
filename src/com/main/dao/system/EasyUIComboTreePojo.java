package com.main.dao.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EasyUIComboTreePojo {
	private String id;
	private String pid;
	private String state;
	private String text;
	// private String iconCls ;
	// private String checked ;
	private List<EasyUIComboTreePojo> children ;

	public EasyUIComboTreePojo() {
	}

	public EasyUIComboTreePojo(String id, String pid, String state,
			String text, List<EasyUIComboTreePojo> children) {
		this.id = id;
		this.pid = pid;
		this.state = state;
		this.text = text;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<EasyUIComboTreePojo> getChildren() {
		return children;
	}

	public void setChildren(List<EasyUIComboTreePojo> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "EasyUIComboTreePojo [id=" + id + ", pid=" + pid + ", state="
				+ state + ", text=" + text + ", children=" + children + "]"
				+ System.getProperty("separator.line");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EasyUIComboTreePojo other = (EasyUIComboTreePojo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
