package com.renosys.handy.data.task;

/**
 * Created by linhnm on 11/9/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Task {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("table")
    @Expose
    private Table table;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("elapsed")
    @Expose
    private Integer elapsed;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getElapsed() {
        return elapsed;
    }

    public void setElapsed(Integer elapsed) {
        this.elapsed = elapsed;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

}