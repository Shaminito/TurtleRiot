package com.turtleriot;

import android.app.Application;

import com.turtleriot.javaBean.Usuario;

public class UsuarioApplication extends Application {

    Usuario usuario;
    String clave;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
