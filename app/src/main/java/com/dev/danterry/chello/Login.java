package com.dev.danterry.chello;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    Button btnLogin;
    EditText txtEmail;
    EditText txtContra;
    TextView tvRegister;
    Persistencia pm = new Persistencia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.btnLogin = (Button)findViewById(R.id.btnLogin);
        this.txtEmail = (EditText)findViewById(R.id.txtEmail);
        this.txtContra = (EditText)findViewById(R.id.txtContra);
        this.tvRegister = (TextView)findViewById(R.id.tvRegister);

        View.OnClickListener Loguearse = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtEmail.getText().toString().isEmpty() || txtContra.getText().toString().isEmpty()){

                    Toast toast1 = Toast.makeText(getApplicationContext(),
                              "Favor de no dejar campos vacíos",
                                   Toast.LENGTH_SHORT);
                    toast1.show();

                }else{
                    segundoPlano sp = new segundoPlano();
                    sp.execute();
                }
            }
        };

        View.OnClickListener Registrarse = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent CreateAccount = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(CreateAccount);
            }
        };


        btnLogin.setOnClickListener(Loguearse);

        tvRegister.setOnClickListener(Registrarse);
    }

    private class segundoPlano extends AsyncTask<Void,Void,Object> {

        @Override
        protected Object doInBackground(Void... voids) {
            Usuario usuario = pm.login(txtEmail.getText().toString(), txtContra.getText().toString());
            return usuario;
        }

        @Override
        protected void onPostExecute(Object Result){
            Usuario usuario = (Usuario) Result;
            if(usuario!=null){
                Intent iCreateAccount = new Intent(Login.this, CuentaUsuario.class);
                iCreateAccount.putExtra("Usuario", usuario);
                startActivity(iCreateAccount);
            }else{
                Toast toast2 = Toast.makeText(getApplicationContext(),
                        "Email y/o contraseÃ±a incorrectos.",
                        Toast.LENGTH_SHORT);
                toast2.show();
            }
        }
    }
}
