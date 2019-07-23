package com.dev.danterry.chello;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipo implements Serializable{
    private int id = 0;
    private String nombre = new String(), descripcion = new String();
    private List<Usuario> miembros = new ArrayList<>();
    private List<Tablero> tableros = new ArrayList<>();

    public Equipo() {
    }

    public Equipo(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Usuario> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Usuario> miembros) {
        this.miembros = miembros;
    }

    public boolean anadirMiembro(Usuario u){
        boolean yaExiste = false;
        for (int i = 0; i<this.miembros.size(); i++){
            if(u.getId()==this.miembros.get(i).getId()){
                yaExiste = true;
            }
        }
        if (yaExiste){
            return false;
        }else{
            miembros.add(u);
            return true;
        }
    }

    public boolean removerMiembro(Usuario u){
        boolean eliminado = false;
        for (int i = 0; i<this.miembros.size(); i++){
            if (u.getId()==this.miembros.get(i).getId()){
                this.miembros.remove(u);
                eliminado = true;
            }
        }
        return eliminado;
    }

    public List<Tablero> getTableros() {
        return tableros;
    }

    public void setTableros(List<Tablero> tableros) {
        this.tableros = tableros;
    }

    public boolean anadirTablero(Tablero t){
        boolean yaExiste = false;
        for (int i = 0; i<this.tableros.size(); i++){
            if(t.getId()==this.tableros.get(i).getId()){
                yaExiste = true;
            }
        }
        if (yaExiste){
            return false;
        }else{
            tableros.add(t);
            return true;
        }
    }

    public boolean removerTablero(Tablero t){
        boolean eliminado = false;
        for (int i = 0; i<this.tableros.size(); i++){
            if (t.getId()==this.tableros.get(i).getId()){
                this.tableros.remove(t);
                eliminado = true;
            }
        }
        return eliminado;
    }
}