package com.dev.danterry.chello;

import java.io.Serializable;

public class EquipoUsuario implements Serializable{
    private int id = 0;
    private int idEquipo = 0;
    private int idUsuario = 0;

    public EquipoUsuario() {
    }

    public EquipoUsuario(int idUsuario, int idEquipo) {
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
    }

    public EquipoUsuario(int id, int idUsuario, int idEquipo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
