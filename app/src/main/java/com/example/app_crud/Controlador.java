package com.example.app_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

public class Controlador {
    Helper helper;

    public Controlador(Context context) {
        helper = new Helper(context);
    }

    //Create

    public long createGenero(Genero genero){
        SQLiteDatabase sql = helper.getWritableDatabase();
        ContentValues valInsert = new ContentValues();
        valInsert.put("descripcion", genero.getDescripcion());
        return sql.insert("genero",null, valInsert);
    }

    public long createAlbum(Album album){
        SQLiteDatabase sql = helper.getWritableDatabase();
        ContentValues valInsert = new ContentValues();
        valInsert.put("nombre", album.getNombre());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //Dando formato
        valInsert.put("f_lanzamiento", dateFormat.format(album.getfLanzamiento()));
        valInsert.put("precio", album.getPrecio());
        valInsert.put("src",album.getSrc());
        valInsert.put("artista_id_artista", album.getArtista().getIdArtista());
        valInsert.put("genero_id_genero", album.getGeneros().getIdGenero());
        return sql.insert("album",null,valInsert);


    }

    public long createArtista(Artista artista){
        SQLiteDatabase sql = helper.getWritableDatabase();
        ContentValues valInsert = new ContentValues();
        valInsert.put("nombre", artista.getNombre());
        valInsert.put("descripcion", artista.getDescripcion());
        return sql.insert("artista",null,valInsert);
    }

    public long createCancion(Cancion cancion){
        SQLiteDatabase sql = helper.getWritableDatabase();
        ContentValues valInsert = new ContentValues();
        valInsert.put("titulo", cancion.getTitulo());
        valInsert.put("duracion", cancion.getDuracion());
        valInsert.put("album_id_album", cancion.getAlbum().getIdAlbum());
        return sql.insert("cancion",null,valInsert);
    }

    //Read
    public ArrayList<Genero> readGeneros(){
        ArrayList<Genero> listaGeneros = new ArrayList<>();
        SQLiteDatabase sql = helper.getReadableDatabase();
        String [] columnasConsultadas = {"id_genero", "descripcion"};
        Cursor cursor = sql.query(
                "genero",
                columnasConsultadas,
                null,
                null,
                null,
                null,
                "descripcion"
        );

        if(cursor==null){
            return listaGeneros;
        }
        if(!cursor.moveToFirst()){
            return listaGeneros;
        }

        do{
            int id = cursor.getInt(0);
            String descripcion = cursor.getString(1);
            System.out.println("registroo: "+id+" | "+descripcion);
            Genero genero = new Genero(id,descripcion);
            listaGeneros.add(genero);
        }while(cursor.moveToNext());
        cursor.close();
        return listaGeneros;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Album> readAllAlbum() throws ParseException {
        ArrayList<Album> listaAlbumes = new ArrayList<>();
        SQLiteDatabase sql = helper.getReadableDatabase();
        String [] columnasConsultadas = {"id_album", "nombre", "f_lanzamiento", "precio","src","artista_id_artista","genero_id_genero"};
        Cursor cursor = sql.query(
                "album",
                columnasConsultadas,
                null,
                null,
                null,
                null,
                "nombre"
        );

        if(cursor==null){
            return listaAlbumes;
        }
        if(!cursor.moveToFirst()){
            return listaAlbumes;
        }

        do{
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String stringDate = cursor.getString(2);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            java.util.Date fechaUtil = dateFormat.parse(stringDate);
            java.sql.Date fecha = new Date(fechaUtil.getTime());
            Float precio = cursor.getFloat(3);
            byte[] src= cursor.getBlob(4);
            int idArtista = cursor.getInt(5);
            int idGenero = cursor.getInt(6);

            Album album = new Album(id,nombre,fecha,precio,getGenero(idGenero), getArtistaAlbum(idArtista), src);
            listaAlbumes.add(album);
        }while(cursor.moveToNext());
        cursor.close();
        return listaAlbumes;
    }

    //Select
    //Busca artista por el id de un album
    public Artista getArtistaAlbum(int idArtista){
        Artista artista = null;
        SQLiteDatabase sql = helper.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from artista where id_artista = "+idArtista,null);
        if(cursor==null){
            return artista;
        }
        if(!cursor.moveToFirst()){
            return artista;
        }
        do{
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String descripcion = cursor.getString(2);
            artista = new Artista(id,nombre,descripcion);
        }while(cursor.moveToNext());
        cursor.close();
        return artista;
    }

    //Busca la lista de generos que pertenecen al album
    public  Genero getGenero(int idGenero){
        Genero genero = null;
        SQLiteDatabase sql = helper.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from genero where id_genero = "+idGenero,null);
        if(cursor==null){
            return genero;
        }
        if(!cursor.moveToFirst()){
            return genero;
        }
        do{
            int id = cursor.getInt(0);
            String descripcion = cursor.getString(1);
            genero = new Genero(id, descripcion);
        }while(cursor.moveToNext());
        cursor.close();
        return genero;
    }

    //Leer todos los generos
    public ArrayList<Genero> readAllGeneros(){
        ArrayList<Genero> listaGeneros = new ArrayList<>();
        SQLiteDatabase sql = helper.getReadableDatabase();
        String [] columnasConsultadas = {"id_genero", "descripcion"};
        Cursor cursor = sql.query(
                "genero",
                columnasConsultadas,
                null,
                null,
                null,
                null,
                "id_genero"
        );

        if(cursor==null){
            return listaGeneros;
        }
        if(!cursor.moveToFirst()){
            return listaGeneros;
        }

        do{
            int id = cursor.getInt(0);
            String descripcion = cursor.getString(1);

            Genero genero = new Genero(id,descripcion);
            listaGeneros.add(genero);
        }while(cursor.moveToNext());
        cursor.close();
        return listaGeneros;
    }
    //Leer todos los artistas
    public ArrayList<Artista> readAllArtistas(){
        ArrayList<Artista> listaArtista= new ArrayList<>();
        SQLiteDatabase sql = helper.getReadableDatabase();
        String [] columnasConsultadas = {"id_artista", "nombre","descripcion"};
        Cursor cursor = sql.query(
                "artista",
                columnasConsultadas,
                null,
                null,
                null,
                null,
                "nombre"
        );

        if(cursor==null){
            return listaArtista;
        }
        if(!cursor.moveToFirst()){
            return listaArtista;
        }

        do{
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String descripcion = cursor.getString(2);

            Artista  artista = new Artista(id, nombre, descripcion);
            listaArtista.add(artista);
        }while(cursor.moveToNext());
        cursor.close();
        return listaArtista;
    }
    //Delete
    public int deleteGenero(Genero generoAEliminar){
        SQLiteDatabase baseDeDatos = helper.getWritableDatabase();
        String campo = "id_genero = ?";
        String[] argumento = {String.valueOf(generoAEliminar.getIdGenero())};
        return baseDeDatos.delete("genero",campo, argumento);
    }


}
