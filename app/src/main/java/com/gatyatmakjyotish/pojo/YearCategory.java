package com.gatyatmakjyotish.pojo;

public class YearCategory {

    private Integer id;
    private String name;
    private String title;
    private String date,description;

    public YearCategory(int id,String name,String title){
        this.id=id;
        this.name=name;
        this.title=title;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
