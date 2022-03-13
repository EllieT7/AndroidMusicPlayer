package com.example.app_crud;

import java.util.ArrayList;

public class Artista {
    private int idArtista;
    private String nombre;
    private String descripcion;


    public Artista(int idArtista, String nombre, String descripcion) {
        this.idArtista = idArtista;
        this.nombre = nombre;
        this.descripcion = descripcion;

    }

    public Artista(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;

    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
