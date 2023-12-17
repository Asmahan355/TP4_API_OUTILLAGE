package com.example.tp4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private Date creation;
    private String content;

    private String auteur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Address() {
    }

    public Address(Long id, Date creation, String content, String auteur) {
        this.id = id;
        this.creation = creation;
        this.content = content;
        this.auteur = auteur;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", creation=" + creation +
                ", content='" + content + '\'' +
                '}';
    }
}