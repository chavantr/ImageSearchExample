package com.microsoft.imageseach.Model;

/**
 * Created by Tatyabhau Chavan on 5/3/2016.
 */
public class ImageSearch {

    private String batchcomplete;
    private Continue aContinue;
    private Query query;


    public Continue getaContinue() {
        return aContinue;
    }

    public void setaContinue(Continue aContinue) {
        this.aContinue = aContinue;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }


    public String getBatchcomplete() {
        return batchcomplete;
    }

    public void setBatchcomplete(String batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

}
