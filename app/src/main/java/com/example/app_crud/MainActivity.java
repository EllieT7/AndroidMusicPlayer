package com.example.app_crud;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.islamkhsh.CardSliderViewPager;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

public class MainActivity extends AppCompatActivity {

    Controlador controlador;
    ArrayList<Album> listaAlbumes;
    Adaptador adaptador;
    RecyclerView rvlista;
    ImageView btnBuscar;
    FloatingActionsMenu grupoBotones;
    FloatingActionButton fabArtista, fabGenero;
    ArtistasFragment artistasFragment;
    GenerosFragment generosFragment;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlador = new Controlador(this);
        try {
            listaAlbumes = controlador.readAllAlbum();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adaptador = new Adaptador(listaAlbumes);
        rvlista = findViewById(R.id.rvData);
        btnBuscar = findViewById(R.id.btnBuscar);
        grupoBotones = findViewById(R.id.grupoFloat);
        fabArtista = findViewById(R.id.btnArtistas);
        fabGenero = findViewById(R.id.btnGeneros);
        artistasFragment = new ArtistasFragment();
        generosFragment = new GenerosFragment();
        rvlista.setAdapter(adaptador);
        rvlista.setLayoutManager(new GridLayoutManager(this,2));
        rvlista.addOnItemTouchListener(new RecyclerTouchListener(this, rvlista, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                Album albumSeleccionado = listaAlbumes.get(position);
                bundle.putSerializable("album",albumSeleccionado);
                DetalleAlbumFragment nuevoFragmentDetalle = new DetalleAlbumFragment();
                nuevoFragmentDetalle.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal, nuevoFragmentDetalle).commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miAlerta();
            }
        });

        fabArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Artista");
                grupoBotones.collapse();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal, artistasFragment).commit();
            }
        });
        fabGenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Genero");
                grupoBotones.collapse();
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal, generosFragment).commit();
            }
        });
        try {
            CardSliderViewPager cardSliderViewPager = (CardSliderViewPager) findViewById(R.id.viewPager);
            cardSliderViewPager.setAdapter(new AlbumCentralAdapter(listaAlbumes));
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }

    void miAlerta(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("TITULO");
        alerta.setMessage("Mensaje de la alerta");
        alerta.setCancelable(false);    //modal(no se puede salir sin presionar el boton)
        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alerta.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alerta.show();
    }
}