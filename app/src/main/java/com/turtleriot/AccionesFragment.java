package com.turtleriot;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.turtleriot.acciones.AccionesSeguidosActivity;
import com.turtleriot.acciones.AdministrarAccionesActivity;
import com.turtleriot.acciones.CrearAccionesActivity;
import com.turtleriot.fbDataBase.FireDataBase;
import com.turtleriot.javaBean.Accion;
import com.turtleriot.javaBean.BuscarAccionesAdaptador;

import java.util.ArrayList;

public class AccionesFragment extends Fragment {

    private View v;

    private RecyclerView rvBuscarAcciones;

    private ArrayList<Accion> listaAcciones;
    private LinearLayoutManager llm;
    private BuscarAccionesAdaptador adaptador;

    private FloatingActionButton fabSeguidosAcciones;
    private FloatingActionButton fabCrearAcciones;
    private FloatingActionButton fabAdministrarAcciones;

    private EditText etBuscarAcciones;
    private ImageView ivbtnBuscarAcciones;

    private SwipeRefreshLayout srlBuscarAcciones;

    // DATABASE
    private FireDataBase fdb;

    public AccionesFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_acciones, container, false);

        rvBuscarAcciones = v.findViewById(R.id.rvBuscarAcciones);

        arrancarRecycleView();

        fabSeguidosAcciones = v.findViewById(R.id.fabSeguidosAcciones);
        fabCrearAcciones = v.findViewById(R.id.fabCrearAcciones);
        fabAdministrarAcciones = v.findViewById(R.id.fabAdministrarAcciones);

        c_fabSeguidosAcciones();
        c_fabCrearAcciones();
        c_fabAdministrarAcciones();

        etBuscarAcciones = v.findViewById(R.id.etBuscarAcciones);
        ivbtnBuscarAcciones = v.findViewById(R.id.ivbtnBuscarAcciones);

        c_ivbtnBuscarAcciones();

        srlBuscarAcciones = v.findViewById(R.id.srlBuscarAcciones);
        srlBuscarAcciones.setOnRefreshListener(srlListener);

        return v;
    }

    private void arrancarRecycleView() {
        listaAcciones = new ArrayList<>();
        llm = new LinearLayoutManager(getActivity());

        adaptador = new BuscarAccionesAdaptador(listaAcciones);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accion accion = listaAcciones.get(rvBuscarAcciones.getChildAdapterPosition(v));
                Intent i = new Intent(getContext(),InfoAccionesActivity.class);
                i.putExtra("ACCION",accion);
                startActivity(i);
            }
        });

        fdb = new FireDataBase();
        fdb.setListaAcciones(listaAcciones);
        fdb.setAdaptador(adaptador);

        fdb.arrancarChildItemListener();

        rvBuscarAcciones.setLayoutManager(llm);
        rvBuscarAcciones.setAdapter(adaptador);
        rvBuscarAcciones.setItemAnimator(new DefaultItemAnimator());
    }

    private SwipeRefreshLayout.OnRefreshListener srlListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            arrancarRecycleView();
            srlBuscarAcciones.setRefreshing(false);
        }
    };

    private void c_fabSeguidosAcciones() {
        fabSeguidosAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AccionesSeguidosActivity.class));
            }
        });
    }

    private void c_fabCrearAcciones() {
        fabCrearAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),CrearAccionesActivity.class));
            }
        });
    }

    private void c_fabAdministrarAcciones() {
        fabAdministrarAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AdministrarAccionesActivity.class));
            }
        });
    }

    private void c_ivbtnBuscarAcciones() {
        ivbtnBuscarAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomAccion = etBuscarAcciones.getText().toString().trim();
                if(!nomAccion.isEmpty()){
                    ArrayList<Accion> list = new ArrayList<>();
                    for (Accion accion : listaAcciones){
                        if(accion.getTitulo().equalsIgnoreCase(nomAccion)){
                            list.add(accion);
                        }
                    }

                    adaptador.setListaAcciones(list);
                    rvBuscarAcciones.setAdapter(adaptador);
                }
            }
        });
    }
}