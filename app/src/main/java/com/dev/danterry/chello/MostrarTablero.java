package com.dev.danterry.chello;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MostrarTablero extends AppCompatActivity implements Serializable {

    HorizontalScrollView scrollView;
    Button btnRegresar, btnAnadir;
    TextView titulo;

    Tablero tablero = new Tablero();
    List<Modulo> modulos;
    Usuario usuario = new Usuario();
    int metodo = 0;

    LayoutInflater layoutInflater;

    Persistencia pm = new Persistencia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        layoutInflater = getLayoutInflater();
        this.btnRegresar = (Button) findViewById(R.id.id_btn_regresar);
        this.btnAnadir = (Button) findViewById(R.id.id_btn_anadir_modulo);
        this.titulo = (TextView) findViewById(R.id.id_ttl_tabla);
        this.scrollView = (HorizontalScrollView) findViewById(R.id.idHorizontalView);

        try{
            tablero = (Tablero) getIntent().getExtras().getSerializable("Tablero");
            usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");
        }catch (Exception e){
            Intent intent = new Intent(MostrarTablero.this, CuentaUsuario.class);
            startActivity(intent);
        }

        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarTablero.this, actCrearModulo.class);
                intent.putExtra("Usuario", (Serializable) usuario);
                intent.putExtra("Tablero", (Serializable) tablero);
                startActivity(intent);
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarTablero.this, CuentaUsuario.class);
                intent.putExtra("Usuario", (Serializable) usuario);
                startActivity(intent);
            }
        });


        this.titulo.setText(tablero.getTitulo());
        segundoPlano sp = new segundoPlano();
        sp.execute();
    }

    private class segundoPlano extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
                try{
                    modulos = pm.obtenerModulosDeTablero(tablero.getId());

                    for (int i = 0; i < modulos.size(); i++){
                        List<Nota> notas = pm.seleccionarNotasDeModulo(modulos.get(i).getId());
                        for(int j = 0; j < notas.size(); j++){
                            notas.get(j).setTareas(new ArrayList<Tarea>());
                            notas.get(j).setComentarios(new ArrayList<Comentario>());
                        }
                        modulos.get(i).setNotas(notas);
                    }
                    return true;
                }catch(Exception e){
                    return false;
                }
        }
        @Override
        protected void onPostExecute(Boolean b){
            View viewContainer = layoutInflater.inflate(R.layout.element_container, scrollView, false);
            LinearLayout container = viewContainer.findViewById(R.id.idContainer);
            for (int i = 0; i < modulos.size(); i++) {
                final int numI = i;
                View viewModulo = layoutInflater.inflate(R.layout.element_modulo, container, false);
                final LinearLayout modulo = (LinearLayout) viewModulo.findViewById(R.id.idModulo);

                View viewTituloModulo = layoutInflater.inflate(R.layout.element_modulo_titulo, modulo, false);
                TextView tituloModulo = (TextView) viewTituloModulo.findViewById(R.id.idElementModuloTitulo);
                tituloModulo.setText(modulos.get(i).getNombre());
                modulo.addView(tituloModulo);
                View viewScrollModulo = layoutInflater.inflate(R.layout.element_modulo_scroll, modulo, false);
                ScrollView scrollModulo = (ScrollView) viewScrollModulo.findViewById(R.id.idElementModuloScroll);
                View viewButtonContainer = layoutInflater.inflate(R.layout.element_modulo_scroll_container, scrollModulo, false);
                LinearLayout buttonContainer = (LinearLayout) viewButtonContainer.findViewById(R.id.idElementModuloScrollContainer);
                List<Nota> notas = modulos.get(i).getNotas();

                for (int j = 0; j < notas.size(); j++){
                    final int numJ = j;
                    final Nota notaActual = notas.get(j);
                    LinearLayout buttonRow = new LinearLayout(MostrarTablero.this);
                    buttonRow.setOrientation(LinearLayout.HORIZONTAL);
                    CheckBox check = new CheckBox(MostrarTablero.this);
                    if (modulos.get(numI).getNotas().get(numJ).isCompletado()){
                        check.setChecked(true);
                    }else{
                        check.setChecked(false);
                    }
                    buttonRow.addView(check);

                    View viewButton = layoutInflater.inflate(R.layout.element_modulo_scroll_boton, buttonContainer, false);
                    Button botonNota = (Button) viewButton.findViewById(R.id.idElementModuloScrollButton);
                    botonNota.setText(notas.get(j).getTitulo());
                    botonNota.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tablero.setModulos(modulos);
                            Intent intent = new Intent(MostrarTablero.this, verNota.class);
                            intent.putExtra("Tablero", (Serializable) tablero);
                            intent.putExtra("Nota", (Serializable) notaActual);
                            intent.putExtra("Usuario", (Serializable) usuario);
                            startActivity(intent);
                            finish();
                        }
                    });
                    buttonRow.addView(botonNota);
                    buttonContainer.addView(buttonRow);
                }
                View viewButtonAnadir = layoutInflater.inflate(R.layout.element_modulo_boton_notanueva, buttonContainer, false);
                Button botonAnadir = (Button) viewButtonAnadir.findViewById(R.id.idElementModuloBotonNotaNueva);
                botonAnadir.setText(getResources().getString(R.string.btn_nuevaTarea));
                botonAnadir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tablero.setModulos(modulos);
                        Intent intent = new Intent(MostrarTablero.this, actAgregarActividad.class);
                        intent.putExtra("Usuario", (Serializable) usuario);
                        intent.putExtra("Tablero", (Serializable) tablero);
                        intent.putExtra("Modulo", (Serializable) modulos.get(numI));
                        startActivity(intent);
                    }
                });
                buttonContainer.addView(botonAnadir);
                scrollModulo.addView(buttonContainer);
                modulo.addView(scrollModulo);
                container.addView(modulo);
            }
            View viewSpace = layoutInflater.inflate(R.layout.element_space, container, false);
            LinearLayout space = (LinearLayout) viewSpace.findViewById(R.id.idSpace);
            container.addView(space);
            scrollView.addView(container);
        }
    }

}
