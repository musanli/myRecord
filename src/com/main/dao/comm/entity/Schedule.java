package com.main.dao.comm.entity;

import java.util.HashMap;

public class Schedule {
    /**����*/
    private String id;

    /**�ճ̴�������*/
    private String createdate;

    /**����ʼʱ��*/
    private String taskstarttime;

    /**�������ʱ��*/
    private String taskendtime;

    /**����ʼ����*/
    private String taskstartdate;

    /**�����������*/
    private String taskenddate;

    /**�ճ̴���ʱ��*/
    private String cratetime;

    /**����ճ̱�ʶ*/
    private String finish;

    /**�ճ����*/
    private String sort;
    private String isdelete;

    public String getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public String getTaskstarttime() {
        return taskstarttime;
    }

    public void setTaskstarttime(String taskstarttime) {
        this.taskstarttime = taskstarttime == null ? null : taskstarttime.trim();
    }

    public String getTaskendtime() {
        return taskendtime;
    }

    public void setTaskendtime(String taskendtime) {
        this.taskendtime = taskendtime == null ? null : taskendtime.trim();
    }

    public String getTaskstartdate() {
        return taskstartdate;
    }

    public void setTaskstartdate(String taskstartdate) {
        this.taskstartdate = taskstartdate == null ? null : taskstartdate.trim();
    }

    public String getTaskenddate() {
        return taskenddate;
    }

    public void setTaskenddate(String taskenddate) {
        this.taskenddate = taskenddate == null ? null : taskenddate.trim();
    }

    public String getCratetime() {
        return cratetime;
    }

    public void setCratetime(String cratetime) {
        this.cratetime = cratetime == null ? null : cratetime.trim();
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish == null ? null : finish.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public HashMap getPrimaryKey() {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id",this.id);
        return map;
    }
}