package com.example.app_crud;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleAlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleAlbumFragment extends Fragment {
    Button btnAtras, btnVentaCompra;
    TextView tvTitulo, tvArtista, tvPrecio, tvFecha, tvGenero;
    ImageView imgDetalle;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalleAlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleAlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleAlbumFragment newInstance(String param1, String param2) {
        DetalleAlbumFragment fragment = new DetalleAlbumFragment();
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
        View vista = inflater.inflate(R.layout.fragment_detalle_album, container, false);
        Album albumRecibido = (Album) getArguments().getSerializable("album");
        System.out.println("info que llegó: "+getArguments().getSerializable("album"));
        btnAtras = vista.findViewById(R.id.btnAtras);
        btnVentaCompra = vista.findViewById(R.id.btnVentaComprar);
        tvTitulo = vista.findViewById(R.id.nombreAlbum);
        tvFecha = vista.findViewById(R.id.tvDetalleFecha);
        tvArtista = vista.findViewById(R.id.tvDetalleArtista);
        tvPrecio = vista.findViewById(R.id.tvDetallePrecio);
        tvGenero = vista.findViewById(R.id.tvDetalleGenero);
        imgDetalle = vista.findViewById(R.id.ivFotoDetalle);
        //Asignando
        tvTitulo.setText(albumRecibido.getNombre());
        tvFecha.setText(albumRecibido.getfLanzamiento().toString());
        tvArtista.setText(albumRecibido.getArtista().getNombre());
        tvPrecio.setText(albumRecibido.getPrecio()+"$");
        tvGenero.setText(albumRecibido.getGeneros().getDescripcion());
        byte[] imagenBytesRecuperado = albumRecibido.getSrc();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytesRecuperado,0,imagenBytesRecuperado.length);
        imgDetalle.setImageBitmap(bitmap);



        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });
        btnVentaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal, new VentaFragment()).commit();
            }
        });
        return vista;
    }
}