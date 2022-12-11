package com.example.myapplication.data;


import java.io.Serializable;

public class BookDetails implements Serializable {
    public BookDetails(String title, int resourceId,String author,String translator,String publisher,int pubYear,int pubMonth,String ISBN,String status,String bookShelf,String notes,String law,String hyperlink,int position) {
        this.title = title;
        this.resourceId = resourceId;
        this.author = author;
        this.translator = translator;
        this.publisher = publisher;
        this.pubYear = pubYear;
        this.pubMonth = pubMonth;
        this.ISBN = ISBN;
        this.status = status;
        this.bookShelf = bookShelf;
        this.notes = notes;
        this.law = law;
        this.hyperlink = hyperlink;
        this.position = position;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getAuthor(){return author;}

    public void setAuthor(String author){this.author = author;}

    public String getTranslator(){return translator;}

    public void setTranslator(String translator){this.translator = translator;}

    public String getPublisher(){return publisher;}

    public void setPublisher(String publisher){this.publisher = publisher;}

    public int getPubYear(){return pubYear;}

    public void setPubYear(int pubYear){this.pubYear = pubYear;}

    public int getPubMonth(){return pubMonth;}

    public void setPubMonth(int pubMonth){this.pubMonth = pubMonth;}

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookShelf() {
        return bookShelf;
    }

    public void setBookShelf(String bookShelf) {
        this.bookShelf = bookShelf;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public String getLaw() {
        return law;
    }

    public void setLaw(String law) {
        this.law = law;
    }

    private String title;
    private Double price;
    private int resourceId;
    private String author;
    private  String translator;
    private  String publisher;
    private  int pubYear;
    private  int pubMonth;
    private String ISBN;
    private String status;
    private String bookShelf;
    private String notes;
    private String law;
    private String hyperlink;
    int position;
}
