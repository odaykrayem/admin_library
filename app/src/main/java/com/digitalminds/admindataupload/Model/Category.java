package com.digitalminds.admindataupload.Model;

public class Category {


    private String categoryId;
    private String categoryNameEN;
    private String categoryNameAR;
    private String categoryNameKU;
    private String categoryIcon;


    public Category() {
    }

    public Category(String categoryId, String categoryNameEN, String cateoryNameAR, String categoryNameKURD, String categoryIcon) {
        this.categoryId = categoryId;
        this.categoryNameEN = categoryNameEN;
        this.categoryNameAR = cateoryNameAR;
        this.categoryNameKU = categoryNameKURD;
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryNameEN() {
        return categoryNameEN;
    }

    public void setCategoryNameEN(String categoryNameEN) {
        this.categoryNameEN = categoryNameEN;
    }

    public String getCategoryNameAR() {
        return categoryNameAR;
    }

    public void setCategoryNameAR(String categoryNameAR) {
        this.categoryNameAR = categoryNameAR;
    }

    public String getCategoryNameKU() {
        return categoryNameKU;
    }

    public void setCategoryNameKU(String categoryNameKU) {
        this.categoryNameKU = categoryNameKU;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }


}