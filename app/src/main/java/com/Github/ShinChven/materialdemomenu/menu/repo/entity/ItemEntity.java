package com.Github.ShinChven.materialdemomenu.menu.repo.entity;

import java.util.Date;

/**
 * Created by ShinChven on 2014/12/5.
 */
public class ItemEntity {
    private int _ID;
    private int ITEM_ID;
    private String ITEM_NAME;
    private String DESCRIPTION;
    private int CATEGORY_ID;
    private float PRICE;
    private int ITEM_STATUS;
    private String IMG;
    private Date UPDATE_TIME;

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public int getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(int ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public int getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public void setCATEGORY_ID(int CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }

    public int getITEM_STATUS() {
        return ITEM_STATUS;
    }

    public float getPRICE() {
        return PRICE;
    }

    public void setPRICE(float PRICE) {
        this.PRICE = PRICE;
    }

    public void setITEM_STATUS(int ITEM_STATUS) {
        this.ITEM_STATUS = ITEM_STATUS;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public Date getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(Date UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }

    private int orderedCount = 1;
    private boolean isDisplayed = false;

    public int getOrderedCount() {
        return orderedCount;
    }

    public void setOrderedCount(int orderedCount) {
        this.orderedCount = orderedCount;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public void setDisplayed(boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }


}
