package com.example.app_crud;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminEditarAlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminEditarAlbumFragment extends Fragment {
    Button btnAgregarCancion, btnAtras, btnAgregarFoto, btnGuardarAlbum;
    ImageView imagen;
    EditText etPlannedDate, etNombreAlbum, etPrecio;
    Spinner spinnerArtistas, spinnerGeneros;
    Controlador controlador;
    ArrayList<Cancion> listaCanciones = new ArrayList<>();
    RecyclerView rvlistadoCanciones;
    AdaptadorAdminCanciones adaptador;
    Album albumRecibido;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminEditarAlbumFragment() {
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
    public static AdminEditarAlbumFragment newInstance(String param1, String param2) {
        AdminEditarAlbumFragment fragment = new AdminEditarAlbumFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_admin_editar_album, container, false);
        albumRecibido = (Album) getArguments().getSerializable("album");
        System.out.println("info que llegó: "+getArguments().getSerializable("album"));
        //Elementos
        controlador = new Controlador(getContext());
        etNombreAlbum = vista.findViewById(R.id.etNombreAlbum);
        etPrecio = vista.findViewById(R.id.etPrecioAlbum);
        rvlistadoCanciones = vista.findViewById(R.id.rvCancionesAgregadas);
        listaCanciones = albumRecibido.getListaCanciones();
        adaptador = new AdaptadorAdminCanciones(listaCanciones);
        rvlistadoCanciones.setAdapter(adaptador);
        rvlistadoCanciones.setLayoutManager(new GridLayoutManager(this.getContext(),1));

        spinnerArtistas = vista.findViewById(R.id.spinnerArtista);
        spinnerGeneros = vista.findViewById(R.id.spinnerGenero);
        etPlannedDate = vista.findViewById(R.id.etPlannedDate);
        llenarSpinner(spinnerArtistas, "artista", albumRecibido.getArtista().getNombre());
        llenarSpinner(spinnerGeneros, "genero", albumRecibido.getGeneros().getDescripcion());
        imagen = vista.findViewById(R.id.imgPortada);

        //Rellenar datos
        etNombreAlbum.setText(albumRecibido.getNombre());
        etPrecio.setText(albumRecibido.getPrecio()+"");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date fecha = formatter.parse(albumRecibido.getfLanzamiento().toString());
            SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy");
            etPlannedDate.setText(formato2.format(fecha));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        byte[] imagenBytesRecuperado = albumRecibido.getSrc();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytesRecuperado,0,imagenBytesRecuperado.length);
        imagen.setImageBitmap(bitmap);


        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        btnGuardarAlbum = vista.findViewById(R.id.btnAdminGuardarAlbum);
        btnGuardarAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int idAlbum = albumRecibido.getIdAlbum();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date fecha = formatter.parse(etPlannedDate.getText().toString());
                    float precio = Float.parseFloat(etPrecio.getText().toString());
                    Genero genero = (Genero)spinnerGeneros.getSelectedItem();
                    Artista artista = (Artista) spinnerArtistas.getSelectedItem();
                    Bitmap imageToStore = ((BitmapDrawable)imagen.getDrawable()).getBitmap();
                    ByteArrayOutputStream objeto = new ByteArrayOutputStream();
                    imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, objeto);
                    byte[] imgBytes = objeto.toByteArray();
                    Album  album = new Album(idAlbum,etNombreAlbum.getText().toString(),new Date(fecha.getTime()),precio,genero, artista,imgBytes, listaCanciones);
                    long res = controlador.editarAlbum(album);
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

    public void llenarSpinner(Spinner spinner, String tipo, String coincidencia){
      if(tipo.equals("artista")){
          ArrayList<Artista> listaArtistas = controlador.readAllArtistas();
          ArrayAdapter<Artista> adaptadorlistaArtistas =  new ArrayAdapter<Artista>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaArtistas);;
          spinner.setAdapter(adaptadorlistaArtistas);
          for(int i=0; i<listaArtistas.size();i++){
              if(listaArtistas.get(i).toString().equals(coincidencia)){
                  spinner.setSelection(i);
                  break;
              }
          }
      }else{
          ArrayList<Genero> listaGenero = controlador.readAllGeneros();
          ArrayAdapter<Genero> adaptadorlistaGenero =  new ArrayAdapter<Genero>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, listaGenero);;
          spinner.setAdapter(adaptadorlistaGenero);
          for(int i=0; i<listaGenero.size();i++){
              if(listaGenero.get(i).toString().equals(coincidencia)){
                  spinner.setSelection(i);
                  break;
              }
          }
      }
    }

}