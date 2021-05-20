package com.xWash.dao;

import com.xWash.entity.Feedback;

import java.util.ArrayList;

public interface FeedbackDao {
    public ArrayList<Feedback> getAll();
    public Feedback getTextById(int id);
    public Feedback getTextByOwner(String owner);
    public boolean saveFeedback(Feedback fb);
}
