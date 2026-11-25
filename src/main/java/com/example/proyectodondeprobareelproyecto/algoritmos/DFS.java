package com.example.proyectodondeprobareelproyecto.algoritmos;

import com.example.proyectodondeprobareelproyecto.modelo.*;
import java.util.*;

/**
 * Recorrido en profundidad (DFS).
 */
public class DFS{

    public static List<Ubicacion> recorrer(Grafo grafo,Ubicacion inicio){
        List<Ubicacion> res=new ArrayList<>();
        Set<Ubicacion> vis=new HashSet<>();
        dfs(grafo,inicio,vis,res);
        return res;
    }

    private static void dfs(Grafo g,Ubicacion a,Set<Ubicacion> v,List<Ubicacion> r){
        v.add(a);
        r.add(a);
        for(Conexion c:g.getConexiones()){
            if(c.getOrigen().equals(a)&&!v.contains(c.getDestino())){
                dfs(g,c.getDestino(),v,r);
            }
        }
    }
}