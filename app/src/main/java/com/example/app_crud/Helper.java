package com.example.app_crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    static final String dataBase = "base3";
    static final int verDB = 1;

    public Helper(@Nullable Context context) {
        super(context, dataBase, null, verDB);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Tabla album
        sqLiteDatabase.execSQL("CREATE TABLE album (id_album integer NOT NULL CONSTRAINT album_pk PRIMARY KEY AUTOINCREMENT, nombre varchar(100) NOT NULL,  f_lanzamiento date NOT NULL,precio float NOT NULL, src blob NOT NULL, artista_id_artista integer NOT NULL, genero_id_genero integer NOT NULL, CONSTRAINT album_artista FOREIGN KEY (artista_id_artista) REFERENCES artista (id_artista), CONSTRAINT album_genero FOREIGN KEY (genero_id_genero) REFERENCES genero (id_genero));");
        //Tabla artista
        sqLiteDatabase.execSQL("CREATE TABLE artista (id_artista integer NOT NULL CONSTRAINT artista_pk PRIMARY KEY AUTOINCREMENT,nombre varchar(100) NOT NULL,descripcion varchar(250) NOT NULL);");
        //Tabla cancion
        sqLiteDatabase.execSQL("CREATE TABLE cancion (id_cancion integer NOT NULL CONSTRAINT cancion_pk PRIMARY KEY AUTOINCREMENT,titulo varchar(100) NOT NULL,duracion varchar(10) NOT NULL,album_id_album integer NOT NULL,CONSTRAINT cancion_album FOREIGN KEY (album_id_album)REFERENCES album (id_album));");
        //Tabla genero
        sqLiteDatabase.execSQL("CREATE TABLE genero (id_genero integer NOT NULL CONSTRAINT genero_pk PRIMARY KEY AUTOINCREMENT,descripcion varchar(100) NOT NULL);");
        //Tabla venta
        sqLiteDatabase.execSQL("CREATE TABLE venta (id_venta integer NOT NULL CONSTRAINT venta_pk PRIMARY KEY AUTOINCREMENT, ci varchar(20) NOT NULL, cliente varchar(100) NOT NULL, ubicacion varchar(200) NOT NULL );");
        //Tabla venta_album
        sqLiteDatabase.execSQL("CREATE TABLE venta_album (id_venta_album integer NOT NULL CONSTRAINT venta_album_pk PRIMARY KEY AUTOINCREMENT, cantidad integer NOT NULL, venta_id_venta integer NOT NULL, album_id_album integer NOT NULL, CONSTRAINT venta_album_venta FOREIGN KEY (venta_id_venta) REFERENCES venta (id_venta), CONSTRAINT venta_album_album FOREIGN KEY (album_id_album) REFERENCES album (id_album) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS album");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS album_genero");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS artista");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cancion");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS genero");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS venta");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS venta_genero");

        onCreate(sqLiteDatabase);
    }
}
