package com.turtleriot.acciones;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.turtleriot.R;
import com.turtleriot.UsuarioApplication;
import com.turtleriot.fbDataBase.FireDataBase;
import com.turtleriot.javaBean.Accion;
import com.turtleriot.storage.StorageFotos;

import java.util.Calendar;

public class CrearAccionesActivity extends AppCompatActivity {

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "/";

    private TextInputLayout tilTituloACC;
    private ImageView ivFotoACC;
    private ImageView ivPlayaACC;
    private TextInputLayout tilDescripcionACC;

    public final Calendar ca = Calendar.getInstance();
    //fecha
    final int mes = ca.get(Calendar.MONTH);
    final int dia = ca.get(Calendar.DAY_OF_MONTH);
    final int anio = ca.get(Calendar.YEAR);
    private ImageView ivCrearAcciones;
    //tiempo
    final int hora = ca.get(Calendar.HOUR_OF_DAY);
    final int minuto = ca.get(Calendar.MINUTE);
    //ventanas
    TextView etFecha, etHora;
    ImageButton ibObtenerFecha, ibObtenerHora;

    private FireDataBase fdb;

    //FOTOS
    private StorageFotos sfFotoAcciones;
    private String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_acciones);

        getSupportActionBar().hide();

        tilTituloACC = findViewById(R.id.tilTituloACC);
        ivFotoACC = findViewById(R.id.ivFotoACC);
        ivPlayaACC = findViewById(R.id.ivPlayaACC);
        tilDescripcionACC = findViewById(R.id.tilDescripcionACC);

        ivCrearAcciones = findViewById(R.id.ivCrearAcciones);

        fdb = new FireDataBase();

        etFecha = findViewById(R.id.et_mostrar_fecha_picker);
        etHora = findViewById(R.id.et_mostrar_hora_picker);

        ibObtenerFecha = findViewById(R.id.ib_obtener_fecha);
        ibObtenerHora = findViewById(R.id.ib_obtener_hora);

        sfFotoAcciones = new StorageFotos();

        c_ivFotoACC();
        c_ivPlayaACC();
        c_ivCrearAcciones();
        ib_obtener_fecha ();
    }

    private void ib_obtener_fecha() {
        ibObtenerFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerFecha();


            }
        });
        ibObtenerHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recogerHora();
            }
        });
    }

    private void recogerHora() {
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }

                String hora = horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM;

                etHora.setText(hora);
            }

        }, hora, minuto, false);

        recogerHora.show();
    }

    private void recogerFecha() {

        DatePickerDialog recogerfecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;

                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                String fechaf = diaFormateado + BARRA + mesFormateado + BARRA + year;
                etFecha.setText(fechaf);

            }
        },anio,mes,dia);
        recogerfecha.show();
    }

    private void c_ivFotoACC() {
        ivFotoACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent,
                        "Complete la acción usando"), StorageFotos.RC_FOTO_PLAYA);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == StorageFotos.RC_FOTO_PLAYA){
            if(resultCode == Activity.RESULT_OK){
                sfFotoAcciones.subirFoto(data, this);
                foto = sfFotoAcciones.getFoto();
                Glide.with(ivFotoACC.getContext())
                        .load(foto)
                        .into(ivFotoACC);
            }
        }
    }

    private void c_ivPlayaACC() {
        ivPlayaACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO OBTENER PLAYA
            }
        });
    }

    private void c_ivCrearAcciones() {
        ivCrearAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CREAR UNA NUEVA ACCION PARA EL FIREBASE

                //NOMBRE
                String propietario = ((UsuarioApplication) getApplicationContext()).getUsuario().getUser();
                //TITULO
                String titulo = tilTituloACC.getEditText().getText().toString();
                //PLAYA
                //Playa playa =
                //FECHA
                String fecha = etFecha.getText().toString()+" "+etHora.getText().toString();
                //DESCRIPCIÓN
                String descripcion = tilDescripcionACC.getEditText().getText().toString();
                if(verificarAccion()){
                    fdb.guardarAccion(new Accion(propietario,titulo,fecha,descripcion,foto));
                    finish();
                }
                else{
                    Toast.makeText(CrearAccionesActivity.this,getString(R.string.toast_AccionVacio),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verificarAccion() {
        return !tilTituloACC.getEditText().getText().toString().isEmpty() && !(etFecha.getText().toString().isEmpty() || etHora.getText().toString().isEmpty()) && !tilDescripcionACC.getEditText().getText().toString().isEmpty();
    }
}
