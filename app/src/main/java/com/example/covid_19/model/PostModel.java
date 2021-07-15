package com.example.covid_19.model;

public class PostModel {
    private int userId, id;
    private String title, body;

    public PostModel(int userId, int id, String name, String body) {
        this.userId = userId;
        this.id = id;
        this.title = name;
        this.body = body;
    }

    public PostModel() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
