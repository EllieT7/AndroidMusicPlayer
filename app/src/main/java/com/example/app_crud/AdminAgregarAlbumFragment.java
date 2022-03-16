package com.example.app_crud;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAgregarAlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminAgregarAlbumFragment extends Fragment {
    Button btnAgregarCancion, btnAtras, btnAgregarFoto, btnAgregarAlbum;
    ImageView imagen;
    EditText etPlannedDate, etNombreAlbum, etPrecio;
    Spinner spinnerArtistas, spinnerGeneros;
    Controlador controlador;
    ArrayList<Cancion> listaCanciones = new ArrayList<>();
    RecyclerView rvlistadoCanciones;
    AdaptadorAdminCanciones adaptador;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminAgregarAlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminAgregarAlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminAgregarAlbumFragment newInstance(String param1, String param2) {
        AdminAgregarAlbumFragment fragment = new AdminAgregarAlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_admin_agregar_album, container, false);
        etNombreAlbum = vista.findViewById(R.id.etNombreAlbum);
        etPrecio = vista.findViewById(R.id.etPrecioAlbum);

        rvlistadoCanciones = vista.findViewById(R.id.rvCancionesAgregadas);
        adaptador = new AdaptadorAdminCanciones(listaCanciones);
        rvlistadoCanciones.setAdapter(adaptador);
        rvlistadoCanciones.setLayoutManager(new GridLayoutManager(this.getContext(),1));


        controlador = new Controlador(getContext());
        spinnerArtistas = vista.findViewById(R.id.spinnerArtista);
        spinnerGeneros = vista.findViewById(R.id.spinnerGenero);
        llenarSpinner(spinnerArtistas, "artista");
        llenarSpinner(spinnerGeneros, "genero");
        etPlannedDate = vista.findViewById(R.id.etPlannedDate);
        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        btnAgregarAlbum = vista.findViewById(R.id.btnAdminGuardarAlbum);
        btnAgregarAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date fecha = formatter.parse(etPlannedDate.getText().toString());
                    float precio = Float.parseFloat(etPrecio.getText().toString());
                    Genero genero = (Genero)spinnerGeneros.getSelectedItem();
                    Artista artista = (Artista) spinnerArtistas.getSelectedItem();
                    Bitmap imageToStore = ((BitmapDrawable)imagen.getDrawable()).getBitmap();
                    ByteArrayOutputStream objeto = new ByteArrayOutputStream();
                    imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, objeto);
                    byte[] imgBytes = objeto.toByteArray();
                    Album  album = new Album(etNombreAlbum.getText().toString(),new Date(fecha.getTime()),precio,genero, artista,imgBytes, listaCanciones);
                    long res = controlador.createAlbum(album);
                    if(res <= 0){
                        System.out.println(" fracaso  proceso de alta ");
                        Toast.makeText(getActivity().getApplicationContext(),"error, fracaso en la grabacion", Toast.LENGTH_LONG).show();
                    }else{
                        System.out.println(" exito en el proceso de alta ");

                        Toast.makeText(getActivity().getApplicationContext(),"Album guardado correctamente 🥳 "+res, Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAgregarCancion = vista.findViewById(R.id.btnAgregarCancion);
        btnAgregarCancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View subView = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_cancion, null);
                final EditText etNombre, etDuracion;
                etNombre = subView.findViewById(R.id.etNombreCancion);
                etDuracion = subView.findViewById(R.id.etDuracion);

                AlertDialog.Builder ale =new  AlertDialog.Builder(getContext());
                ale.setTitle("Nueva canción");
                ale.setView(subView);
                ale.setCancelable(false);
                ale.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cancion cancion = new Cancion(etNombre.getText().toString(),etDuracion.getText().toString());

                        refrescarListaDeCanciones(cancion);
                    /*objProd.setNombreProducto(etNombre.getText().toString());
                    objProd.setCostoUnitario(Float.parseFloat(etCosto.getText().toString()));
                    long res = controlador.cambioProducto(objProd);
                    if(res < 0){
                        Toast.makeText(getApplicationContext(),"Error en el cambio", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"succes, exito en  e cambio "+res, Toast.LENGTH_LONG).show();
                        refrescarListaDeProductos();
                    }*/
                    }
                });

                ale.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                ale.show();
            }

        });
        btnAtras = vista.findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);

            }
        });
        imagen = vista.findViewById(R.id.imgPortada);

        btnAgregarFoto = vista.findViewById(R.id.btnAgregarFoto);
        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });
        return vista;

    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                etPlannedDate.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    public void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"),10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            Uri path = data.getData();
            //Uri path2 = Uri.parse("content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F33/ORIGINAL/NONE/image%2Fpng/1721836016");
            //imagen.setImageURI(path2);
            try {
                //InputStream imageStream = getActivity().getContentResolver().openInputStream(path);
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                //Bitmap opcion = StringToBitMap("android.graphics.Bitmap@d6e40d1");
                //System.out.println();
                //byte[] dato = getBytes(imageStream);
                imagen.setImageBitmap(selectedImage);
                System.out.println(selectedImage);
                //storeImage(new ModelClassImage("fotito",selectedImage));
                //System.out.println("listo: "+add());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void refrescarListaDeCanciones(Cancion cancion) {

        System.out.println("100");
        if (adaptador == null) return;
        System.out.println("200");
        listaCanciones.add(cancion);
        adaptador.setLista(listaCanciones);
        adaptador.notifyDataSetChanged();
        System.out.println("300");
    }
    /*public void storeImage(ModelClassImage objectModelImage){
        try {
            Bitmap imageToStore = objectModelImage.getImage();
            ByteArrayOutputStream objeto = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, objeto);
            byte[] imgBytes = objeto.toByteArray();
            System.out.println(imgBytes);
            Genero generos = new Genero("kpop");
            java.util.Date utilDate = new java.util.Date();
            Album album = new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),imgBytes);
            Controlador controlador = new Controlador(getActivity());
            long res= controlador.createAlbum(album);
            if(res <= 0){
                System.out.println(" fracaso  proceso de alta ");
                Toast.makeText(getActivity().getApplicationContext(),"error, fracaso en la grabacion", Toast.LENGTH_LONG).show();
            }else{
                System.out.println(" exito en el proceso de alta ");

                Toast.makeText(getActivity().getApplicationContext(),"succes, exito en la grabacion "+res, Toast.LENGTH_LONG).show();
            }
            //metemos db
            //ContentValues objectoConteny = new ContentValues();

        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
    public void llenarSpinner(Spinner spinner, String tipo){
      if(tipo.equals("artista")){
          ArrayList<Artista> listaArtistas = controlador.readAllArtistas();
          ArrayAdapter<Artista> adaptadorlistaArtistas =  new ArrayAdapter<Artista>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaArtistas);;
          spinner.setAdapter(adaptadorlistaArtistas);
      }else{
          ArrayList<Genero> listaGenero = controlador.readAllGeneros();
          ArrayAdapter<Genero> adaptadorlistaGenero =  new ArrayAdapter<Genero>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaGenero);;
          spinner.setAdapter(adaptadorlistaGenero);
      }
    }
}