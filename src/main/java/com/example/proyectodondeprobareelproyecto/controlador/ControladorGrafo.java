package com.example.proyectodondeprobareelproyecto.controlador;

import com.example.proyectodondeprobareelproyecto.algoritmos.*;
import com.example.proyectodondeprobareelproyecto.modelo.*;
import java.util.*;

/**
 * Controlador principal del sistema.
 */
public class ControladorGrafo{

    private Grafo grafo=LectorCSV.cargar("/datos/conexiones.csv");

    public Grafo getGrafo(){
        return grafo;
    }

    public void agregarConexion(String origen,String destino,int peso){
        grafo.agregarConexion(origen,destino,peso);
    }

    public void eliminarConexion(String origen,String destino){
        grafo.eliminarConexion(origen,destino);
    }

    public String obtenerCSV(){
        return grafo.generarCSV();
    }

    public String obtenerListaAdyacenciaTexto(){
        StringBuilder sb=new StringBuilder("Lista de adyacencia:\n\n");
        grafo.obtenerListaAdyacencia().forEach((k,v)->
                sb.append(k).append(" → ").append(v).append("\n")
        );
        return sb.toString();
    }

    public String obtenerMatriz(){
        return grafo.obtenerMatrizAdyacencia();
    }

    public Map<Ubicacion,Integer> calcularDijkstra(String nombre){
        Ubicacion inicio=buscar(nombre);
        return inicio==null?null:Dijkstra.calcular(grafo,inicio);
    }

    public List<Ubicacion> recorrerBFS(String nombre){
        Ubicacion inicio=buscar(nombre);
        return inicio==null?null:BFS.recorrer(grafo,inicio);
    }

    public List<Ubicacion> recorrerDFS(String nombre){
        Ubicacion inicio=buscar(nombre);
        return inicio==null?null:DFS.recorrer(grafo,inicio);
    }

    public List<Conexion> ordenarPorPesoQuick(){
        List<Conexion> copia=Ordenamientos.copiar(grafo.getConexiones());
        Ordenamientos.quickSortPeso(copia,0,copia.size()-1);
        return copia;
    }

    public List<Conexion> ordenarPorPesoShell(){
        List<Conexion> copia=Ordenamientos.copiar(grafo.getConexiones());
        Ordenamientos.shellSortPeso(copia);
        return copia;
    }

    private Ubicacion buscar(String nombre){
        for(Ubicacion u:grafo.getVertices()){
            if(u.getNombre().equals(nombre)) return u;
        }
        return null;
    }

    public void reiniciar(){
        grafo=LectorCSV.cargar("/datos/conexiones.csv");
    }
}