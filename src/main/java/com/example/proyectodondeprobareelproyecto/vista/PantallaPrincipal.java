package com.example.proyectodondeprobareelproyecto.vista;

import com.example.proyectodondeprobareelproyecto.controlador.ControladorGrafo;
import com.example.proyectodondeprobareelproyecto.modelo.Conexion;
import com.example.proyectodondeprobareelproyecto.modelo.Ubicacion;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pantalla principal del sistema de rutas.
 */
public class PantallaPrincipal extends BorderPane{

    private ControladorGrafo controlador;
    private TextArea areaInfo;
    private TableView<Conexion> tabla;
    private Pane areaDibujo;
    private MediaPlayer mediaPlayer;
    private boolean silenciado=false;

    public PantallaPrincipal(){
        controlador=new ControladorGrafo();

        try{
            Media media=new Media(getClass().getResource("/musica/alex.mp3").toExternalForm());
            mediaPlayer=new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.35);
            mediaPlayer.play();
        }catch(Exception e){}

        HBox barraSuperior=new HBox();
        barraSuperior.setPadding(new Insets(14,22,14,22));
        barraSuperior.setAlignment(Pos.CENTER);
        barraSuperior.setStyle("-fx-background-color:#0d1b2a;");

        Label titulo=new Label("Sistema de Rutas");
        titulo.setFont(Font.font("System",FontWeight.BOLD,22));
        titulo.setTextFill(Color.WHITE);

        Button btnSilenciar=new Button("🔊");
        btnSilenciar.setStyle("-fx-background-color:#1b263b; -fx-text-fill:white; -fx-font-size:15; -fx-padding:6 12; -fx-background-radius:8;");
        btnSilenciar.setOnAction(e->{
            if(mediaPlayer!=null){
                if(silenciado){
                    mediaPlayer.play();
                    btnSilenciar.setText("🔊");
                }else{
                    mediaPlayer.pause();
                    btnSilenciar.setText("🔇");
                }
                silenciado=!silenciado;
            }
        });

        Region izq=new Region();
        Region der=new Region();
        HBox.setHgrow(izq,Priority.ALWAYS);
        HBox.setHgrow(der,Priority.ALWAYS);
        barraSuperior.getChildren().addAll(izq,titulo,der,btnSilenciar);

        VBox panelIzquierdo=new VBox(8);
        panelIzquierdo.setPadding(new Insets(16));
        panelIzquierdo.setStyle("-fx-background-color:#0d1b2a;");

        Label lblControles=new Label("Controles");
        lblControles.setTextFill(Color.WHITE);
        lblControles.setFont(Font.font("System",FontWeight.BOLD,15));

        Button btnCargarCSV=crearBoton("Importar CSV");
        Button btnGuardar=crearBoton("Guardar cambios");
        Button btnGuardarComo=crearBoton("Guardar como");
        Button btnNuevo=crearBoton("Nuevo grafo");

        TextField txtOrigen=new TextField();
        txtOrigen.setPromptText("Origen");
        TextField txtDestino=new TextField();
        txtDestino.setPromptText("Destino");
        TextField txtPeso=new TextField();
        txtPeso.setPromptText("Peso");
        CheckBox chkNoDirigida=new CheckBox("No dirigida");
        chkNoDirigida.setTextFill(Color.WHITE);
        Button btnAgregar=crearBoton("Agregar");

        TextField txtElimOrigen=new TextField();
        txtElimOrigen.setPromptText("Origen");
        TextField txtElimDestino=new TextField();
        txtElimDestino.setPromptText("Destino");
        Button btnEliminar=crearBoton("Eliminar");

        TextField txtModOrigen=new TextField();
        txtModOrigen.setPromptText("Origen");
        TextField txtModDestino=new TextField();
        txtModDestino.setPromptText("Destino");
        TextField txtModPeso=new TextField();
        txtModPeso.setPromptText("Nuevo peso");
        Button btnModificarPeso=crearBoton("Modificar peso");

        TextField txtNombreActual=new TextField();
        txtNombreActual.setPromptText("Nombre actual");
        TextField txtNombreNuevo=new TextField();
        txtNombreNuevo.setPromptText("Nuevo nombre");
        Button btnModificarNombre=crearBoton("Modificar nombre");

