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
        sql.insert("album",null,valInsert);
        for(int i=0; i<album.getListaCanciones().size()-1;i++){
            ContentValues valInsert1 = new ContentValues();
            valInsert1.put("titulo", album.getListaCanciones().get(i).getTitulo());
            valInsert1.put("duracion", album.getListaCanciones().get(i).getDuracion());
            valInsert1.put("album_id_album", getUltimoAlbumID());
            sql.insert("cancion",null,valInsert1);
        }
        ContentValues valInsert1 = new ContentValues();
        int size = album.getListaCanciones().size();
        valInsert1.put("titulo", album.getListaCanciones().get(size-1).getTitulo());
        valInsert1.put("duracion", album.getListaCanciones().get(size-1).getDuracion());
        valInsert1.put("album_id_album", getUltimoAlbumID());
        return sql.insert("cancion",null,valInsert1);
    }

    public long createArtista(Artista artista){
        SQLiteDatabase sql = helper.getWritableDatabase();
        ContentValues valInsert = new ContentValues();
        valInsert.put("nombre", artista.getNombre());
        valInsert.put("descripcion", artista.getDescripcion());
        return sql.insert("artista",null,valInsert);
    }

    public long createCancion(Cancion cancion, Album album){
        SQLiteDatabase sql = helper.getWritableDatabase();
        ContentValues valInsert = new ContentValues();
        valInsert.put("titulo", cancion.getTitulo());
        valInsert.put("duracion", cancion.getDuracion());
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
            ArrayList<Cancion> listaCanciones = getCanciones(id);
            Album album = new Album(id,nombre,fecha,precio,getGenero(idGenero), getArtistaAlbum(idArtista), src, listaCanciones);
            listaAlbumes.add(album);
        }while(cursor.moveToNext());
        cursor.close();
        return listaAlbumes;
    }

    public ArrayList<Cancion> getCanciones(int idAlbum){
        ArrayList<Cancion> lista = new ArrayList<>();
        //TODO trabajar query
        return lista;
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

    //Obtener ultimo elemento album
    //Busca la lista de generos que pertenecen al album
    public int getUltimoAlbumID(){
        int id = 0;
        SQLiteDatabase sql = helper.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select id_album from album order by id_album desc",null);
        if(cursor==null){
            return id;
        }
        if(!cursor.moveToFirst()){
            return id;
        }
        do{
            int idRecolectado = cursor.getInt(0);
            id = idRecolectado;
            break;
        }while(cursor.moveToNext());
        cursor.close();
        return id;
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
    //Leer todos las canciones
    public ArrayList<Cancion> readAllCanciones(){
        ArrayList<Cancion> listaCanciones= new ArrayList<>();
        SQLiteDatabase sql = helper.getReadableDatabase();
        String [] columnasConsultadas = {"id_cancion", "titulo","duracion","album_id_album"};
        Cursor cursor = sql.query(
                "cancion",
                columnasConsultadas,
                null,
                null,
                null,
                null,
                "id_cancion"
        );

        if(cursor==null){
            return listaCanciones;
        }
        if(!cursor.moveToFirst()){
            return listaCanciones;
        }

        do{
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String duracion = cursor.getString(2);
            int idAlbum = cursor.getInt(3);
            System.out.println("idAlbum: "+idAlbum);
            Cancion cancion = new Cancion(id,nombre,duracion);
            listaCanciones.add(cancion);
        }while(cursor.moveToNext());
        cursor.close();
        return listaCanciones;
    }
    //Delete
    public int deleteGenero(Genero generoAEliminar){
        SQLiteDatabase baseDeDatos = helper.getWritableDatabase();
        String campo = "id_genero = ?";
        String[] argumento = {String.valueOf(generoAEliminar.getIdGenero())};
        return baseDeDatos.delete("genero",campo, argumento);
    }


}
