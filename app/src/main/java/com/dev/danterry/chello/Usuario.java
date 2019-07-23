package com.dev.danterry.chello;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {
    private int id;
    private String nombre, email, contrasena, avatar;
    private List<Tablero> tableros;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String email, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
    }

    public Usuario(String nombre, String email, String contrasena) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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