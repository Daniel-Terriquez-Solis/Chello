package com.dev.danterry.chello;

import java.io.Serializable;

public class ActividadUsuario implements Serializable{
    private int id = 0;
    private int idActividad = 0;
    private int idUsuario = 0;

    public ActividadUsuario() {
    }

    public ActividadUsuario(int idUsuario, int idActividad) {
        this.idUsuario = idUsuario;
        this.idActividad = idActividad;
    }

    public ActividadUsuario(int id, int idUsuario, int idActividad) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idActividad = idActividad;
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
