package com.example.app_crud;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.sql.Date;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistasFragment extends Fragment {
    Controlador controlador;
    private RecyclerView rvlista;
    private ArrayList<Artista> listaArtistas;
    private AdaptadorArtistas adaptador;
    Button btnAtras;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArtistasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtistasFragment newInstance(String param1, String param2) {
        ArtistasFragment fragment = new ArtistasFragment();
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
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_artistas, container, false);
        controlador = new Controlador(getContext());
        listaArtistas = controlador.readAllArtistas();
        adaptador = new AdaptadorArtistas(listaArtistas);
        rvlista = vista.findViewById(R.id.rvArtistas);
        btnAtras = vista.findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });
        rvlista.setAdapter(adaptador);
        rvlista.setLayoutManager(new GridLayoutManager(this.getContext(),1));
        rvlista.addOnItemTouchListener(new RecyclerTouchListener(this.getContext(), rvlista, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                Artista artistaSeleccionado = listaArtistas.get(position);
                ArrayList<Album> albums = controlador.getAlbumByArtista(artistaSeleccionado.getIdArtista());
                bundle.putSerializable("album",albums);
                AllAlbumsFragment nuevoFragment = new AllAlbumsFragment();
                nuevoFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal, nuevoFragment).commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return vista;
    }
}