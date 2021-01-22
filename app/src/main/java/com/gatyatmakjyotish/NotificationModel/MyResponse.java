package com.gatyatmakjyotish.NotificationModel;

import java.util.List;

/**
 * Created by hp on 4/15/2018.
 */

public class MyResponse  {
    public long mukticast_id;
    public int sucess;
    public int failure;
    public int canonical_ids;
    public List<Result> results;

    public MyResponse() {
    }

    public MyResponse(long mukticast_id, int sucess, int failure, int canonical_ids, List<Result> results) {
        this.mukticast_id = mukticast_id;
        this.sucess = sucess;
        this.failure = failure;
        this.canonical_ids = canonical_ids;
        this.results = results;
    }

    public long getMukticast_id() {
        return mukticast_id;
    }

    public void setMukticast_id(long mukticast_id) {
        this.mukticast_id = mukticast_id;
    }

    public int getSucess() {
        return sucess;
    }

    public void setSucess(int sucess) {
        this.sucess = sucess;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(int canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
