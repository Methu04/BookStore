/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;



/**
 *
 * @author HP
 */
public class Book {
    private String title;
    private int authId;
    private String ISBN;
    private int year;
    private double price;
    private int stockQuantity;
    private int bookId;

    public Book() {
    }

    public Book(int bookId, String title, int authId, String ISBN, int year, double price, int stockQuantity) {
        this.bookId=bookId;
        this.title = title;
        this.authId = authId;
        this.ISBN = ISBN;
        this.year = year;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public String getTitle() {
        return title;
    }

    public int getAuthId() {
        return authId;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    @Override
    public String toString() {
        return "{\"bookId\":" + bookId + 
               ",\"title\":\"" + title + 
               "\",\"authorId\":" + authId +
               ",\"isbn\":\"" + ISBN + 
               "\",\"year\":" + year + 
               ",\"price\":" + price + 
               ",\"quantity\":" + stockQuantity + "}";
}

    

    
   
   
}
