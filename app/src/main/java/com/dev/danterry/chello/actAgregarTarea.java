package com.dev.danterry.chello;

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
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class actAgregarTarea extends AppCompatActivity implements View.OnClickListener {

    EditText edtTxtDescripcionTarea;
    DatePicker dpFechaVencimiento;
    TimePicker tpHoraVencimiento;
    Button btnAnadirTarea;
    TextView txtListaTareas;
    Date fechaVencimiento = new Date();
    Calendar calendar = Calendar.getInstance();
    Nota nota = new Nota();
    Tablero tablero = new Tablero();
    Usuario usuario = new Usuario();
    Persistencia pm = new Persistencia();
    Tarea tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);

        edtTxtDescripcionTarea = (EditText) findViewById(R.id.edtTxtDescripcionTarea);
        dpFechaVencimiento = (DatePicker) findViewById(R.id.dpFechaVencimiento);
        tpHoraVencimiento = (TimePicker) findViewById(R.id.tpHoraVencimiento);
        btnAnadirTarea = (Button) findViewById(R.id.btnAnadirTarea);
        txtListaTareas = (TextView) findViewById(R.id.txtListaTareas);

        int dia = dpFechaVencimiento.getDayOfMonth();
        int mes = dpFechaVencimiento.getMonth() + 1;
        int anio = dpFechaVencimiento.getYear();

        fechaVencimiento.setDate(dia);
        fechaVencimiento.setMonth(mes);
        fechaVencimiento.setYear(anio);

        calendar.set(anio, mes, dia);

        try{
            nota = (Nota) getIntent().getExtras().getSerializable("Nota");
            tablero = (Tablero) getIntent().getExtras().getSerializable("Tablero");
            usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");
        } catch (Exception e){
            Intent intent = new Intent(actAgregarTarea.this, verNota.class);
            startActivity(intent);
        }
        String texto = txtListaTareas.getText().toString() + nota.getTitulo();
        txtListaTareas.setText(texto);
        btnAnadirTarea.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        tarea = new Tarea();
        tarea.setDescripcion(edtTxtDescripcionTarea.getText().toString());
        tarea.setVencimiento(calendar.getTime().toString());
        tarea.setEstado(false);
        tarea.setIdActividad(nota.getId());

        segundoPlano sp = new segundoPlano();
        sp.execute();
    }

    private class segundoPlano extends AsyncTask<Void,Void,Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            try{
                Integer b = pm.agregarTarea(tarea, nota.getId(), usuario.getId());
                return b;
            }catch(Exception e){
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer b){
            if(b!=0){
                Intent iMostrarDatos = new Intent(actAgregarTarea.this, verNota.class);
                iMostrarDatos.putExtra("Tablero", (Serializable) tablero);
                iMostrarDatos.putExtra("Usuario", (Serializable) usuario);
                iMostrarDatos.putExtra("Nota", (Serializable) nota);
                startActivity(iMostrarDatos);
            }
        }
    }
}