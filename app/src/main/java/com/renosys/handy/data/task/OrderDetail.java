package com.renosys.handy.data.task;

/**
 * Created by linhnm on 11/9/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("menuCode")
    @Expose
    private String menuCode;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("addons")
    @Expose
    private List<Addon> addons = null;
    @SerializedName("sets")
    @Expose
    private List<Set> sets = null;
    @SerializedName("timing")
    @Expose
    private String timing;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("canceledAt")
    @Expose
    private String canceledAt;
    @SerializedName("deliveredAt")
    @Expose
    private String deliveredAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Addon> getAddons() {
        return addons;
    }

    public void setAddons(List<Addon> addons) {
        this.addons = addons;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(String canceledAt) {
        this.canceledAt = canceledAt;
    }

    public String getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(String deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

}