package com.afeka.scrummaster;

public interface ResponseListener<T> {
    public void onRes(T res);
    public void onError(Exception e);
}
