package com.Github.ShinChven.materialdemomenu.menu.repo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ShinChven on 2014/12/5.
 */
public class CategoryEntity {
    private int _ID;
    private int CATEGORY_ID;
    private String CATE_NAME;
    private String IMG;
    private Date UPDATE_TIME;
    private int PARENT_ID;

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public int getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public void setCATEGORY_ID(int CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }

    public String getCATE_NAME() {
        return CATE_NAME;
    }

    public void setCATE_NAME(String CATE_NAME) {
        this.CATE_NAME = CATE_NAME;
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

    public int getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(int PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    private List<ItemEntity> items = new ArrayList<ItemEntity>();

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }
}
