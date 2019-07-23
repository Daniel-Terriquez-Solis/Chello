package com.dev.danterry.chello;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;

public class verNota extends AppCompatActivity implements Serializable {

    Usuario usuario = new Usuario();
    Tablero tablero = new Tablero();
    Nota nota = new Nota();
    TextView titulo, descripcion, lblTareas, lblComentarios, notaFecha;
    Button btnRegresar;
    ScrollView scrollTareas, scrollComentarios;
    Modulo modulo;
    Persistencia pm = new Persistencia();

    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_nota);

        titulo = (TextView) findViewById(R.id.id_ttl_nota);
        descripcion = (TextView) findViewById(R.id.id_text_nota_descripcion);
        btnRegresar = (Button) findViewById(R.id.id_btn_nota_regresar);
        scrollTareas = (ScrollView) findViewById(R.id.idScrollTareas);
        scrollComentarios = (ScrollView) findViewById(R.id.idScrollComentarios);
        lblTareas = (TextView) findViewById(R.id.id_label_nota_tareas);
        lblComentarios = (TextView) findViewById(R.id.id_label_nota_comentarios);
        notaFecha = (TextView) findViewById(R.id.id_text_nota_fecha);

        inflater = getLayoutInflater();

        try{
            usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");
            tablero = (Tablero) getIntent().getExtras().getSerializable("Tablero");
            nota = (Nota) getIntent().getExtras().getSerializable("Nota");
        }catch(Exception e){
            Intent intent = new Intent(verNota.this, MostrarTablero.class);
            startActivity(intent);
        }

        titulo.setText(nota.getTitulo());
        notaFecha.setText(nota.getVencimiento().toString());
        descripcion.setText(nota.getDescripcion());

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(verNota.this, MostrarTablero.class);
                intent.putExtra("Tablero", (Serializable) tablero);
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
                nota.setTareas(pm.obtenerTareasDeActividad(nota.getId()));
                nota.setComentarios(pm.ObtenerComentariosDeActividad(nota.getId()));
                return true;
            }catch(Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean b){
            if(b){
                LinearLayout layoutTareas = new LinearLayout(verNota.this);
                LinearLayout layoutComentarios = new LinearLayout(verNota.this);

                for(int i = 0; i < nota.getTareas().size(); i++){
                    final int numI = i;
                    Tarea tareaActual = nota.getTareas().get(i);
                    LinearLayout rowTarea = new LinearLayout(verNota.this);
                    rowTarea.setOrientation(LinearLayout.HORIZONTAL);

                    CheckBox check = new CheckBox(verNota.this);
                    if (nota.getTareas().get(numI).isEstado()){
                        check.setChecked(true);
                    }else{
                        check.setChecked(false);
                    }
                    check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (nota.getTareas().get(numI).isEstado()){
                                nota.getTareas().get(numI).setEstado(false);
                            }else{
                                nota.getTareas().get(numI).setEstado(true);
                            }
                        }
                    });
                    rowTarea.addView(check);

                    LinearLayout layoutTarea = new LinearLayout(verNota.this);
                    layoutTarea.setOrientation(LinearLayout.VERTICAL);

                    View viewFechaTarea = inflater.inflate(R.layout.element_nota_label_tarea_fecha, layoutTarea, false);
                    TextView labelTareaFecha = (TextView) viewFechaTarea.findViewById(R.id.id_lbl_tarea_fecha);
                    labelTareaFecha.setText(tareaActual.getVencimiento());
                    layoutTarea.addView(labelTareaFecha);

                    View viewLabelTarea = inflater.inflate(R.layout.element_nota_label_tarea_texto, layoutTarea, false);
                    TextView labelTareaTexto = (TextView) viewLabelTarea.findViewById(R.id.id_lbl_tarea);
                    labelTareaTexto.setText(tareaActual.getDescripcion());
                    layoutTarea.addView(labelTareaTexto);

                    rowTarea.addView(layoutTarea);

                    layoutTareas.addView(rowTarea);
                }

                View viewBoton = inflater.inflate(R.layout.element_nota_boton_tareaocomentarionuevo, layoutTareas, false);
                Button botonTareaNueva = (Button) viewBoton.findViewById(R.id.idElementModuloBotonTareaComentarioNuevo);
                botonTareaNueva.setText("+ Tarea nueva");
                botonTareaNueva.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(verNota.this, actAgregarTarea.class);
                        intent.putExtra("Usuario", usuario);
                        intent.putExtra("Tablero", tablero);
                        intent.putExtra("Nota", nota);
                        intent.putExtra("Modulo", modulo);
                        startActivity(intent);
                    }
                });
                layoutTareas.addView(botonTareaNueva);

                scrollTareas.addView(layoutTareas);

                for(int i = 0; i < nota.getComentarios().size(); i++){
                    Comentario comentarioActual = nota.getComentarios().get(i);
                    LinearLayout layoutComentario = new LinearLayout(verNota.this);
                    layoutComentario.setOrientation(LinearLayout.VERTICAL);

                    View viewComentarioTexto = inflater.inflate(R.layout.element_nota_label_comentario_texto, layoutComentario, false);
                    TextView comentarioTexto = viewComentarioTexto.findViewById(R.id.id_label_comentario_texto);
                    comentarioTexto.setText(comentarioActual.getDescripcion());
                    layoutComentario.addView(comentarioTexto);

                    LinearLayout contUsuarioFecha = new LinearLayout(verNota.this);
                    contUsuarioFecha.setOrientation(LinearLayout.VERTICAL);

                    View viewComentarioUsuario = inflater.inflate(R.layout.element_nota_label_comentario_usuario, contUsuarioFecha, false);
                    TextView comentarioUsuario = viewComentarioUsuario.findViewById(R.id.id_label_comentario_usuario);
                    comentarioUsuario.setText(comentarioActual.getUsuario().getNombre());
                    contUsuarioFecha.addView(comentarioUsuario);

                    View viewComentarioFecha = inflater.inflate(R.layout.element_nota_label_comentario_fecha, contUsuarioFecha, false);
                    TextView comentarioFecha = viewComentarioFecha.findViewById(R.id.id_label_comentario_fecha);
                    comentarioFecha.setText(comentarioActual.getFecha());
                    contUsuarioFecha.addView(comentarioFecha);

                    layoutComentario.addView(contUsuarioFecha);

                    layoutComentarios.addView(layoutComentario);
                }

                View viewBotonComentarioNuevo = inflater.inflate(R.layout.element_nota_boton_tareaocomentarionuevo, layoutComentarios, false);
                Button botonComentarioNuevo = (Button) viewBotonComentarioNuevo.findViewById(R.id.idElementModuloBotonTareaComentarioNuevo);
                botonComentarioNuevo.setText("+ Comentario nuevo");
                botonComentarioNuevo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(verNota.this, actCrearComentario.class);
                        intent.putExtra("Usuario", (Serializable) usuario);
                        intent.putExtra("Tablero", (Serializable) tablero);
                        intent.putExtra("Nota", (Serializable) nota);
                        intent.putExtra("Modulo", (Serializable) modulo);
                        startActivity(intent);
                    }
                });
                layoutComentarios.addView(botonComentarioNuevo);

                scrollComentarios.addView(layoutComentarios);
            }
        }
    }
}
