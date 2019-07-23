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
import java.util.ArrayList;
import java.util.List;

public class actCrearEquipo extends AppCompatActivity implements View.OnClickListener {
    EditText edtTxtNombreEquipo, edtTxtUsuarios;
    Button btnBuscarUsuario, btnAgregarEquipo;
    Equipo equipo;
    Usuario usuario = new Usuario();
    Persistencia pm = new Persistencia();
    Boolean finished = false;
    String nuevoMiembro = "";
    List<Integer> listaUsuarios;
    Usuario usuarioNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_equipo);

        edtTxtNombreEquipo = (EditText) findViewById(R.id.edtTxtNombreEquipo);
        edtTxtUsuarios = (EditText) findViewById(R.id.edtTxtUsuarios);
        btnBuscarUsuario = (Button) findViewById(R.id.btnBuscarUsuario);
        btnAgregarEquipo = (Button) findViewById(R.id.btnAgregarEquipo);
        equipo = new Equipo();
        listaUsuarios = new ArrayList<>();

        try{
            usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");
        }catch(Exception e){
            Intent intent = new Intent(actCrearEquipo.this, Login.class);
            startActivity(intent);
        }

        btnBuscarUsuario.setOnClickListener(this);
        btnAgregarEquipo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        segundoPlano sp;
        switch(v.getId()){
            case R.id.btnBuscarUsuario:
                nuevoMiembro = edtTxtUsuarios.getText().toString();
                finished = false;
                sp = new segundoPlano();
                sp.execute();
                break;
            case R.id.btnAgregarEquipo:
                equipo.setNombre(edtTxtNombreEquipo.getText().toString());
                finished = true;
                sp = new segundoPlano();
                sp.execute();
                break;
        }
    }

    private class segundoPlano extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Integer result = 0;
            Boolean exito = false;
            if(finished){
                result = pm.agregarEquipo(equipo);
                pm.agregarEquipoUsuario(result, usuario.getId());
                for(int i = 0; i < listaUsuarios.size(); i++){
                    pm.agregarEquipoUsuario(result, listaUsuarios.get(i));
                }
                exito = true;
            }else {
                try{
                    usuarioNuevo = pm.seleccionUsuarioPorEmail(nuevoMiembro);
                    exito = true;
                }catch (Exception e){
                    exito = false;
                }
            }
            return exito;
        }

        @Override
        protected void onPostExecute(Boolean exito){
            if(finished){
                if (exito){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Equipo creado con exito.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(actCrearEquipo.this, CuentaUsuario.class);
                    intent.putExtra("Usuario", (Serializable) usuario);
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Error al crear el Equipo.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else{
                if(exito){
                    listaUsuarios.add(usuarioNuevo.getId());
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Equipo creado con exito.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Equipo creado con exito.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }
}