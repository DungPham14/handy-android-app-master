package com.renosys.handy.data.task;

/**
 * Created by linhnm on 11/9/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Set {

    @SerializedName("frameCode")
    @Expose
    private String frameCode;
    @SerializedName("detail")
    @Expose
    private SetDetail detail;

    public String getFrameCode() {
        return frameCode;
    }

    public void setFrameCode(String frameCode) {
        this.frameCode = frameCode;
    }

    public SetDetail getDetail() {
        return detail;
    }

    public void setDetail(SetDetail detail) {
        this.detail = detail;
    }

}