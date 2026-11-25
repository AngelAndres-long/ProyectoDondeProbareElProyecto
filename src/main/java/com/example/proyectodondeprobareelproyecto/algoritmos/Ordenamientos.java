package com.example.proyectodondeprobareelproyecto.algoritmos;

import com.example.proyectodondeprobareelproyecto.modelo.Conexion;
import java.util.ArrayList;
import java.util.List;

/**
 * Algoritmos de ordenamiento propios.
 */
public class Ordenamientos{

    public static void quickSortPeso(List<Conexion> lista,int bajo,int alto){
        if(bajo<alto){
            int pi=particionPeso(lista,bajo,alto);
            quickSortPeso(lista,bajo,pi-1);
            quickSortPeso(lista,pi+1,alto);
        }
    }

    private static int particionPeso(List<Conexion> lista,int bajo,int alto){
        int pivote=lista.get(alto).getPeso();
        int i=bajo-1;
        for(int j=bajo;j<alto;j++){
            if(lista.get(j).getPeso()<=pivote){
                i++;
                intercambiar(lista,i,j);
            }
        }
        intercambiar(lista,i+1,alto);
        return i+1;
    }

    public static void shellSortPeso(List<Conexion> lista){
        int n=lista.size();
        for(int gap=n/2;gap>0;gap/=2){
            for(int i=gap;i<n;i++){
                Conexion temp=lista.get(i);
                int j;
                for(j=i;j>=gap&&lista.get(j-gap).getPeso()>temp.getPeso();j-=gap){
                    lista.set(j,lista.get(j-gap));
                }
                lista.set(j,temp);
            }
        }
    }

    private static void intercambiar(List<Conexion> lista,int i,int j){
        Conexion temp=lista.get(i);
        lista.set(i,lista.get(j));
        lista.set(j,temp);
    }

    public static List<Conexion> copiar(List<Conexion> original){
        return new ArrayList<>(original);
    }
}