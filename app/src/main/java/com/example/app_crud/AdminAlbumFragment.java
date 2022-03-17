package com.example.app_crud;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminAlbumFragment extends Fragment {
    private RecyclerView rvlista;
    private ArrayList<Album> listaAlbum;
    private AdaptadorAdminAlbums adaptador;
    Button btnAtras, btnAgregarAlbum;
    Controlador controlador;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminAlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminAlbumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminAlbumFragment newInstance(String param1, String param2) {
        AdminAlbumFragment fragment = new AdminAlbumFragment();
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
        View vista = inflater.inflate(R.layout.fragment_admin_album, container, false);
        controlador = new Controlador(getContext());
        try {
            listaAlbum = controlador.readAllAlbum();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adaptador = new AdaptadorAdminAlbums(getActivity(),listaAlbum);
        rvlista = vista.findViewById(R.id.rvAllAlbumAdmin);
        btnAtras = vista.findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);

            }
        });
        rvlista.setAdapter(adaptador);
        rvlista.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        btnAgregarAlbum = vista.findViewById(R.id.btnAgregarAlbum);
        btnAgregarAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_principal2, new AdminAgregarAlbumFragment()).commit();

            }
        });
        return vista;
    }

}