package com.turtleriot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.turtleriot.fbDataBase.FireDataBase;
import com.turtleriot.javaBean.Accion;
import com.turtleriot.javaBean.Usuario;

public class InfoAccionesActivity extends AppCompatActivity {

    private ImageView ivBotonGuardar;

    private Accion accion;

    private TextView tvTituloACC;
    private TextView tvPlayaACC;
    private ImageView ivFotoACC;
    private TextView tvDescripcionACC;
    private TextView tvFechaACC;

    private FireDataBase fdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_acciones);

        getSupportActionBar().hide();

        accion = getIntent().getParcelableExtra("ACCION");

        ivBotonGuardar = findViewById(R.id.ivBotonGuardar);

        tvTituloACC = findViewById(R.id.tvTituloACC);
        tvPlayaACC = findViewById(R.id.tvPlayaACC);
        ivFotoACC = findViewById(R.id.ivFotoACC);
        tvDescripcionACC = findViewById(R.id.tvDescripcionACC);
        tvFechaACC = findViewById(R.id.tvFechaACC);

        tvTituloACC.setText(accion.getTitulo());
        //tvPlayaACC.setText(accion.getPlaya().getTitulo());
        Glide.with(ivFotoACC.getContext())
                .load(accion.getFoto())
                .into(ivFotoACC);
        tvDescripcionACC.setText(accion.getDescripcion());
        tvFechaACC.setText(accion.getFecha());

        fdb = new FireDataBase();
        fdb.obtenerclaveAccion(accion);
        c_ivBotonGuardar();
    }

    private void c_ivBotonGuardar() {
        ivBotonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = ((UsuarioApplication) getApplicationContext()).getClave();
                Usuario usuario = ((UsuarioApplication) getApplicationContext()).getUsuario();
                fdb.unirseAccion(usuario,key);
            }
        });
    }
}