package com.example.proyectodondeprobareelproyecto.modelo;

/**
 * Representa un vértice del grafo.
 */
public class Ubicacion{

    private String nombre;

    public Ubicacion(String nombre){
        this.nombre=nombre;
    }

    public String getNombre(){
        return nombre;
    }

    @Override
    public String toString(){
        return nombre;
    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null||getClass()!=o.getClass()) return false;
        return nombre.equals(((Ubicacion)o).nombre);
    }

    @Override
    public int hashCode(){
        return nombre.hashCode();
    }
}