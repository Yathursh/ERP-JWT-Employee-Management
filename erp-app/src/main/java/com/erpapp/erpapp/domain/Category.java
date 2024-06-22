package com.erpapp.erpapp.domain;

public class Category {

    private Integer category_id;
    private Integer emp_id;
    private String title;
    private String description;

    public Category(Integer category_id, Integer emp_id, String title, String description) {
        this.category_id = category_id;
        this.emp_id = emp_id;
        this.title = title;
        this.description = description;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
