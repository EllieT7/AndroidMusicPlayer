package com.example.app_crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Arrays;

public class Album implements Serializable {
    private transient int idAlbum;
    private transient String nombre;
    private transient Date fLanzamiento;
    private transient float precio;
    private transient Genero generos;
    private transient Artista artista;
    private transient byte[] src;
    private transient ArrayList<Cancion> listaCanciones;

    public Album(int idAlbum, String nombre, Date fLanzamiento, float precio,Genero generos, Artista artista, byte[] src, ArrayList<Cancion> listaCanciones) {
        this.idAlbum = idAlbum;
        this.nombre = nombre;
        this.fLanzamiento = fLanzamiento;
        this.precio = precio;
        this.generos = generos;
        this.artista = artista;
        this.src = src;
        this.listaCanciones = listaCanciones;
    }

    public Album(String nombre, Date fLanzamiento, float precio, Genero generos, Artista artista, byte[] src, ArrayList<Cancion> listaCanciones) {
        this.nombre = nombre;
        this.fLanzamiento = fLanzamiento;
        this.precio = precio;
        this.generos = generos;
        this.artista = artista;
        this.src = src;
        this.listaCanciones = listaCanciones;
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

    public byte[] getSrc() {
        return src;
    }

    public void setSrc(byte[] src) {
        this.src = src;
    }

    public Genero getGeneros() {
        return generos;
    }

    public void setGeneros(Genero generos) {
        this.generos = generos;
    }

    public ArrayList<Cancion> getListaCanciones() {
        return listaCanciones;
    }

    public void setListaCanciones(ArrayList<Cancion> listaCanciones) {
        this.listaCanciones = listaCanciones;
    }

    @Override
    public String toString() {
        return "Album{" +
                "idAlbum=" + idAlbum +
                ", nombre='" + nombre + '\'' +
                ", fLanzamiento=" + fLanzamiento +
                ", precio=" + precio +
                ", generos=" + generos +
                ", artista=" + artista +
                ", src=" + Arrays.toString(src) +
                ", listaCanciones=" + listaCanciones +
                '}';
    }
}
