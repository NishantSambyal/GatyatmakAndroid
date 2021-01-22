package com.gatyatmakjyotish.pojo;

public class YearResult {
    private String date;
    private String feeling;
    private String description;

    public YearResult(String feeling, String description) {
        this.feeling = feeling;
        this.description = description;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ResultCategory{" +
                "name='" + date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
