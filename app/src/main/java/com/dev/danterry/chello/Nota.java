package com.dev.danterry.chello;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Nota implements Serializable {
    private int id = 0;
    private int idModulo = 0;
    private String titulo = new String();
    private String descripcion = new String();
    private String vencimiento = new String();
    private boolean completado = false;
    private List<Tarea> tareas = new ArrayList<>();
    private List<Comentario> comentarios = new ArrayList<>();

    public Nota() {
    }

    public Nota(int id, String titulo, String descripcion, String vencimiento, boolean completado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.vencimiento = vencimiento;
        this.completado = completado;
    }

    public Nota(int id, int idModulo, String titulo, String descripcion, String vencimiento, boolean completado) {
        this.id = id;
        this.idModulo = idModulo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.vencimiento = vencimiento;
        this.completado = completado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String  getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String  vencimiento) {
        this.vencimiento = vencimiento;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public boolean anadirTarea(Tarea t){
        boolean yaExiste = false;
        for (int i = 0; i<this.tareas.size(); i++){
            if(t.getId()==this.tareas.get(i).getId()){
                yaExiste = true;
            }
        }
        if (yaExiste){
            return false;
        }else{
            tareas.add(t);
            return true;
        }
    }

    public boolean removerTarea(Tarea t){
        boolean eliminado = false;
        for (int i = 0; i<this.tareas.size(); i++){
            if (t.getId()==this.tareas.get(i).getId()){
                this.tareas.remove(t);
                eliminado = true;
            }
        }
        return eliminado;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public boolean anadirComentario(Comentario c){
        boolean yaExiste = false;
        for (int i = 0; i<this.comentarios.size(); i++){
            if(c.getId()==this.comentarios.get(i).getId()){
                yaExiste = true;
            }
        }
        if (yaExiste){
            return false;
        }else{
            comentarios.add(c);
            return true;
        }
    }

    public boolean removerComentario(Comentario c){
        boolean eliminado = false;
        for (int i = 0; i<this.comentarios.size(); i++){
            if (c.getId()==this.comentarios.get(i).getId()){
                this.tareas.remove(c);
                eliminado = true;
            }
        }
        return eliminado;
    }
}