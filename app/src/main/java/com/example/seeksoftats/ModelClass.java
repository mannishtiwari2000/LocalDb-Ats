package com.example.seeksoftats;

public class ModelClass {
    public String tagId;
    public String assetUID;
    public String plant;
    public String costCenter;
    public String location;
    public String last_inv_on;
    public String assetGuid;
    public String assetDesc;
    public String status="Non";
    public String lastSeenLoc;
    public String performedBy;
    public String performedOn;
    public String serialNo;
    public String qty;
    public String businessArea;
    public String capitaliationDate;
    String Color;

    public ModelClass() {
    }

    public ModelClass(String tagId, String assetUID, String plant, String costCenter, String location, String last_inv_on, String assetGuid, String assetDesc, String status, String lastSeenLoc, String performedBy, String performedOn, String serialNo, String qty, String businessArea, String capitaliationDate, String color) {
        this.tagId = tagId;
        this.assetUID = assetUID;
        this.plant = plant;
        this.costCenter = costCenter;
        this.location = location;
        this.last_inv_on = last_inv_on;
        this.assetGuid = assetGuid;
        this.assetDesc = assetDesc;
        this.status = status;
        this.lastSeenLoc = lastSeenLoc;
        this.performedBy = performedBy;
        this.performedOn = performedOn;
        this.serialNo = serialNo;
        this.qty = qty;
        this.businessArea = businessArea;
        this.capitaliationDate = capitaliationDate;
        Color = color;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getAssetUID() {
        return assetUID;
    }

    public void setAssetUID(String assetUID) {
        this.assetUID = assetUID;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLast_inv_on() {
        return last_inv_on;
    }

    public void setLast_inv_on(String last_inv_on) {
        this.last_inv_on = last_inv_on;
    }

    public String getAssetGuid() {
        return assetGuid;
    }

    public void setAssetGuid(String assetGuid) {
        this.assetGuid = assetGuid;
    }

    public String getAssetDesc() {
        return assetDesc;
    }

    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastSeenLoc() {
        return lastSeenLoc;
    }

    public void setLastSeenLoc(String lastSeenLoc) {
        this.lastSeenLoc = lastSeenLoc;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getPerformedOn() {
        return performedOn;
    }

    public void setPerformedOn(String performedOn) {
        this.performedOn = performedOn;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getCapitaliationDate() {
        return capitaliationDate;
    }

    public void setCapitaliationDate(String capitaliationDate) {
        this.capitaliationDate = capitaliationDate;
    }




    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
