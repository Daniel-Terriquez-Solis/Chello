package com.dev.danterry.chello;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class actCrearTablero extends AppCompatActivity implements View.OnClickListener {
    EditText edtTxtNombreTablero;
    Button btnAgregarTablero;
    Usuario usuario = new Usuario();
    Persistencia pm = new Persistencia();
    Tablero tableroNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tablero);
        edtTxtNombreTablero = (EditText) findViewById(R.id.edtTxtNombreTablero);
        btnAgregarTablero = (Button) findViewById(R.id.btnAgregarTablero);

        try{
            usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");
        }catch(Exception e){
            Intent intent = new Intent(actCrearTablero.this, Login.class);
            startActivity(intent);
        }

        btnAgregarTablero.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        tableroNuevo = new Tablero();

        tableroNuevo.setTitulo(edtTxtNombreTablero.getText().toString());

        segundoPlano sp = new segundoPlano();
        sp.execute();
    }

    private class segundoPlano extends AsyncTask<Void,Void,Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            Integer result = pm.agregarTablero(tableroNuevo);
            if(result>0){
                pm.crearTableroUsuario(result, usuario.getId());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result){
            if(result>0){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Tablero creado con exito.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Error al crear el tablero.",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
            Intent intent = new Intent(actCrearTablero.this, CuentaUsuario.class);
            intent.putExtra("Usuario", (Serializable) usuario);
            startActivity(intent);
        }
    }
}