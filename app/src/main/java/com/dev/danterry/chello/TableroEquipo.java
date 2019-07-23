package com.dev.danterry.chello;

import java.io.Serializable;

public class TableroEquipo implements Serializable{
    private int id = 0;
    private int idTablero = 0;
    private int idEquipo = 0;

    public TableroEquipo() {
    }

    public TableroEquipo(int idEquipo, int idTablero) {
        this.idEquipo = idEquipo;
        this.idTablero = idTablero;
    }

    public TableroEquipo(int id, int idEquipo, int idTablero) {
        this.id = id;
        this.idEquipo = idEquipo;
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

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
}
