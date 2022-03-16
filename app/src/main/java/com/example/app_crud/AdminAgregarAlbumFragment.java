package com.example.app_crud;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAgregarAlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminAgregarAlbumFragment extends Fragment {
    Button btnAgregarCancion, btnAtras, btnAgregarFoto;
    ImageView imagen;

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
        imagen.setImageBitmap(getImagenDB());
        btnAgregarFoto = vista.findViewById(R.id.btnAgregarFoto);
        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });
        return vista;

    }
    public void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"),10);
    }
    public Bitmap getImagenDB(){
        Bitmap bitmap = null;
        Helper helper = new Helper(getActivity());
        SQLiteDatabase objDB = helper.getReadableDatabase();
        Cursor objCursor = objDB.rawQuery("select src from album where id_album=1", null);
        if(objCursor.getCount()!=0){
            while(objCursor.moveToNext()){
                byte[] imagenBytesRecuperado = objCursor.getBlob(0);
                bitmap = BitmapFactory.decodeByteArray(imagenBytesRecuperado,0,imagenBytesRecuperado.length);
            }
        }
        return bitmap;

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
                storeImage(new ModelClassImage("fotito",selectedImage));
                //System.out.println("listo: "+add());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private String add(){
        imagen.setDrawingCacheEnabled(true);
        imagen.buildDrawingCache();
        Bitmap bitmap = imagen.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,200,baos);
        byte[] data = baos.toByteArray();
        return data.toString();
    }
    private byte[] getLogoImage(String url){
        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();

            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayOutputStream baf = new ByteArrayOutputStream();
            byte[] data = new byte[500];
            int current = 0;
            while ((current = bis.read(data,0,data.length)) != -1) {
                baf.write(data,0,current);
            }

            return baf.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    public void storeImage(ModelClassImage objectModelImage){
        try {
            Bitmap imageToStore = objectModelImage.getImage();
            ByteArrayOutputStream objeto = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, objeto);
            byte[] imgBytes = objeto.toByteArray();
            System.out.println(imgBytes);
            ArrayList<Genero> generos = new ArrayList<>();
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
    }
}