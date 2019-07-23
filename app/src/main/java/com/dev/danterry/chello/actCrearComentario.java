package com.dev.danterry.chello;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class actCrearComentario extends AppCompatActivity implements View.OnClickListener {
    EditText edtTxtComentario;
    Button btnAnadirComentario;
    Nota nota = new Nota();
    Usuario usuario = new Usuario();
    Tablero tablero = new Tablero();
    Persistencia pm = new Persistencia();
    Comentario comentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_comentario);

        edtTxtComentario = (EditText) findViewById(R.id.edtTxtComentario);
        btnAnadirComentario = (Button) findViewById(R.id.btnAnadirComentario);

        try{
            usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");
            nota = (Nota) getIntent().getExtras().getSerializable("Nota");
            tablero = (Tablero) getIntent().getExtras().getSerializable("Tablero");
        }catch(Exception e){
            Intent iCrearComentario = new Intent(this, verNota.class);
            startActivity(iCrearComentario);
        }

        btnAnadirComentario.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Date date = new Date();
            comentario = new Comentario();
            comentario.setDescripcion(edtTxtComentario.getText().toString());
            comentario.setFecha(date.toString());
            comentario.setUsuario(usuario);

        Intent iVerTarea = new Intent(this, verNota.class);
        iVerTarea.putExtra("Tablero", (Serializable) tablero);
        iVerTarea.putExtra("Nota", (Serializable) nota);
        iVerTarea.putExtra("Usuario", (Serializable) usuario);
        startActivity(iVerTarea);
    }

    private class segundoPlano extends AsyncTask<Void,Void,Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            try{
                int b = pm.crearComentario(comentario, usuario.getId(), nota.getId());
                return b;
            }catch(Exception e){
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer b){
            if(b!=0){
                Intent iMostrarDatos = new Intent(actCrearComentario.this, verNota.class);
                iMostrarDatos.putExtra("Tablero", (Serializable) tablero);
                iMostrarDatos.putExtra("Usuario", (Serializable) usuario);
                iMostrarDatos.putExtra("Nota", (Serializable) nota);
                startActivity(iMostrarDatos);
            }
        }
    }
}