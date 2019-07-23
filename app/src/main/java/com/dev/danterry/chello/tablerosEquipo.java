package com.dev.danterry.chello;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.zip.Inflater;

public class tablerosEquipo extends AppCompatActivity {

    TextView nombreEquipo;
    LinearLayout contenedorTableros;
    Button botonRegresar;
    Usuario usuario;
    Equipo equipo;
    LayoutInflater inflater;
    Persistencia pm = new Persistencia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableros_equipo);

        inflater = getLayoutInflater();

        nombreEquipo = (TextView) findViewById(R.id.id_equipo_nombre);
        contenedorTableros = (LinearLayout) findViewById(R.id.id_equipo_contenedor);
        botonRegresar = (Button) findViewById(R.id.id_equipo_boton_regresar);

        try{
            usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");
            equipo = (Equipo) getIntent().getExtras().getSerializable("Equipo");
        }catch (Exception e){
            Intent intent = new Intent(tablerosEquipo.this, CuentaUsuario.class);
            startActivity(intent);
        }

        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tablerosEquipo.this, CuentaUsuario.class);
                intent.putExtra("Usuario", (Serializable) usuario);
                startActivity(intent);
            }
        });

        segundoPlano sp = new segundoPlano();
        sp.execute();
    }

    private class segundoPlano extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                equipo.setTableros(pm.obtenerTablerosDeEquipo(equipo.getId()));
                return true;
            }catch(Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean b){
            for (int i = 0; i < equipo.getTableros().size(); i++){
                final int numI = i;
                View viewBotonTablero = inflater.inflate(R.layout.element_equipo_boton, contenedorTableros, false);
                Button botonTablero = viewBotonTablero.findViewById(R.id.id_equipo_boton_tablero);
                botonTablero.setText(equipo.getTableros().get(i).getTitulo());
                botonTablero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(tablerosEquipo.this, MostrarTablero.class);
                        intent.putExtra("Usuario", usuario);
                        intent.putExtra("Tablero", equipo.getTableros().get(numI));
                        startActivity(intent);
                    }
                });
                contenedorTableros.addView(botonTablero);
            }
        }
    }
}
