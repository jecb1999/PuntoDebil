package comm;

import java.io.IOException;
import java.net.Socket;

import comm.Receptor.OnMessageListener;

public class Session{
	
	
	private String id;
	private Socket socket;
	private Receptor receptor;
	private Emisor emisor;
	private OnMessageListener listener;
	private boolean turno;
	private int winnerNumber;
	
	
	public Session(String id, Socket socket) {
		try {
			this.id = id;
			this.socket = socket;
			this.turno = false;
			receptor = new Receptor(socket.getInputStream());
			receptor.start();
			emisor = new Emisor(socket.getOutputStream());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public Emisor getEmisor() {
		return this.emisor;
	}
	
	public Receptor getReceptor() {
		return this.receptor;
	}
	
	public String getId() {
		return id;
	}

	public boolean isTurno() {
		return turno;
	}

	public void setTurno(boolean turno) {
		this.turno = turno;
	}
	
	public int getNumber() {
		return winnerNumber;
	}
	
	public void giveNumber() {
		winnerNumber = (int) Math.floor(Math.random()*8+1);
	}

}
