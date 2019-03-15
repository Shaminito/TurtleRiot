package com.turtleriot.javaBean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Accion implements Parcelable {

    //NOMBRE
    private String propietario;
    //TITULO
    private String titulo;
    //FOTO
    private String foto;
    //PLAYA
    //private Playa playa;
    //FECHA
    private String fecha;
    //DESCRIPCIÓN
    private String descripcion;
    //USUARIOS SEGUIDOS
    private ArrayList<Usuario> seguidores = new ArrayList<>();

    public Accion(){}

    public Accion(String propietario, String titulo, String fecha, String descripcion, String foto){
        this.propietario = propietario;
        this.titulo = titulo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.foto = foto;
        seguidores = new ArrayList<>();
    }

    // Métodos utilizados para el firebase

    protected Accion(Parcel in) {
        propietario = in.readString();
        titulo = in.readString();
        foto = in.readString();
        fecha = in.readString();
        descripcion = in.readString();
        seguidores = in.createTypedArrayList(Usuario.CREATOR);
    }

    public static final Creator<Accion> CREATOR = new Creator<Accion>() {
        @Override
        public Accion createFromParcel(Parcel in) {
            return new Accion(in);
        }

        @Override
        public Accion[] newArray(int size) {
            return new Accion[size];
        }
    };

    public String getPropietario() {
        return propietario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public ArrayList<Usuario> getListaSeguidores() {
        return seguidores;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(propietario);
        dest.writeString(titulo);
        dest.writeString(foto);
        dest.writeString(fecha);
        dest.writeString(descripcion);
        dest.writeTypedList(seguidores);
    }
}
