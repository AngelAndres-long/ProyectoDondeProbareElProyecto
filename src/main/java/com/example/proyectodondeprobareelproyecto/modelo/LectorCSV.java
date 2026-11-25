package com.example.proyectodondeprobareelproyecto.modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Lee el archivo CSV y construye el grafo.
 */
public class LectorCSV{

    public static Grafo cargar(String ruta){
        Grafo grafo=new Grafo();
        try(BufferedReader br=new BufferedReader(new InputStreamReader(
                LectorCSV.class.getResourceAsStream(ruta)))){
            br.readLine();//Saltar encabezado
            String linea;
            while((linea=br.readLine())!=null){
                String[] p=linea.split(",");
                if(p.length==3){
                    try{
                        grafo.agregarConexion(p[0].trim(),p[1].trim(),Integer.parseInt(p[2].trim()));
                    }catch(Exception ignored){}
                }
            }
        }catch(Exception e){
            System.err.println("Error: "+e.getMessage());
        }
        return grafo;
    }
}