package com.dev.danterry.chello;

import java.io.Serializable;
import java.util.Date;

public class Comentario implements Serializable {
    private int id = 0;
    private int idActividad = 0;
    private String descripcion = new String();
    private String fecha = new String();
    private Usuario usuario = new Usuario();

    public Comentario() {
    }

    public Comentario(int id, String descripcion, String fecha, Usuario usuario) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public Comentario(int id, int idActividad, String descripcion, String fecha, Usuario usuario) {
        this.id = id;
        this.idActividad = idActividad;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}