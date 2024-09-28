package com.example.elijahschool;

public class Option {
    private String name;      // Option name
    private int imageResId;   // Resource ID or file path for the image
    private String category;  // Primary category (like "Valgyti", "Eiti", etc.)

    // Constructor for creating a new option
    public Option(String name, int imageResId, String category) {
        this.name = name;
        this.imageResId = imageResId;
        this.category = category;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
