/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author HP
 */
public class Author {
    private int authId;
    private String name;
    private String biography;

    public Author() {
    }

    public Author(int authId, String name, String biography) {
        this.authId = authId;
        this.name = name;
        this.biography = biography;
        
    }

    public int getAuthId() {
        return authId;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
  
    
}
