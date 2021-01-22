package com.gatyatmakjyotish.pojo;

import java.util.List;

public class ResultCategory {
    private String date;
    private String feeling;
    private String description;
    private String fromdate;
    private String todate;
    private List<YearResult> yearResults;

    public ResultCategory() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public List<YearResult> getYearResults() {
        return yearResults;
    }

    public void setYearResults(List<YearResult> yearResults) {
        this.yearResults = yearResults;
    }

    @Override
    public String toString() {
        return "ResultCategory{" +
                "name='" + date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
