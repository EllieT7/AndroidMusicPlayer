package com.example.app_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mnuAlta:
                //Intent invoca = new Intent(this, Alta.class);
                //startActivity(invoca);
                break;
            case R.id.mnuLista:
                //Intent invoca1 = new Intent(this, Lista.class);
                //startActivity(invoca1);
                break;



        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaAlbumes = new ArrayList<>();
        adaptador = new Adaptador(listaAlbumes);
        rvlista = findViewById(R.id.rvData);
        btnBuscar = findViewById(R.id.btnBuscar);
        grupoBotones = findViewById(R.id.grupoFloat);
        fabArtista = findViewById(R.id.btnArtistas);
        fabGenero = findViewById(R.id.btnGeneros);
        artistasFragment = new ArtistasFragment();
        generosFragment = new GenerosFragment();
        cargaData();
        rvlista.setAdapter(adaptador);
        rvlista.setLayoutManager(new GridLayoutManager(this,2));
        rvlista.addOnItemTouchListener(new RecyclerTouchListener(this, rvlista, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal, new DetalleAlbumFragment()).commit();
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
    void cargaData(){
        /*
        ArrayList<Genero> generos = new ArrayList<>();
        java.util.Date utilDate = new java.util.Date();
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
        listaAlbumes.add(new Album("Blurryface",new Date(utilDate.getTime()),23,generos, new Artista("Twenty One Pilots","besotes"),null));
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
        listaAlbumes.add(new Album("Blurryface",new Date(utilDate.getTime()),23,generos, new Artista("Twenty One Pilots","besotes"),null));
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
        listaAlbumes.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
*/
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