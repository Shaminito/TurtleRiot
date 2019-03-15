package com.turtleriot.fbDataBase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.turtleriot.javaBean.Accion;
import com.turtleriot.javaBean.BuscarAccionesAdaptador;
import com.turtleriot.javaBean.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireDataBase {

    private DatabaseReference dbR;
    private ChildEventListener cel;

    private ArrayList<Accion> listaAcciones;
    private BuscarAccionesAdaptador adaptador;

    private String keyAccion;

    public FireDataBase(){
        dbR = FirebaseDatabase.getInstance().getReference().child("acciones");
    }

    public void arrancarChildItemListener(){
        addChildItemListener();
    }

    public void guardarAccion(Accion accion) {
        dbR.child(dbR.push().getKey()).setValue(accion);
    }

    public void obtenerclaveAccion(Accion accionParam){
        consultarAcciones(accionParam);
    }

    public void unirseAccion(Usuario usuario, String key){
        Map<String,Object> mapa = new HashMap<>();
        mapa.put(keyAccion+"/seguidores/"+key,usuario);
        dbR.updateChildren(mapa);
    }

    private void addChildItemListener() {
        if(cel == null){
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Accion accion = dataSnapshot.getValue(Accion.class);
                    //if(accion.getPropietario() != ){
                        listaAcciones.add(accion);
                    //}
                    adaptador.notifyItemInserted(listaAcciones.size() - 1);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //TODO CHANGED
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    //TODO REMOVED
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            dbR.addChildEventListener(cel);
        }
    }

    private void consultarAcciones(final Accion accionparam) {
        if(cel == null){
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Accion accion = dataSnapshot.getValue(Accion.class);
                    if(accion.getTitulo().equals(accionparam.getTitulo())){
                        keyAccion = dataSnapshot.getKey();

                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //TODO CHANGED
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    //TODO REMOVED
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            dbR.addChildEventListener(cel);
        }
    }

    public DatabaseReference getDbR() {
        return dbR;
    }

    public void setListaAcciones(ArrayList<Accion> listaAcciones) {
        this.listaAcciones = listaAcciones;
    }

    public void setAdaptador(BuscarAccionesAdaptador adaptador) {
        this.adaptador = adaptador;
    }
}
