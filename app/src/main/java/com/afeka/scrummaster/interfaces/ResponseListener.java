package com.afeka.scrummaster.interfaces;

public interface ResponseListener<T> {
    public void onRes(T res);
    public void onError(Exception e);
}
