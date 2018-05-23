package com.main.dao.comm.entity;

import java.util.HashMap;

public class MenuManage {
    /**节点id*/
    private String id;

    /**父节点*/
    private String pid;

    /**节点名称*/
    private String name;

    /**节点路径*/
    private String url;

    /**节点类别*/
    private String category;

    /**父节点标识*/
    private String isparent;

    /**是否显示*/
    private String isdisplay;

    /**备注_暂且用来记录节点等级*/
    private String remark;
    

    
    


	public MenuManage() {
		super();
	}

	public MenuManage(String id, String pid, String name, String url,
			String category, String isparent, String isdisplay, String remark) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.url = url;
		this.category = category;
		this.isparent = isparent;
		this.isdisplay = isdisplay;
		this.remark = remark;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getIsparent() {
        return isparent;
    }

    public void setIsparent(String isparent) {
        this.isparent = isparent == null ? null : isparent.trim();
    }

    public String getIsdisplay() {
        return isdisplay;
    }

    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay == null ? null : isdisplay.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public HashMap getPrimaryKey() {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id",this.id);
        return map;
    }

	@Override
	public String toString() {
		return "MenuManage [id=" + id + ", pid=" + pid + ", name=" + name
				+ ", url=" + url + ", category=" + category + ", isparent="
				+ isparent + ", isdisplay=" + isdisplay + ", remark=" + remark
				+ "]";
	}
    
}