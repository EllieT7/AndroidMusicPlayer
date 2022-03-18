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

    public long createVenta(Venta venta){
        SQLiteDatabase sql = helper.getWritableDatabase();
        ContentValues valInsert = new ContentValues();
        valInsert.put("ci", venta.getCi());
        valInsert.put("cliente", venta.getCliente());
        valInsert.put("ubicacion",venta.getUbicacion());
        valInsert.put("album_id_album",venta.getAlbum().getIdAlbum());
        return sql.insert("venta",null,valInsert);
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
        SQLiteDatabase sql = helper.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from cancion where album_id_album = "+idAlbum,null);
        if(cursor==null){
            return lista;
        }
        if(!cursor.moveToFirst()){
            return lista;
        }
        do{
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String duracion = cursor.getString(2);
            Cancion cancion = new Cancion(id,nombre,duracion);
            lista.add(cancion);
        }while(cursor.moveToNext());
        cursor.close();
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

    public ArrayList<Venta> readAllVentas(){
        ArrayList<Venta> listaVentas = new ArrayList<>();
        SQLiteDatabase sql = helper.getReadableDatabase();
        String [] columnasConsultadas = {"id_venta", "ci","cliente", "ubicacion","album_id_album"};
        Cursor cursor = sql.query(
                "venta",
                columnasConsultadas,
                null,
                null,
                null,
                null,
                "id_venta"
        );

        if(cursor==null){
            return listaVentas;
        }
        if(!cursor.moveToFirst()){
            return listaVentas;
        }

        do{
            int id = cursor.getInt(0);
            String ci = cursor.getString(1);
            String cliente = cursor.getString(2);
            String ubicacion = cursor.getString(3);
            int idAlbum = cursor.getInt(4);
            Album album = getAlbumById(idAlbum);
            Venta venta = new Venta(id,ci,cliente,ubicacion,album);
            listaVentas.add(venta);
        }while(cursor.moveToNext());
        cursor.close();
        return listaVentas;
    }
    public Album getAlbumById(int idAlbum){
        Album album = null;
        SQLiteDatabase sql = helper.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from album where id_album="+idAlbum,null);
        if(cursor==null){
            return album;
        }
        if(!cursor.moveToFirst()){
            return album;
        }
        do{
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String stringDate = cursor.getString(2);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            java.util.Date fechaUtil = null;
            try {
                fechaUtil = dateFormat.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.sql.Date fecha = new Date(fechaUtil.getTime());
            Float precio = cursor.getFloat(3);
            byte[] src= cursor.getBlob(4);
            int idArtista = cursor.getInt(5);
            int idGenero = cursor.getInt(6);
            ArrayList<Cancion> listaCanciones = getCanciones(id);
            album = new Album(id,nombre,fecha,precio,getGenero(idGenero), getArtistaAlbum(idArtista), src, listaCanciones);
        }while(cursor.moveToNext());
        cursor.close();
        return album;
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
    public int deleteGenero(int idGenero){
        SQLiteDatabase baseDeDatos = helper.getWritableDatabase();
        ArrayList<Album> lista = getAlbumByGenero(idGenero);
        String[] argumento = {String.valueOf(idGenero)};
        for(int i=0;i<lista.size();i++){
            deleteAlbum(lista.get(0).getIdAlbum());
        }
        return baseDeDatos.delete("genero","id_genero = ?",argumento);
    }

    public int deleteAlbum(int idAlbum){
        SQLiteDatabase baseDeDatos = helper.getWritableDatabase();
        String campo = "album_id_album = ?";
        String[] argumento = {String.valueOf(idAlbum)};
        baseDeDatos.delete("cancion",campo, argumento);
        baseDeDatos.delete("venta",campo,argumento);
        campo = "id_album = ?";
        return baseDeDatos.delete("album",campo, argumento);
    }

    public int deleteArtista(int idArtista){
        SQLiteDatabase baseDeDatos = helper.getWritableDatabase();
        ArrayList<Album> lista = getAlbumByArtista(idArtista);
        for(int i=0;i<lista.size();i++){
            deleteAlbum(lista.get(0).getIdAlbum());
        }
        String campo = "id_artista = ?";
        String[] argumento = {String.valueOf(idArtista)};
        return baseDeDatos.delete("artista",campo, argumento);
    }

    //Update
    public long editarAlbum(Album album){
        SQLiteDatabase sql = helper.getWritableDatabase();
        ContentValues valoresActualizar = new ContentValues();
        valoresActualizar.put("nombre", album.getNombre());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); //Dando formato
        valoresActualizar.put("f_lanzamiento", dateFormat.format(album.getfLanzamiento()));
        valoresActualizar.put("precio", album.getPrecio());
        valoresActualizar.put("src",album.getSrc());
        valoresActualizar.put("artista_id_artista", album.getArtista().getIdArtista());
        valoresActualizar.put("genero_id_genero", album.getGeneros().getIdGenero());
        // where id...
        String campoParaActualizar = "id_album = ?";
        String[] argumentosParaActualizar = {String.valueOf(album.getIdAlbum())};
        sql.update("album",valoresActualizar,campoParaActualizar,argumentosParaActualizar);
        //Eliminamos canciones
        SQLiteDatabase baseDeDatos = helper.getWritableDatabase();
        // where id...
        String campoParaEliminar = "album_id_album = ?";
        String[] argumentosParaEliminar = {String.valueOf(album.getIdAlbum())};
        sql.delete("cancion",  campoParaEliminar, argumentosParaEliminar);

        //insertamos canciones
        for(int i=0; i<album.getListaCanciones().size()-1;i++){
            ContentValues valInsert1 = new ContentValues();
            valInsert1.put("titulo", album.getListaCanciones().get(i).getTitulo());
            valInsert1.put("duracion", album.getListaCanciones().get(i).getDuracion());
            valInsert1.put("album_id_album", album.getIdAlbum());
            sql.insert("cancion",null,valInsert1);
        }
        ContentValues valInsert1 = new ContentValues();
        int size = album.getListaCanciones().size();
        valInsert1.put("titulo", album.getListaCanciones().get(size-1).getTitulo());
        valInsert1.put("duracion", album.getListaCanciones().get(size-1).getDuracion());
        valInsert1.put("album_id_album", album.getIdAlbum());
        return sql.insert("cancion",null,valInsert1);
    }

    public long editarArtista(Artista artista){
        SQLiteDatabase sql = helper.getWritableDatabase();
        ContentValues valoresActualizar = new ContentValues();
        valoresActualizar.put("nombre", artista.getNombre());
        valoresActualizar.put("descripcion", artista.getDescripcion());
        // where id...
        String campoParaActualizar = "id_artista = ?";
        String[] argumentosParaActualizar = {String.valueOf(artista.getIdArtista())};
        return sql.update("artista",valoresActualizar,campoParaActualizar,argumentosParaActualizar);
    }
    public long editarGenero(Genero genero){
        SQLiteDatabase sql = helper.getWritableDatabase();
        ContentValues valoresActualizar = new ContentValues();
        valoresActualizar.put("descripcion", genero.getDescripcion());
        // where id...
        String campoParaActualizar = "id_genero = ?";
        String[] argumentosParaActualizar = {String.valueOf(genero.getIdGenero())};
        return sql.update("genero",valoresActualizar,campoParaActualizar,argumentosParaActualizar);
    }
    public ArrayList<Album> getAlbumByGenero(int idGenero){
        ArrayList<Album> lista = new ArrayList<>();
        SQLiteDatabase sql = helper.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from album where genero_id_genero="+idGenero,null);
        if(cursor==null){
            return lista;
        }
        if(!cursor.moveToFirst()){
            return lista;
        }
        do{
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String stringDate = cursor.getString(2);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            java.util.Date fechaUtil = null;
            try {
                fechaUtil = dateFormat.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.sql.Date fecha = new Date(fechaUtil.getTime());
            Float precio = cursor.getFloat(3);
            byte[] src= cursor.getBlob(4);
            int idArtista = cursor.getInt(5);
            int idGenero2 = cursor.getInt(6);
            ArrayList<Cancion> listaCanciones = getCanciones(id);
            Album album = new Album(id,nombre,fecha,precio,getGenero(idGenero2), getArtistaAlbum(idArtista), src, listaCanciones);
            lista.add(album);
        }while(cursor.moveToNext());
        cursor.close();
        return lista;
    }
    public ArrayList<Album> getAlbumByArtista(int idArtista){
        ArrayList<Album> lista = new ArrayList<>();
        SQLiteDatabase sql = helper.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select * from album where artista_id_artista="+idArtista,null);
        if(cursor==null){
            return lista;
        }
        if(!cursor.moveToFirst()){
            return lista;
        }
        do{
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String stringDate = cursor.getString(2);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            java.util.Date fechaUtil = null;
            try {
                fechaUtil = dateFormat.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.sql.Date fecha = new Date(fechaUtil.getTime());
            Float precio = cursor.getFloat(3);
            byte[] src= cursor.getBlob(4);
            int idArtista2 = cursor.getInt(5);
            int idGenero2 = cursor.getInt(6);
            ArrayList<Cancion> listaCanciones = getCanciones(id);
            Album album = new Album(id,nombre,fecha,precio,getGenero(idGenero2), getArtistaAlbum(idArtista2), src, listaCanciones);
            lista.add(album);
        }while(cursor.moveToNext());
        cursor.close();
        return lista;

    }
}
