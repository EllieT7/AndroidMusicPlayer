package com.example.app_crud;

import java.util.ArrayList;
import java.sql.Date;

public class Album {
    private int idAlbum;
    private String nombre;
    private Date fLanzamiento;
    private float precio;
    private ArrayList<Genero> generos;
    private Artista artista;
    private String src;

    public Album(int idAlbum, String nombre, Date fLanzamiento, float precio, ArrayList<Genero> generos, Artista artista, String src) {
        this.idAlbum = idAlbum;
        this.nombre = nombre;
        this.fLanzamiento = fLanzamiento;
        this.precio = precio;
        this.generos = generos;
        this.artista = artista;
        this.src = src;
    }

    public Album(String nombre, Date fLanzamiento, float precio, ArrayList<Genero> generos, Artista artista, String src) {
        this.nombre = nombre;
        this.fLanzamiento = fLanzamiento;
        this.precio = precio;
        this.generos = generos;
        this.artista = artista;
        this.src = src;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getfLanzamiento() {
        return fLanzamiento;
    }

    public void setfLanzamiento(Date fLanzamiento) {
        this.fLanzamiento = fLanzamiento;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
