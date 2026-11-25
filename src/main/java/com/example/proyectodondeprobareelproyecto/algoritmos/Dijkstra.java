package com.example.proyectodondeprobareelproyecto.algoritmos;

import com.example.proyectodondeprobareelproyecto.modelo.*;
import java.util.*;

/**
 * Algoritmo de Dijkstra para encontrar la ruta de menor costo.
 */
public class Dijkstra{

    public static Map<Ubicacion,Integer> calcular(Grafo grafo,Ubicacion inicio){
        Map<Ubicacion,Integer> dist=new HashMap<>();
        for(Ubicacion u:grafo.getVertices()) dist.put(u,Integer.MAX_VALUE);
        dist.put(inicio,0);
        PriorityQueue<Ubicacion> pq=new PriorityQueue<>(Comparator.comparingInt(dist::get));
        pq.add(inicio);
        while(!pq.isEmpty()){
            Ubicacion actual=pq.poll();
            for(Conexion c:grafo.getConexiones()){
                if(c.getOrigen().equals(actual)){
                    int nd=dist.get(actual)+c.getPeso();
                    if(nd<dist.get(c.getDestino())){
                        dist.put(c.getDestino(),nd);
                        pq.add(c.getDestino());
                    }
                }
            }
        }
        return dist;
    }
}