package com.dev.danterry.chello;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Tablero implements Serializable {
    private int id = 0;
    private String titulo = new String();
    private List<Modulo> modulos = new ArrayList<>();

    public Tablero() {
    }

    public Tablero(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public boolean anadirModulo(Modulo m){
        boolean yaExiste = false;
        for (int i = 0; i<this.modulos.size(); i++){
            if(m.getId()==this.modulos.get(i).getId()){
                yaExiste = true;
            }
        }
        if (yaExiste){
            return false;
        }else{
            modulos.add(m);
            return true;
        }
    }

    public boolean removerModulo(Modulo m){
        boolean eliminado = false;
        for (int i = 0; i<this.modulos.size(); i++){
            if (m.getId()==this.modulos.get(i).getId()){
                this.modulos.remove(m);
                eliminado = true;
            }
        }
        return eliminado;
    }
}