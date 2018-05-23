package com.main.dao.comm.entity;

import java.util.HashMap;

public class Dict {
    /**自动增长的主键*/
    private Integer id;

    /**约束的键*/
    private String name;

    /**该键所代表的含义*/
    private String value;

    /**备注*/
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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
		return "Dict [id=" + id + ", name=" + name + ", value=" + value
				+ ", remark=" + remark + "]"+System.getProperty("line.separator");
	}
    
}