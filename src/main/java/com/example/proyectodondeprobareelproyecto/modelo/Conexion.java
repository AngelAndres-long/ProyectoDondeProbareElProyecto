package com.example.proyectodondeprobareelproyecto.modelo;

/**
 * Representa una arista con peso entre dos ubicaciones.
 */
public class Conexion{

    private Ubicacion origen;
    private Ubicacion destino;
    private int peso;

    public Conexion(Ubicacion origen,Ubicacion destino,int peso){
        this.origen=origen;
        this.destino=destino;
        this.peso=peso;
    }

    public Ubicacion getOrigen(){
        return origen;
    }

    public Ubicacion getDestino(){
        return destino;
    }

    public int getPeso(){
        return peso;
    }

    @Override
    public String toString(){
        return origen+" -> "+destino+" ("+peso+")";
    }
}