package com.dev.danterry.chello;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    Button btnRegister;
    EditText txtNombre;
    EditText txtEmailRegister;
    EditText txtContraRegister;
    EditText txtRContra;
    Persistencia pm = new Persistencia();
    Usuario usuario = new Usuario();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        this.txtEmailRegister = (EditText)findViewById(R.id.txtEmailRegister);
        this.txtContraRegister = (EditText)findViewById(R.id.txtContraRegister);
        this.txtRContra = (EditText)findViewById(R.id.txtRContra);
        this.btnRegister = (Button)findViewById(R.id.btnRegister);
        this.txtNombre = (EditText)findViewById(R.id.txtNombre);

        View.OnClickListener Registrarse = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtEmailRegister.getText().toString().isEmpty() ||
                   txtContraRegister.getText().toString().isEmpty() ||
                   txtRContra.getText().toString().isEmpty() ||
                   txtNombre.getText().toString().isEmpty()){

                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Favor de no dejar campos vacíos",
                            Toast.LENGTH_SHORT);
                    toast1.show();

                }else{

                    if(!txtContraRegister.getText().toString().equals(txtRContra.getText().toString())){

                        Toast toast2 = Toast.makeText(getApplicationContext(),
                                "Los campos de contraseñas no coinciden",
                                Toast.LENGTH_SHORT);
                        toast2.show();


                    }else{
                        usuario = new Usuario(txtNombre.getText().toString(),
                                txtEmailRegister.getText().toString(),
                                txtContraRegister.getText().toString());
                        if(!txtEmailRegister.getText().toString().isEmpty()){
                            segundoPlano sp = new segundoPlano();
                            sp.execute();
                        }
                        }
                    }
                }
        };

        btnRegister.setOnClickListener(Registrarse);
    }

    private class segundoPlano extends AsyncTask<Void,Void,Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            int idUsuario = pm.agregarUsuario(usuario);
            return idUsuario;
        }

        @Override
        protected void onPostExecute(Integer Result){
            if(Result!=0){
                Intent intent = new Intent(CreateAccount.this, Login.class);
                startActivity(intent);
            }else{

                Toast toast2 = Toast.makeText(getApplicationContext(),
                        "Error al registrar",
                        Toast.LENGTH_SHORT);
                toast2.show();

            }
        }
    }
}