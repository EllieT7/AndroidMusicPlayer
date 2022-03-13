package com.example.app_crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnAgregarGenero;
    EditText etDescripcion;
    Controlador controlador;
    ArrayList<Album> listaAlbumes;
    Adaptador adaptador;
    RecyclerView rvlista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaAlbumes = new ArrayList<>();
        adaptador = new Adaptador(listaAlbumes);
        rvlista = findViewById(R.id.rvData);
        cargaData();
        rvlista.setAdapter(adaptador);
        rvlista.setLayoutManager(new GridLayoutManager(this,2));
        /*
        //btnAgregarGenero = findViewById(R.id.btnGuardarGenero);
        //etDescripcion = findViewById(R.id.etDescripcion);
        controlador = new Controlador(this);
        System.out.println(controlador.readGeneros());
        btnAgregarGenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descripcion = etDescripcion.getText().toString().trim();
                long res = controlador.createGenero(new Genero(descripcion));
                if(res <= 0){
                    System.out.println(" fracaso  proceso de alta ");
                    Toast.makeText(getApplicationContext(),"error, fracaso en la grabacion", Toast.LENGTH_LONG).show();
                }else{
                    System.out.println(" exito en el proceso de alta ");
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    Toast.makeText(getApplicationContext(),"succes, exito en la grabacion "+res, Toast.LENGTH_LONG).show();
                }
            }
        });*/

    }
    void cargaData(){
        ArrayList<Genero> generos = new ArrayList<>();
        java.util.Date utilDate = new java.util.Date();
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),"src"));
        listaAlbumes.add(new Album("Blurryface",new Date(utilDate.getTime()),23,generos, new Artista("Twenty One Pilots","besotes"),"src"));
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),"src"));
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),"src"));
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),"src"));
        listaAlbumes.add(new Album("Blurryface",new Date(utilDate.getTime()),23,generos, new Artista("Twenty One Pilots","besotes"),"src"));
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),"src"));
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),"src"));

    }
}