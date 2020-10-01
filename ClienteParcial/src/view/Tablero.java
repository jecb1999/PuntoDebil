package view;

import control.TableroController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tablero extends Stage{
	
	private Button uno;
	private Button dos;
	private Button tres;
	private Button cuatro;
	private Button cinco;
	private Button seis;
	private Button siete;
	private Button ocho;
	private Button nueve;
	private Text turno;
	private Scene scene;
	private AnchorPane tab;
	private TableroController control;
	
	public Tablero() {
		tab = new AnchorPane();
		tab.setPrefHeight(433);
		tab.setPrefWidth(361);
		uno = new Button();
		uno.setLayoutX(64);
		uno.setLayoutY(113);
		uno.setPrefHeight(54);
		uno.setPrefWidth(52);
		uno.setId("1");
		dos = new Button();
		dos.setLayoutX(155);
		dos.setLayoutY(113);
		dos.setPrefHeight(54);
		dos.setPrefWidth(52);
		dos.setId("2");
		tres = new Button();
		tres.setLayoutX(244);
		tres.setLayoutY(113);
		tres.setPrefHeight(54);
		tres.setPrefWidth(52);
		tres.setId("3");
		cuatro = new Button();
		cuatro.setLayoutX(64);
		cuatro.setLayoutY(208);
		cuatro.setPrefHeight(54);
		cuatro.setPrefWidth(52);
		cuatro.setId("4");
		cinco = new Button();
		cinco.setLayoutX(155);
		cinco.setLayoutY(208);
		cinco.setPrefHeight(54);
		cinco.setPrefWidth(52);
		cinco.setId("5");
		seis = new Button();
		seis.setLayoutX(244);
		seis.setLayoutY(208);
		seis.setPrefHeight(54);
		seis.setPrefWidth(52);
		seis.setId("6");
		siete = new Button();
		siete.setLayoutX(64);
		siete.setLayoutY(304);
		siete.setPrefHeight(54);
		siete.setPrefWidth(52);
		siete.setId("7");
		ocho = new Button();
		ocho.setLayoutX(155);
		ocho.setLayoutY(304);
		ocho.setPrefHeight(54);
		ocho.setPrefWidth(52);
		ocho.setId("8");
		nueve = new Button();
		nueve.setLayoutX(244);
		nueve.setLayoutY(304);
		nueve.setPrefHeight(54);
		nueve.setPrefWidth(52);
		nueve.setId("9");
		turno = new Text();
		turno.setLayoutX(155);
		turno.setLayoutY(46);
		turno.setWrappingWidth(52.99999585747719);
		turno.setId("turno");
		turno.setText("Esperando contrincante");
		tab.getChildren().add(uno);
		tab.getChildren().add(dos);
		tab.getChildren().add(tres);
		tab.getChildren().add(cuatro);
		tab.getChildren().add(cinco);
		tab.getChildren().add(seis);
		tab.getChildren().add(siete);
		tab.getChildren().add(ocho);
		tab.getChildren().add(nueve);
		tab.getChildren().add(turno);
		scene = new Scene(tab);
		this.setScene(scene);
		control = new TableroController(this);
	}
	
	public Text getTurno() {
		return turno;
	}
	
	public AnchorPane getTable() {
		return tab;
	}

	public Button getUno() {
		return uno;
	}

	public Button getDos() {
		return dos;
	}

	public Button getTres() {
		return tres;
	}

	public Button getCuatro() {
		return cuatro;
	}

	public Button getCinco() {
		return cinco;
	}

	public Button getSeis() {
		return seis;
	}

	public Button getSiete() {
		return siete;
	}

	public Button getOcho() {
		return ocho;
	}

	public Button getNueve() {
		return nueve;
	}

	public AnchorPane getTab() {
		return tab;
	}

	public TableroController getControl() {
		return control;
	}
	
	
	

}
