package com.turtleriot.autenticacion;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Autenticacion {

    private FirebaseAuth fba;
    private FirebaseUser user;

    private String usuario;
    private String passwd;

    public Autenticacion(){
        fba = FirebaseAuth.getInstance();
    }

    public void obtenerCuenta(String usuario, String passwd){
        this.usuario = usuario;
        this.passwd = passwd;
    }

    public boolean validarDatos(){
        if (usuario.isEmpty() || passwd.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public FirebaseAuth getFba() {
        return fba;
    }

    public void setUser(FirebaseUser currentUser) {
        user = currentUser;
    }

    public FirebaseUser getUser() {
        return user;
    }
}