package com.gatyatmakjyotish.pojo;

public class DateCategory {
    private Integer id;
    private String date;
    private String feeling;
    private String description;

    public DateCategory(String feeling,String description) {
        this.id = id;
        this.date = date;
        this.feeling = feeling;
        this.description=description;
    }

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
    public String getFeeling(){
        return feeling;
    }
    public String getDescription(){
        return description;
    }
}
