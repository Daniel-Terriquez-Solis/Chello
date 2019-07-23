package com.dev.danterry.chello;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CuentaUsuario extends AppCompatActivity {
    TextView txtBienvenida, txtCorreo;
    ScrollView llTableros;
    ScrollView llEquipos;
    Button btnCerrarSesion;
    Usuario usuario;
    List<Tablero> tableros;
    List<Equipo> equipos;
    LayoutInflater inflater;
    Persistencia persistencia = new Persistencia();
    int metodo = 0;
    segundoPlano sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_usuario);

        inflater = getLayoutInflater();

        txtBienvenida = (TextView) findViewById(R.id.txtBienvenida);
        txtCorreo = (TextView) findViewById(R.id.txtCorreo);
        llTableros = (ScrollView) findViewById(R.id.llTableros);
        llEquipos = (ScrollView) findViewById(R.id.llEquipos);
        btnCerrarSesion = (Button) findViewById(R.id.btnLogout);

        try {
            usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");

            String txtB = "Bienvenido " + usuario.getNombre();
            txtBienvenida.setText(txtB);
            String txtC = "Correo: " + usuario.getEmail();
            txtCorreo.setText(txtC);

        } catch (Exception e) {
            Intent iLogIn = new Intent(this, Login.class);
            startActivity(iLogIn);
        }

        try {
            sp = new segundoPlano();
            sp.execute();

        } catch (Exception e) {
            tableros = new ArrayList<>();
            equipos = new ArrayList<>();
        }

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuentaUsuario.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private class segundoPlano extends AsyncTask<Void,Void,Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
                tableros = persistencia.obtenerTablerosDeUsuario(usuario.getId());
                equipos = persistencia.obtenerEquiposDeUsuario(usuario.getId());
                return 0;
        }

        @Override
        protected void onPostExecute(Integer result){
                llenarTableros();
                llenarEquipos();
        }
    }

    private void llenarTableros(){
        LinearLayout container1 = new LinearLayout(this);
        container1.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < tableros.size(); i++) {
            final Tablero tableroActual = tableros.get(i);
            View viewBotonTablero = inflater.inflate(R.layout.element_usuario_boton_tablero, container1, false);
            Button botonTablero = viewBotonTablero.findViewById(R.id.id_usuario_btn_tablero);
            botonTablero.setText(tableros.get(i).getTitulo());
            botonTablero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CuentaUsuario.this, MostrarTablero.class);
                    intent.putExtra("Usuario", (Serializable) usuario);
                    intent.putExtra("Tablero", (Serializable) tableroActual);
                    startActivity(intent);
                }
            });
            container1.addView(botonTablero);
        }

        View viewBotonTablero = inflater.inflate(R.layout.element_usuario_boton_tablero, container1, false);
        Button botonTablero = viewBotonTablero.findViewById(R.id.id_usuario_btn_tablero);
        botonTablero.setText("+ Nuevo tablero");
        botonTablero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuentaUsuario.this, actCrearTablero.class);
                intent.putExtra("Usuario", (Serializable) usuario);
                startActivity(intent);
            }
        });

        container1.addView(botonTablero);

        llTableros.addView(container1);
    }

    private void llenarEquipos(){
        LinearLayout container2 = new LinearLayout(this);
        container2.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < equipos.size(); i++) {
            final Equipo equipoActual = equipos.get(i);
            View viewBotonEquipo = inflater.inflate(R.layout.element_usuario_boton_tablero, container2, false);
            Button botonEquipo = viewBotonEquipo.findViewById(R.id.id_usuario_btn_tablero);
            botonEquipo.setText(equipos.get(i).getNombre());
            botonEquipo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CuentaUsuario.this, tablerosEquipo.class);
                    intent.putExtra("Usuario", usuario);
                    intent.putExtra("Equipo", equipoActual);
                    startActivity(intent);
                }
            });
            container2.addView(botonEquipo);
        }

        View viewBotonEquipo = inflater.inflate(R.layout.element_usuario_boton_tablero, container2, false);
        Button botonEquipo = viewBotonEquipo.findViewById(R.id.id_usuario_btn_tablero);
        botonEquipo.setText("+ Nuevo equipo");
        botonEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuentaUsuario.this, actCrearEquipo.class);
                intent.putExtra("Usuario", usuario);
                startActivity(intent);
            }
        });

        container2.addView(botonEquipo);

        llEquipos.addView(container2);
    }
}

