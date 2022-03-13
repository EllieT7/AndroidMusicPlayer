package com.example.app_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //Dando formato
        valInsert.put("f_lanzamiento", dateFormat.format(album.getfLanzamiento()));
        valInsert.put("precio", album.getPrecio());
        valInsert.put("artista_id_artista", album.getArtista().getIdArtista());
        return sql.insert("album",null,valInsert);

        //TODO insertar datos en album_genero

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

    public ArrayList<Album> readAlbum() throws ParseException {
        ArrayList<Album> listaAlbumes = new ArrayList<>();
        SQLiteDatabase sql = helper.getReadableDatabase();
        String [] columnasConsultadas = {"id_album", "nombre", "f_lanzamiento", "precio","artista_id_artista"};
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = (Date) dateFormat.parse(stringDate);
            Float precio = cursor.getFloat(3);
            int idArtista = cursor.getInt(4);
            String src="";
            Album album = new Album(id,nombre,fecha,precio,getListaGeneros(id), getArtistaAlbum(idArtista), src);
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
    public ArrayList<Genero> getListaGeneros(int idAlbum){
        ArrayList<Genero> listaGeneros = new ArrayList<>();
        SQLiteDatabase sql = helper.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select genero.id_genero, genero.descripcion from genero, album_genero where genero.id_genero = album_genero.genero_id_genero and album_id_album = "+idAlbum,null);
        if(cursor==null){
            return listaGeneros;
        }
        if(!cursor.moveToFirst()){
            return listaGeneros;
        }
        do{
            int id = cursor.getInt(0);
            String descripcion = cursor.getString(1);
            Genero genero = new Genero(id, descripcion);
            listaGeneros.add(genero);
        }while(cursor.moveToNext());
        cursor.close();
        return listaGeneros;
    }

    //Delete
    public int deleteGenero(Genero generoAEliminar){
        SQLiteDatabase baseDeDatos = helper.getWritableDatabase();
        String campo = "id_genero = ?";
        String[] argumento = {String.valueOf(generoAEliminar.getIdGenero())};
        return baseDeDatos.delete("genero",campo, argumento);
    }

}
