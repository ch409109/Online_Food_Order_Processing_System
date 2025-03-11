package com.example.prm392_final_project.data.model;

public class Review {
    private int id;
    private int userId;
    private int foodId;
    private int rating;
    private String feedback;
    private String createdAt;

    public Review(int id, int userId, int foodId, int rating, String feedback, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.foodId = foodId;
        this.rating = rating;
        this.feedback = feedback;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
