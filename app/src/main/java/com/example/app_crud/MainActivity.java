package com.example.app_crud;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button btnAgregarGenero;
    EditText etDescripcion;
    Controlador controlador;
    ArrayList<Album> listaAlbumes;
    Adaptador adaptador;
    RecyclerView rvlista;
    CarouselPicker carouselPicker;
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
        /*carouselPicker = (CarouselPicker) findViewById(R.id.carousel);
        List<CarouselPicker.PickerItem> mixItems = new ArrayList<>();
        mixItems.add(new CarouselPicker.TextItem("One",20));
        mixItems.add(new CarouselPicker.DrawableItem(R.mipmap.ic_launcher_round));
        mixItems.add(new CarouselPicker.TextItem("two",20));
        mixItems.add(new CarouselPicker.DrawableItem(R.mipmap.ic_launcher));
        CarouselPicker.CarouselViewAdapter mixAdapter = new CarouselPicker.CarouselViewAdapter(this, mixItems,0);
        carouselPicker.setAdapter(mixAdapter);*/

        //view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miAlerta();
            }
        });

        fabArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mostrarFragmentArtistas();
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