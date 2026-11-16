
package com.example.digitalib;

public class Book {

    private String id;
    private String title;
    private String author;
    private String category;
    private String description;
    private boolean available;

    public Book(String id, String title, String author,
                String category, String description,
                boolean available) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
