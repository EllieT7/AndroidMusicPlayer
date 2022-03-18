package com.example.app_crud;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VentaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VentaFragment extends Fragment {
    EditText etCI, etNombreCliente, etUbicacion;
    TextView tvTitulo, tvArtista, tvPrecio;
    Button btnComprar, btnAtras;
    ImageView imagen;
    Controlador controlador;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VentaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VentaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VentaFragment newInstance(String param1, String param2) {
        VentaFragment fragment = new VentaFragment();
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
        View vista = inflater.inflate(R.layout.fragment_venta, container, false);
        Album albumRecibido = (Album) getArguments().getSerializable("album");
        System.out.println("info que llegó: "+getArguments().getSerializable("album"));
        etCI = vista.findViewById(R.id.etCI);
        etNombreCliente = vista.findViewById(R.id.etNombreCliente);
        etUbicacion = vista.findViewById(R.id.etUbicacion);
        tvArtista = vista.findViewById(R.id.tvArtista);
        tvPrecio = vista.findViewById(R.id.tvPrecio);
        tvTitulo = vista.findViewById(R.id.tvTitulo);
        btnComprar = vista.findViewById(R.id.btnComprar);
        imagen = vista.findViewById(R.id.ivFoto);
        controlador = new Controlador(getContext());
        //Asignando
        tvTitulo.setText(albumRecibido.getNombre());
        tvArtista.setText(albumRecibido.getArtista().getNombre());
        tvPrecio.setText(albumRecibido.getPrecio()+" Bs.");
        byte[] imagenBytesRecuperado = albumRecibido.getSrc();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytesRecuperado,0,imagenBytesRecuperado.length);
        imagen.setImageBitmap(bitmap);

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Venta venta = new Venta(etCI.getText().toString(),etNombreCliente.getText().toString(),etUbicacion.getText().toString(),albumRecibido);
                long res = controlador.createVenta(venta);
                if(res <= 0){
                    System.out.println(" fracaso  proceso de alta ");
                    Toast.makeText(getActivity().getApplicationContext(),"error, fracaso en la grabacion", Toast.LENGTH_LONG).show();
                }else{
                    System.out.println(" exito en el proceso de alta ");

                    Toast.makeText(getActivity().getApplicationContext(),"Venta generada correctamente 🥳 "+res, Toast.LENGTH_LONG).show();
                }
            }
        });
        btnAtras = vista.findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return vista;
    }
}