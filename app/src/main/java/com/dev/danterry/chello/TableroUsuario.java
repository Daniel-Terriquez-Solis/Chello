package com.dev.danterry.chello;

import java.io.Serializable;

public class TableroUsuario implements Serializable{
    private int id = 0;
    private int idTablero = 0;
    private int idUsuario = 0;

    public TableroUsuario() {
    }

    public TableroUsuario(int idUsuario, int idTablero) {
        this.idUsuario = idUsuario;
        this.idTablero = idTablero;
    }

    public TableroUsuario(int id, int idUsuario, int idTablero) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idTablero = idTablero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTablero() {
        return idTablero;
    }

    public void setIdTablero(int idTablero) {
        this.idTablero = idTablero;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
