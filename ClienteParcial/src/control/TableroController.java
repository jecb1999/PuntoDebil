package control;

import comm.Receptor.OnMessageListener;
import model.Generic;

import model.SendNumberMessage;
import model.TurnoMessage;

import java.util.ArrayList;

import javax.swing.JOptionPane;



import com.google.gson.Gson;

import comm.TCPConnection;
import javafx.application.Platform;
import javafx.scene.control.Button;
import view.Tablero;

public class TableroController implements OnMessageListener {

	private Tablero view;
	private TCPConnection connection;
	private boolean turno;
	private ArrayList<Button> listButton;

	public TableroController(Tablero view) {
		this.view = view;
		listButton = new ArrayList<Button>();
		listButton.add(view.getUno());
		listButton.add(view.getDos());
		listButton.add(view.getTres());
		listButton.add(view.getCuatro());
		listButton.add(view.getCinco());
		listButton.add(view.getSeis());
		listButton.add(view.getSiete());
		listButton.add(view.getOcho());
		listButton.add(view.getNueve());
		this.turno = false;

		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);

		listButton.forEach(n -> {
			n.setOnAction(e -> {
				if (turno == true) {
					String snumber = n.getId();
					int number = Integer.parseInt(snumber);
					SendNumberMessage sm = new SendNumberMessage(number);
					n.setDisable(true);
					Gson gson = new Gson();
					String msg = gson.toJson(sm);
					connection.getEmisor().sendMessage(msg);
					System.out.println(n.getId());
				} else {
					System.out.println("no es tu turno");
				}
			});

		});
	}

	@Override
	public void OnMessage(String msg) {
		Gson gson = new Gson();
		Generic type = gson.fromJson(msg, Generic.class);
		switch (type.getType()) {
		case "en proceso":
			JOptionPane.showMessageDialog(null, "Partida en proceso", "Error", JOptionPane.ERROR_MESSAGE);
			Platform.runLater(() -> {
				view.close();
			});
			break;
		case "turno":
			TurnoMessage tm = gson.fromJson(msg, TurnoMessage.class);
			turno = tm.isTurno();
			if (turno) {
				view.getTurno().setText("Es tu turno");
			} else {
				view.getTurno().setText("Turno del contrincante");
			}
			break;
		case "ganador":
			JOptionPane.showMessageDialog(null, "¡Ganaste!", "Felicitaciones", JOptionPane.INFORMATION_MESSAGE);
			for (int i = 0; i < listButton.size(); i++) {
				listButton.get(i).setDisable(false);
			}
			turno = false;
			view.getTurno().setText("Esperando contrincante");
			break;
		case "perdedor":
			JOptionPane.showMessageDialog(null, "¡Perdiste!", "Mala Suerte", JOptionPane.INFORMATION_MESSAGE);
			Platform.runLater(() -> {
				view.close();
			});

			break;

		}

	}

}
