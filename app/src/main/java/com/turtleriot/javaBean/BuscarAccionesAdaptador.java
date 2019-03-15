package com.turtleriot.javaBean;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.turtleriot.R;

import java.util.ArrayList;

public class BuscarAccionesAdaptador extends RecyclerView.Adapter<BuscarAccionesAdaptador.MiViewHolder> implements View.OnClickListener{

    private ArrayList<Accion> listaAcciones;
    private View.OnClickListener listener;

    public static class MiViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTituloBACC;
        private ImageView ivFotoBACC;
        //private TextView tvPlayaBACC;
        private TextView tvFechaBACC;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloBACC = itemView.findViewById(R.id.tvTituloBACC);
            ivFotoBACC = itemView.findViewById(R.id.ivFotoBACC);
            //tvPlayaBACC = itemView.findViewById(R.id.tvPlayaBACC);
            tvFechaBACC = itemView.findViewById(R.id.tvFechaBACC);
        }

        public void bindAcciones(Accion accion){
            tvTituloBACC.setText(accion.getTitulo());
            if(accion.getFoto() != null){

                Glide.with(ivFotoBACC.getContext())
                        .load(accion.getFoto())
                        .into(ivFotoBACC);
            }
            else{
                Glide.with(ivFotoBACC.getContext())
                        .load(R.drawable.photo)
                        .into(ivFotoBACC);
            }
            //tvPlayaBACC.settext(accion.getPlaya().getNombre());
            tvFechaBACC.setText(accion.getFecha());
        }
    }

    public BuscarAccionesAdaptador(ArrayList<Accion> listaAcciones){
        this.listaAcciones = listaAcciones;
    }


    public void setListaAcciones(ArrayList<Accion> listaAcciones) {
        this.listaAcciones = listaAcciones;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_buscar_acciones, viewGroup, false);
        v.setOnClickListener(this);
        return new MiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder miViewHolder, int i) {
        miViewHolder.bindAcciones(listaAcciones.get(i));
    }

    @Override
    public int getItemCount() {
        return listaAcciones.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }
}