        TextField txtInicio=new TextField();
        txtInicio.setPromptText("Origen");
        TextField txtDestinoDijkstra=new TextField();
        txtDestinoDijkstra.setPromptText("Destino");
        Button btnBFS=crearBoton("BFS");
        Button btnDFS=crearBoton("DFS");
        Button btnDijkstra=crearBoton("Dijkstra");

        Button btnQuick=crearBoton("QuickSort");
        Button btnShell=crearBoton("ShellSort");
        Button btnNombre=crearBoton("Ordenar nombre");
        Button btnTiempos=crearBoton("Medir tiempos");
        Button btnMostrar=crearBoton("Ver grafo");

        panelIzquierdo.getChildren().addAll(
                lblControles,
                btnCargarCSV,btnGuardar,btnGuardarComo,btnNuevo,
                new Label("Agregar"),txtOrigen,txtDestino,txtPeso,chkNoDirigida,btnAgregar,
                new Label("Eliminar"),txtElimOrigen,txtElimDestino,btnEliminar,
                new Label("Modificar peso"),txtModOrigen,txtModDestino,txtModPeso,btnModificarPeso,
                new Label("Modificar nombre"),txtNombreActual,txtNombreNuevo,btnModificarNombre,
                new Separator(),
                new Label("Algoritmos"),txtInicio,txtDestinoDijkstra,btnBFS,btnDFS,btnDijkstra,
                new Separator(),
                btnQuick,btnShell,btnNombre,btnTiempos,btnMostrar
        );

        ScrollPane scrollIzquierdo=new ScrollPane(panelIzquierdo);
        scrollIzquierdo.setFitToWidth(true);
        scrollIzquierdo.setPrefWidth(250);
        scrollIzquierdo.setStyle("-fx-background:#0d1b2a; -fx-border-color:#415a77; -fx-border-width:2; -fx-border-radius:10;");
        scrollIzquierdo.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox panelDerecho=new VBox(10);
        panelDerecho.setPadding(new Insets(16));
        panelDerecho.setPrefWidth(200);
        panelDerecho.setStyle("-fx-background-color:#0d1b2a; -fx-border-color:#415a77; -fx-border-width:2; -fx-border-radius:10; -fx-background-radius:10;");

        Label lblInfo=new Label("Información");
        lblInfo.setTextFill(Color.WHITE);
        lblInfo.setFont(Font.font("System",FontWeight.BOLD,14));

        areaInfo=new TextArea();
        areaInfo.setEditable(false);
        areaInfo.setStyle("-fx-control-inner-background:#0d1b2a; -fx-text-fill:#e0e0e0;");

        ScrollPane scrollInfo=new ScrollPane(areaInfo);
        scrollInfo.setFitToWidth(true);
        scrollInfo.setPrefHeight(180);
        scrollInfo.setStyle("-fx-background:#0d1b2a;");

        Label lblExtra=new Label("Representación");
        lblExtra.setTextFill(Color.WHITE);
        lblExtra.setFont(Font.font("System",FontWeight.BOLD,14));

        Button btnLista=crearBoton("Lista adyacencia");
        Button btnMatriz=crearBoton("Matriz adyacencia");

        panelDerecho.getChildren().addAll(lblInfo,scrollInfo,lblExtra,btnLista,btnMatriz);

        areaDibujo=new Pane();
        areaDibujo.setStyle("-fx-background-color:#0d1b2a;");
        areaDibujo.setPrefHeight(320);
        areaDibujo.setMinHeight(280);
        areaDibujo.setMaxHeight(320);
        areaDibujo.setOnMouseClicked(e->abrirVentanaGrafo(null,null));

        Rectangle clip=new Rectangle();
        clip.widthProperty().bind(areaDibujo.widthProperty());
        clip.heightProperty().bind(areaDibujo.heightProperty());
        areaDibujo.setClip(clip);

