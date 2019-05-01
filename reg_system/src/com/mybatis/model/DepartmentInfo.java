package com.mybatis.model;

public class DepartmentInfo {
    private String id;

    private String dName;

    private String dInfo;

    private String dLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName == null ? null : dName.trim();
    }

    public String getdInfo() {
        return dInfo;
    }

    public void setdInfo(String dInfo) {
        this.dInfo = dInfo == null ? null : dInfo.trim();
    }

    public String getdLocation() {
        return dLocation;
    }

    public void setdLocation(String dLocation) {
        this.dLocation = dLocation == null ? null : dLocation.trim();
    }
}