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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllAlbumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllAlbumsFragment extends Fragment {
    private RecyclerView rvlista;
    private ArrayList<Album> listaAlbum;
    private Adaptador adaptador;
    Button btnAtras;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllAlbumsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllAlbumsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllAlbumsFragment newInstance(String param1, String param2) {
        AllAlbumsFragment fragment = new AllAlbumsFragment();
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
        View vista = inflater.inflate(R.layout.fragment_all_albums, container, false);
        listaAlbum = new ArrayList<>();
        adaptador = new Adaptador(listaAlbum);
        rvlista = vista.findViewById(R.id.rvAllAlbum);
        btnAtras = vista.findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });
        cargaData();
        rvlista.setAdapter(adaptador);
        rvlista.setLayoutManager(new GridLayoutManager(this.getContext(),2));

        return vista;
    }
    void cargaData(){
        /*
        ArrayList<Genero> generos = new ArrayList<>();
        java.util.Date utilDate = new java.util.Date();
        listaAlbum.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
        listaAlbum.add(new Album("Blurryface",new Date(utilDate.getTime()),23,generos, new Artista("Twenty One Pilots","besotes"), null));
        listaAlbum.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
        listaAlbum.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
        listaAlbum.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
        listaAlbum.add(new Album("Blurryface",new Date(utilDate.getTime()),23,generos, new Artista("Twenty One Pilots","besotes"),null));
        listaAlbum.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
        listaAlbum.add(new Album("Map of the Soul",new Date(utilDate.getTime()),23,generos, new Artista("BTS","besotes"),null));
*/
    }
}