        tabla=new TableView<>();
        TableColumn<Conexion,String> colOrigen=new TableColumn<>("Origen");
        colOrigen.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(c.getValue().getOrigen().getNombre()));
        TableColumn<Conexion,String> colDestino=new TableColumn<>("Destino");
        colDestino.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(c.getValue().getDestino().getNombre()));
        TableColumn<Conexion,Integer> colPeso=new TableColumn<>("Peso");
        colPeso.setCellValueFactory(c->new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getPeso()));
        tabla.getColumns().addAll(colOrigen,colDestino,colPeso);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox panelCentral=new VBox(6);
        panelCentral.setPadding(new Insets(6));
        panelCentral.getChildren().addAll(areaDibujo,tabla);
        VBox.setVgrow(tabla,Priority.ALWAYS);

        btnCargarCSV.setOnAction(e->{
            FileChooser fc=new FileChooser();
            fc.setTitle("Seleccionar CSV");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));
            File archivo=fc.showOpenDialog(null);
            if(archivo!=null){
                controlador.cargarDesdeArchivo(archivo);
                actualizar();
            }
        });

        btnGuardar.setOnAction(e->{
            controlador.guardarCambios();
            abrirVentanaTexto("Guardado","Cambios guardados");
        });

        btnGuardarComo.setOnAction(e->{
            FileChooser fc=new FileChooser();
            fc.setTitle("Guardar CSV");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));
            fc.setInitialFileName("conexiones.csv");
            File archivo=fc.showSaveDialog(null);
            if(archivo!=null){
                controlador.guardarComo(archivo);
                abrirVentanaTexto("Guardado","Archivo guardado en:\n"+archivo.getAbsolutePath());
            }
        });

        btnNuevo.setOnAction(e->{
            controlador.nuevoGrafo();
            actualizar();
        });

        btnAgregar.setOnAction(e->{
            try{
                String o=txtOrigen.getText().trim();
                String d=txtDestino.getText().trim();
                int p=Integer.parseInt(txtPeso.getText().trim());
                if(!o.isEmpty()&&!d.isEmpty()&&!o.equals(d)&&p>=0){
                    if(chkNoDirigida.isSelected()){
                        controlador.agregarConexionNoDirigida(o,d,p);
                    }else{
                        controlador.agregarConexion(o,d,p);
                    }
                    actualizar();
                    txtOrigen.clear();
                    txtDestino.clear();
                    txtPeso.clear();
                }
            }catch(Exception ex){}
        });

        btnEliminar.setOnAction(e->{
            String o=txtElimOrigen.getText().trim();
            String d=txtElimDestino.getText().trim();
            if(!o.isEmpty()&&!d.isEmpty()){
                controlador.eliminarConexion(o,d);
                actualizar();
                txtElimOrigen.clear();
                txtElimDestino.clear();
            }
        });

        btnModificarPeso.setOnAction(e->{
            try{
                String o=txtModOrigen.getText().trim();
                String d=txtModDestino.getText().trim();
                int p=Integer.parseInt(txtModPeso.getText().trim());
                if(!o.isEmpty()&&!d.isEmpty()&&p>=0){
                    controlador.modificarPeso(o,d,p);
                    actualizar();
                    txtModOrigen.clear();
                    txtModDestino.clear();
                    txtModPeso.clear();
                }
            }catch(Exception ex){}
        });

        btnModificarNombre.setOnAction(e->{
            String actual=txtNombreActual.getText().trim();
            String nuevo=txtNombreNuevo.getText().trim();
            if(!actual.isEmpty()&&!nuevo.isEmpty()){
                controlador.modificarNombre(actual,nuevo);
                actualizar();
                txtNombreActual.clear();
                txtNombreNuevo.clear();
            }
        });

        btnMostrar.setOnAction(e->abrirVentanaGrafo(null,null));

        btnBFS.setOnAction(e->{
            String inicio=txtInicio.getText().trim();
            if(inicio.isEmpty()) return;
            List<Ubicacion> r=controlador.recorrerBFS(inicio);
            if(r!=null){
                abrirVentanaTexto("BFS","BFS desde "+inicio+":\n"+r);
                abrirVentanaGrafo(r,null);
            }
        });

        btnDFS.setOnAction(e->{
            String inicio=txtInicio.getText().trim();
            if(inicio.isEmpty()) return;
            List<Ubicacion> r=controlador.recorrerDFS(inicio);
            if(r!=null){
                abrirVentanaTexto("DFS","DFS desde "+inicio+":\n"+r);
                abrirVentanaGrafo(r,null);
            }
        });

        btnDijkstra.setOnAction(e->{
            String origen=txtInicio.getText().trim();
            String destino=txtDestinoDijkstra.getText().trim();
            if(origen.isEmpty()||destino.isEmpty()) return;
            String resultado=controlador.obtenerRutaDijkstra(origen,destino);
            abrirVentanaTexto("Dijkstra",resultado);
        });

        btnQuick.setOnAction(e->tabla.getItems().setAll(controlador.ordenarPorPesoQuick()));
        btnShell.setOnAction(e->tabla.getItems().setAll(controlador.ordenarPorPesoShell()));
        btnNombre.setOnAction(e->tabla.getItems().setAll(controlador.ordenarPorNombreQuick()));

        btnTiempos.setOnAction(e->{
            StringBuilder sb=new StringBuilder("Tiempos de ejecución:\n\n");
            long t1,t2;
            String primerVertice="";
            if(!controlador.getGrafo().getVertices().isEmpty()){
                primerVertice=controlador.getGrafo().getVertices().iterator().next().getNombre();
            }

            t1=System.nanoTime();
            controlador.recorrerBFS(primerVertice);
            t2=System.nanoTime();
            sb.append("BFS: ").append(String.format("%.3f",(t2-t1)/1_000_000.0)).append(" ms\n");

            t1=System.nanoTime();
            controlador.recorrerDFS(primerVertice);
            t2=System.nanoTime();
            sb.append("DFS: ").append(String.format("%.3f",(t2-t1)/1_000_000.0)).append(" ms\n");

            t1=System.nanoTime();
            controlador.calcularDijkstra(primerVertice);
            t2=System.nanoTime();
            sb.append("Dijkstra: ").append(String.format("%.3f",(t2-t1)/1_000_000.0)).append(" ms\n");

            t1=System.nanoTime();
            controlador.ordenarPorPesoQuick();
            t2=System.nanoTime();
            sb.append("QuickSort: ").append(String.format("%.3f",(t2-t1)/1_000_000.0)).append(" ms\n");

            t1=System.nanoTime();
            controlador.ordenarPorPesoShell();
            t2=System.nanoTime();
            sb.append("ShellSort: ").append(String.format("%.3f",(t2-t1)/1_000_000.0)).append(" ms\n");

            abrirVentanaTexto("Tiempos",sb.toString());
        });

        btnLista.setOnAction(e->abrirVentanaTexto("Lista de adyacencia",controlador.obtenerListaAdyacenciaTexto()));
        btnMatriz.setOnAction(e->abrirVentanaTexto("Matriz de adyacencia",controlador.obtenerMatriz()));

        actualizar();

        this.setTop(barraSuperior);
        this.setLeft(scrollIzquierdo);
        this.setRight(panelDerecho);
        this.setCenter(panelCentral);
        this.setStyle("-fx-background-color:#0d1b2a;");
    }

    public void guardarAlSalir(){
        controlador.guardarAlSalir();
    }

    private Button crearBoton(String texto){
        Button btn=new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color:#415a77; -fx-text-fill:white; -fx-padding:8; -fx-background-radius:6;");
        return btn;
    }

    private void actualizar(){
        var g=controlador.getGrafo();
        StringBuilder sb=new StringBuilder();
        sb.append("Vértices: ").append(g.getNumeroVertices()).append("\n");
        sb.append("Aristas: ").append(g.getNumeroAristas()).append("\n\n");
        for(Ubicacion u:g.getVertices()){
            sb.append("• ").append(u.getNombre()).append("\n");
        }
        areaInfo.setText(sb.toString());
        tabla.getItems().setAll(g.getConexiones());
        dibujarGrafoEn(areaDibujo,1.0,null,null);
    }

    private void abrirVentanaGrafo(List<Ubicacion> visitados,Map<Ubicacion,Integer> distancias){
        Stage stage=new Stage();
        stage.setTitle(visitados!=null?"Recorrido":"Grafo");
        Pane pane=new Pane();
        pane.setStyle("-fx-background-color:#0d1b2a;");
        pane.setPrefSize(2500,1800);
        dibujarGrafoEn(pane,1.2,visitados,distancias);
        ScrollPane scroll=new ScrollPane(pane);
        scroll.setPannable(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        stage.setScene(new Scene(scroll,1100,750));
        stage.show();
    }

    private void abrirVentanaTexto(String titulo,String contenido){
        Stage stage=new Stage();
        stage.setTitle(titulo);
        TextArea area=new TextArea(contenido);
        area.setEditable(false);
        area.setStyle("-fx-control-inner-background:#0d1b2a; -fx-text-fill:#e0e0e0; -fx-font-family:monospace;");
        stage.setScene(new Scene(area,620,480));
        stage.show();
    }

    private void dibujarGrafoEn(Pane pane,double escala,List<Ubicacion> visitados,Map<Ubicacion,Integer> distancias){
        pane.getChildren().clear();
        try{
            Image fondo=new Image(getClass().getResource("/background/fondo.gif").toExternalForm());
            ImageView bg=new ImageView(fondo);
            bg.fitWidthProperty().bind(pane.widthProperty());
            bg.fitHeightProperty().bind(pane.heightProperty());
            bg.setPreserveRatio(false);
            pane.getChildren().add(bg);
        }catch(Exception e){}

        var grafo=controlador.getGrafo();
        Map<String,Circle> nodos=new HashMap<>();

        Map<String,double[]> posFijas=new HashMap<>();
        posFijas.put("Biblioteca",new double[]{200*escala,120*escala});
        posFijas.put("Laboratorio",new double[]{500*escala,120*escala});
        posFijas.put("Cafeteria",new double[]{200*escala,320*escala});
        posFijas.put("Ingenieria",new double[]{500*escala,320*escala});
        posFijas.put("Rectoria",new double[]{200*escala,520*escala});
        posFijas.put("Gimnasio",new double[]{500*escala,520*escala});

        List<Ubicacion> lista=new ArrayList<>(grafo.getVertices());
        int total=lista.size();
        int limite=Math.min(total,50);

        int extra=0;
        int columnas=5;
        double espaciadoX=250*escala;
        double espaciadoY=200*escala;

        for(int i=0;i<limite;i++){
            Ubicacion u=lista.get(i);
            double[] p=posFijas.get(u.getNombre());
            if(p==null){
                int fila=extra/columnas;
                int col=extra%columnas;
                p=new double[]{(180+col*espaciadoX),(150+fila*espaciadoY)};
                extra++;
            }

            StackPane nodo=new StackPane();
            nodo.setLayoutX(p[0]-28*escala);
            nodo.setLayoutY(p[1]-28*escala);
            Circle c=new Circle(28*escala);
            c.setFill(visitados!=null&&visitados.contains(u)?Color.web("#e63946"):Color.web("#457b9d"));
            c.setStroke(Color.WHITE);
            c.setStrokeWidth(2.5);

            String texto=u.getNombre();
            if(texto.length()>8) texto=texto.substring(0,6)+"..";
            if(visitados!=null){
                int orden=visitados.indexOf(u)+1;
                if(orden>0) texto=orden+"\n"+texto;
            }
            Label lb=new Label(texto);
            lb.setTextFill(Color.WHITE);
            lb.setFont(Font.font("System",FontWeight.BOLD,8*escala));
            lb.setAlignment(Pos.CENTER);
            lb.setTextAlignment(TextAlignment.CENTER);
            nodo.getChildren().addAll(c,lb);
            pane.getChildren().add(nodo);
            nodos.put(u.getNombre(),c);
        }

        for(Conexion cn:grafo.getConexiones()){
            Circle o=nodos.get(cn.getOrigen().getNombre());
            Circle d=nodos.get(cn.getDestino().getNombre());
            if(o!=null&&d!=null){
                StackPane padreO=(StackPane)o.getParent();
                StackPane padreD=(StackPane)d.getParent();
                double ox=padreO.getLayoutX()+o.getRadius();
                double oy=padreO.getLayoutY()+o.getRadius();
                double dx=padreD.getLayoutX()+d.getRadius();
                double dy=padreD.getLayoutY()+d.getRadius();
                Line ln=new Line(ox,oy,dx,dy);
                ln.setStroke(Color.web("#cccccc"));
                ln.setStrokeWidth(1.5);
                pane.getChildren().add(1,ln);

                double mx=(ox+dx)/2;
                double my=(oy+dy)/2;
                double angulo=Math.atan2(dy-oy,dx-ox);
                double offsetX=Math.sin(angulo)*18;
                double offsetY=-Math.cos(angulo)*18;

                Label peso=new Label(String.valueOf(cn.getPeso()));
                peso.setLayoutX(mx+offsetX-12);
                peso.setLayoutY(my+offsetY-6);
                peso.setTextFill(Color.WHITE);
                peso.setStyle("-fx-background-color:#0d1b2a; -fx-padding:1 4; -fx-background-radius:3; -fx-font-size:9;");
                pane.getChildren().add(peso);
            }
        }
    }
}