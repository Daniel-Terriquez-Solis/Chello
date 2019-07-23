package com.dev.danterry.chello;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class actCrearModulo extends AppCompatActivity implements View.OnClickListener {
    EditText edtTxtNombreModulo;
    Button btnAgregarModulo;
    Tablero tablero = new Tablero();
    Usuario usuario = new Usuario();
    Persistencia pm = new Persistencia();
    Modulo modulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_modulo);
        edtTxtNombreModulo = (EditText) findViewById(R.id.edtTxtNombreModulo);
        btnAgregarModulo = (Button) findViewById(R.id.btnAgregarModulo);

        try{
            tablero = (Tablero) getIntent().getExtras().getSerializable("Tablero");
            usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");
        }catch (Exception e){
            Intent intent = new Intent(actCrearModulo.this, MostrarTablero.class);
            startActivity(intent);
        }
        btnAgregarModulo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        modulo = new Modulo();
        modulo.setNombre(edtTxtNombreModulo.getText().toString());
        segundoPlano sp = new segundoPlano();
        sp.execute();
    }

    private class segundoPlano extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                pm.agregarModulo(modulo, tablero.getId());
                return true;
            }catch(Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean b){
            Intent iMostrarDatos = new Intent(actCrearModulo.this, MostrarTablero.class);
            iMostrarDatos.putExtra("Usuario", (Serializable) usuario);
            iMostrarDatos.putExtra("Tablero", (Serializable) tablero);
            startActivity(iMostrarDatos);
        }
    }
}