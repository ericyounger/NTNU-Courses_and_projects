package com.example.lagring;

public class Book {
    private String title;
    private String author;
    private String summary;
    private String content;

    public Book(String title, String author, String summary, String content){
        this.title = title;
        this.author = author;
        this. summary = summary;
        this.content = content;
    }

    public Book(String[] args){
        this.title = args[0];
        this.author = args[1];
        this.summary = args[2];
        this.content = args[3];
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }
}
