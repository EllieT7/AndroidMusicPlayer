package com.example.app_crud;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ImageView btnAlbumes, btnArtistas, btnGeneros, btnVentas;
    Controlador controlador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnAlbumes = findViewById(R.id.btnAdminAlbumes);
        btnArtistas = findViewById(R.id.btnAdminArtista);
        btnGeneros = findViewById(R.id.btnAdminGeneros);
        btnVentas = findViewById(R.id.btnAdminVentas);
        btnAlbumes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controlador = new Controlador(getApplicationContext());
                ArrayList<Cancion> lista = controlador.readAllCanciones();
                for(int i=0;i<lista.size();i++){
                    System.out.println(lista.get(i));
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal2, new AdminAlbumFragment()).commit();
            }
        });
        btnArtistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal2, new AdminArtistaFragment()).commit();
            }
        });
        btnGeneros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal2, new AdminGeneroFragment()).commit();
            }
        });
    }
}