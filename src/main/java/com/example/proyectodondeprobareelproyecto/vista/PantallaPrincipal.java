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
import javafx.stage.Stage;

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
        panelIzquierdo.setPrefWidth(230);
        panelIzquierdo.setStyle("-fx-background-color:#0d1b2a; -fx-border-color:#415a77; -fx-border-width:2; -fx-border-radius:10; -fx-background-radius:10;");

        Label lblControles=new Label("Controles");
        lblControles.setTextFill(Color.WHITE);
        lblControles.setFont(Font.font("System",FontWeight.BOLD,15));

        TextField txtOrigen=new TextField();
        txtOrigen.setPromptText("Origen");
        TextField txtDestino=new TextField();
        txtDestino.setPromptText("Destino");
        TextField txtPeso=new TextField();
        txtPeso.setPromptText("Peso");
        Button btnAgregar=crearBoton("Agregar");

        TextField txtElimOrigen=new TextField();
        txtElimOrigen.setPromptText("Origen");
        TextField txtElimDestino=new TextField();
        txtElimDestino.setPromptText("Destino");
        Button btnEliminar=crearBoton("Eliminar");

        TextField txtInicio=new TextField();
        txtInicio.setPromptText("Vértice inicial");
        Button btnBFS=crearBoton("BFS");
        Button btnDFS=crearBoton("DFS");
        Button btnDijkstra=crearBoton("Dijkstra");

        Button btnQuick=crearBoton("QuickSort");
        Button btnShell=crearBoton("ShellSort");
        Button btnReiniciar=crearBoton("Reiniciar");
        Button btnMostrar=crearBoton("Ver grafo");

        panelIzquierdo.getChildren().addAll(
                lblControles,
                new Label("Agregar"),txtOrigen,txtDestino,txtPeso,btnAgregar,
                new Label("Eliminar"),txtElimOrigen,txtElimDestino,btnEliminar,
                new Separator(),
                new Label("Algoritmos"),txtInicio,btnBFS,btnDFS,btnDijkstra,
                new Separator(),
                btnQuick,btnShell,btnReiniciar,btnMostrar
        );

        VBox panelDerecho=new VBox(10);
        panelDerecho.setPadding(new Insets(16));
        panelDerecho.setPrefWidth(200);
        panelDerecho.setStyle("-fx-background-color:#0d1b2a; -fx-border-color:#415a77; -fx-border-width:2; -fx-border-radius:10; -fx-background-radius:10;");

        Label lblInfo=new Label("Información");
        lblInfo.setTextFill(Color.WHITE);
        lblInfo.setFont(Font.font("System",FontWeight.BOLD,14));

        areaInfo=new TextArea();
        areaInfo.setEditable(false);
        areaInfo.setPrefHeight(180);
        areaInfo.setStyle("-fx-control-inner-background:#0d1b2a; -fx-text-fill:#e0e0e0;");

        Label lblExtra=new Label("Representación");
        lblExtra.setTextFill(Color.WHITE);
        lblExtra.setFont(Font.font("System",FontWeight.BOLD,14));

        Button btnLista=crearBoton("Lista adyacencia");
        Button btnMatriz=crearBoton("Matriz adyacencia");

        panelDerecho.getChildren().addAll(lblInfo,areaInfo,lblExtra,btnLista,btnMatriz);

        areaDibujo=new Pane();
        areaDibujo.setStyle("-fx-background-color:#0d1b2a;");
        areaDibujo.setPrefSize(680,320);
        areaDibujo.setMaxSize(680,320);
        areaDibujo.setClip(new Rectangle(680,320));
        areaDibujo.setOnMouseClicked(e->abrirVentanaGrafo(null,null));

        tabla=new TableView<>();
        TableColumn<Conexion,String> colOrigen=new TableColumn<>("Origen");
        colOrigen.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(c.getValue().getOrigen().getNombre()));
        TableColumn<Conexion,String> colDestino=new TableColumn<>("Destino");
        colDestino.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(c.getValue().getDestino().getNombre()));
        TableColumn<Conexion,Integer> colPeso=new TableColumn<>("Peso");
        colPeso.setCellValueFactory(c->new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getPeso()));
        tabla.getColumns().addAll(colOrigen,colDestino,colPeso);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox panelCentral=new VBox(8);
        panelCentral.setPadding(new Insets(8));
        panelCentral.getChildren().addAll(areaDibujo,tabla);
        VBox.setVgrow(tabla,Priority.ALWAYS);

        btnAgregar.setOnAction(e->{
            try{
                String o=txtOrigen.getText().trim();
                String d=txtDestino.getText().trim();
                int p=Integer.parseInt(txtPeso.getText().trim());
                if(!o.isEmpty()&&!d.isEmpty()&&!o.equals(d)&&p>=0){
                    controlador.agregarConexion(o,d,p);
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
            String inicio=txtInicio.getText().trim();
            if(inicio.isEmpty()) return;
            Map<Ubicacion,Integer> d=controlador.calcularDijkstra(inicio);
            if(d!=null){
                StringBuilder sb=new StringBuilder("Dijkstra desde "+inicio+":\n");
                d.forEach((u,val)->sb.append(u.getNombre()).append(": ").append(val==Integer.MAX_VALUE?"∞":val).append("\n"));
                abrirVentanaTexto("Dijkstra",sb.toString());
                abrirVentanaGrafo(null,d);
            }
        });

        btnQuick.setOnAction(e->tabla.getItems().setAll(controlador.ordenarPorPesoQuick()));
        btnShell.setOnAction(e->tabla.getItems().setAll(controlador.ordenarPorPesoShell()));

        btnReiniciar.setOnAction(e->{
            controlador.reiniciar();
            actualizar();
        });

        btnLista.setOnAction(e->abrirVentanaTexto("Lista de adyacencia",controlador.obtenerListaAdyacenciaTexto()));
        btnMatriz.setOnAction(e->abrirVentanaTexto("Matriz de adyacencia",controlador.obtenerMatriz()));

        actualizar();

        this.setTop(barraSuperior);
        this.setLeft(panelIzquierdo);
        this.setRight(panelDerecho);
        this.setCenter(panelCentral);
        this.setStyle("-fx-background-color:#0d1b2a;");
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
        pane.setPrefSize(1600,1000);

        dibujarGrafoEn(pane,1.2,visitados,distancias);

        ScrollPane scroll=new ScrollPane(pane);
        scroll.setPannable(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background:#0d1b2a;");

        Scene scene=new Scene(scroll,1100,750);
        stage.setScene(scene);
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

        Map<String,double[]> pos=new HashMap<>();
        pos.put("Biblioteca",new double[]{160*escala,90*escala});
        pos.put("Laboratorio",new double[]{420*escala,90*escala});
        pos.put("Cafeteria",new double[]{160*escala,240*escala});
        pos.put("Ingenieria",new double[]{420*escala,240*escala});
        pos.put("Rectoria",new double[]{160*escala,390*escala});
        pos.put("Gimnasio",new double[]{420*escala,390*escala});

        int extra=0;
        for(Ubicacion u:grafo.getVertices()){
            double[] p=pos.get(u.getNombre());
            if(p==null){
                int fila=extra/2;
                int col=extra%2;
                p=new double[]{(600+col*140)*escala,(90+fila*140)*escala};
                extra++;
            }

            StackPane nodo=new StackPane();
            nodo.setLayoutX(p[0]-34*escala);
            nodo.setLayoutY(p[1]-34*escala);

            Circle c=new Circle(34*escala);
            c.setFill(visitados!=null&&visitados.contains(u)?Color.web("#e63946"):Color.web("#457b9d"));
            c.setStroke(Color.WHITE);
            c.setStrokeWidth(2.5);

            String texto=u.getNombre();
            if(visitados!=null){
                int orden=visitados.indexOf(u)+1;
                if(orden>0) texto=orden+"\n"+u.getNombre();
            }

            Label lb=new Label(texto);
            lb.setTextFill(Color.WHITE);
            lb.setFont(Font.font("System",FontWeight.BOLD,10*escala));
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
                ln.setStrokeWidth(2);
                pane.getChildren().add(1,ln);

                double mx=(ox+dx)/2;
                double my=(oy+dy)/2;
                Label peso=new Label(String.valueOf(cn.getPeso()));
                peso.setLayoutX(mx-14);
                peso.setLayoutY(my-11);
                peso.setTextFill(Color.WHITE);
                peso.setStyle("-fx-background-color:#0d1b2a; -fx-padding:2 6; -fx-background-radius:4;");
                pane.getChildren().add(peso);
            }
        }
    }
}