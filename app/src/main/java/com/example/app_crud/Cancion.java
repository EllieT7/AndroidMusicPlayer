package com.example.app_crud;

public class Cancion {
    private int idCancion;
    private String titulo;
    private String duracion;
    private Album album;

    public Cancion(int idCancion, String titulo, String duracion, Album album) {
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.duracion = duracion;
        this.album = album;
    }

    public Cancion(String titulo, String duracion, Album album) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.album = album;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Album getAlbum() {
        return album;
    }

    public void setIdAlbum(Album album) {
        this.album = album;
    }
}
