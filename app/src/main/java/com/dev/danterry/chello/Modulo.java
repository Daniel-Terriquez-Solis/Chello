package com.dev.danterry.chello;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Modulo implements Serializable {
    private int id = 0;
    private int idTablero = 0;
    private String nombre = new String();
    private List<Nota> notas = new ArrayList<Nota>();

    public Modulo() {
    }

    public Modulo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Modulo(int id, int idTablero, String nombre) {
        this.id = id;
        this.idTablero = idTablero;
        this.nombre = nombre;
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


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public boolean anadirNotas(Nota n){
        boolean yaExiste = false;
        for (int i = 0; i<this.notas.size(); i++){
            if(n.getId()==this.notas.get(i).getId()){
                yaExiste = true;
            }
        }
        if (yaExiste){
            return false;
        }else{
            notas.add(n);
            return true;
        }
    }

    public boolean removerNota(Nota n){
        boolean eliminado = false;
        for (int i = 0; i<this.notas.size(); i++){
            if (n.getId()==this.notas.get(i).getId()){
                this.notas.remove(n);
                eliminado = true;
            }
        }
        return eliminado;
    }
}