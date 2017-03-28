package com.quascenta.petersroad.droidtag.core;

import com.pedrogomez.renderers.AdapteeCollection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by AKSHAY on 3/27/2017.
 */
public class UserCollection implements AdapteeCollection<UserViewModel> {


    private final List<UserViewModel> sensorViewModelList;

    public UserCollection() {
        this.sensorViewModelList = new LinkedList<UserViewModel>();
    }


    @Override
    public int size() {
        return sensorViewModelList.size();
    }


    @Override
    public UserViewModel get(int index) {
        return sensorViewModelList.get(index);
    }


    @Override
    public boolean add(UserViewModel element) {
        if (element != null) {
            sensorViewModelList.add(element);
            return true;
        } else return false;
    }


    @Override
    public boolean remove(Object element) {
        sensorViewModelList.remove(element);
        return true;
    }


    @Override
    public boolean addAll(Collection<? extends UserViewModel> elements) {
        if (elements == null) {
            return false;
        } else {
            sensorViewModelList.addAll(elements);
            return true;
        }
    }

    @Override
    public boolean removeAll(Collection<?> elements) {
        return false;
    }


    @Override
    public void clear() {

    }

}