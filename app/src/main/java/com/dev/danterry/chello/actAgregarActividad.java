package com.dev.danterry.chello;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class actAgregarActividad extends AppCompatActivity implements View.OnClickListener {

    EditText edtTxtTituloActividad;
    EditText edtTxtDescripcionActividad;
    DatePicker dpFechaVencimiento;
    TimePicker tpHoraVencimiento;
    Button btnAnadirNota;
    Modulo modulo;
    TextView txtListaTareas;
    Date fechaVencimiento = new Date();
    Calendar calendar = Calendar.getInstance();
    Nota nota = new Nota();
    Usuario usuario = new Usuario();
    Tablero tablero = new Tablero();
    Persistencia pm = new Persistencia();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);

        edtTxtTituloActividad = (EditText) findViewById(R.id.edtTxtTituloActividad);
        edtTxtDescripcionActividad = (EditText) findViewById(R.id.edtTxtDescripcionActividad);
        dpFechaVencimiento = (DatePicker) findViewById(R.id.dpFechaVencimiento);
        tpHoraVencimiento = (TimePicker) findViewById(R.id.tpHoraVencimiento);
        btnAnadirNota = (Button) findViewById(R.id.btnAnadirNota);
        txtListaTareas = (TextView) findViewById(R.id.txtListaTareas);

        int dia = dpFechaVencimiento.getDayOfMonth();
        int mes = dpFechaVencimiento.getMonth() + 1;
        int anio = dpFechaVencimiento.getYear();

        fechaVencimiento.setDate(dia);
        fechaVencimiento.setMonth(mes);
        fechaVencimiento.setYear(anio);

        calendar.set(anio, mes, dia);


        try{
            tablero = (Tablero) getIntent().getExtras().getSerializable("Tablero");
            modulo = (Modulo) getIntent().getExtras().getSerializable("Modulo");
            usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");
        } catch (Exception e){
            Intent iAgregarActividad = new Intent(actAgregarActividad.this, MostrarTablero.class);
            startActivity(iAgregarActividad);
        }

        txtListaTareas.setText(txtListaTareas.getText().toString() + modulo);

        btnAnadirNota.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            nota.setTitulo(edtTxtTituloActividad.getText().toString());
            nota.setDescripcion(edtTxtDescripcionActividad.getText().toString());
            nota.setVencimiento(calendar.getTime().toString());

            segundoPlano sp = new segundoPlano();
            sp.execute();
        } catch (Exception e){
            Toast toast = Toast.makeText(this, "Error, llenar todos los campos", Toast.LENGTH_SHORT);

            toast.show();
        }
    }

    private class segundoPlano extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                pm.agregarNota(nota, modulo.getId());
                return true;
            }catch(Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean b){
            Intent iMostrarDatos = new Intent(actAgregarActividad.this, MostrarTablero.class);
            iMostrarDatos.putExtra("Tablero", (Serializable) tablero);
            iMostrarDatos.putExtra("Usuario", (Serializable) usuario);
            startActivity(iMostrarDatos);
        }
    }
}