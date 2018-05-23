package com.main.dao.comm.entity;

import java.util.HashMap;

public class Diary {
    /**该信息的唯一标识*/
    private String diaryid;

    /**创建日期*/
    private String createdate;

    /**创建时间*/
    private String createtime;

    /**该记录的titel*/
    private String diarytitle;

    /**包含人物信息，关联表*/
    private String resumeid;

    public String getDiaryid() {
        return diaryid;
    }

    public void setDiaryid(String diaryid) {
        this.diaryid = diaryid == null ? null : diaryid.trim();
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getDiarytitle() {
        return diarytitle;
    }

    public void setDiarytitle(String diarytitle) {
        this.diarytitle = diarytitle == null ? null : diarytitle.trim();
    }

    public String getResumeid() {
        return resumeid;
    }

    public void setResumeid(String resumeid) {
        this.resumeid = resumeid == null ? null : resumeid.trim();
    }

    public HashMap getPrimaryKey() {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("diaryid",this.diaryid);
        return map;
    }
}