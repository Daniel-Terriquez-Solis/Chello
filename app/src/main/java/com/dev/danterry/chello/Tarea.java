package com.dev.danterry.chello;

import java.io.Serializable;
import java.util.Date;

public class Tarea implements Serializable {
    private int id = 0;
    private int idUsuario = 0;
    private int idActividad = 0;
    private String descripcion = new String();
    private String vencimiento = new Date().toString();
    private boolean estado = false;

    public Tarea() {
    }

    public Tarea(int idUsuario, int idActividad, String descripcion, String vencimiento, boolean estado) {
        this.idUsuario = idUsuario;
        this.idActividad = idActividad;
        this.descripcion = descripcion;
        this.vencimiento = vencimiento;
        this.estado = estado;
    }

    public Tarea(int id, int idUsuario, int idActividad, String descripcion, String vencimiento, boolean estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idActividad = idActividad;
        this.descripcion = descripcion;
        this.vencimiento = vencimiento;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}