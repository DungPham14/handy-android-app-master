package com.renosys.handy.data.task;

/**
 * Created by linhnm on 11/9/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("typeCode")
    @Expose
    private String typeCode;
    @SerializedName("itemCode")
    @Expose
    private String itemCode;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

}