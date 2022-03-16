package com.example.app_crud;

public class Cancion {
    private int idCancion;
    private String titulo;
    private String duracion;

    public Cancion(int idCancion, String titulo, String duracion) {
        this.idCancion = idCancion;
        this.titulo = titulo;
        this.duracion = duracion;
    }

    public Cancion(String titulo, String duracion) {
        this.titulo = titulo;
        this.duracion = duracion;
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

    @Override
    public String toString() {
        return "Cancion{" +
                "idCancion=" + idCancion +
                ", titulo='" + titulo + '\'' +
                ", duracion='" + duracion + '\'' +
                '}';
    }
}
