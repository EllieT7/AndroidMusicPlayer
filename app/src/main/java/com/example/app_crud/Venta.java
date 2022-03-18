package com.example.app_crud;

import java.util.ArrayList;

public class Venta {
    private int idVenta;
    private String ci;
    private String cliente;
    private String ubicacion;
    private Album album;

    public Venta(int idVenta, String ci, String cliente, String ubicacion, Album album) {
        this.idVenta = idVenta;
        this.ci = ci;
        this.cliente = cliente;
        this.ubicacion = ubicacion;
        this.album = album;
    }

    public Venta(String ci, String cliente, String ubicacion, Album album) {
        this.ci = ci;
        this.cliente = cliente;
        this.ubicacion = ubicacion;
        this.album = album;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
