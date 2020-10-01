package comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import model.GanadorMessage;
import model.PartidaEnProceso;
import model.PerdedorMessage;
import model.TurnoMessage;

public class TCPConnection extends Thread{

	//SINGLETON
	private static TCPConnection instance = null;
	private TCPConnection() {
		
	}
	public static synchronized TCPConnection getInstance() {
		if(instance == null) {
			instance = new TCPConnection();
		}
		return instance;
	}
	
	
	//GLOBAL
	private int puerto;
	private OnConnectionListener connectionListener;
	private OnMessageListener messageListener;
	private ServerSocket server;
	private Session jugador1;
	private Session jugador2;
	private Session enPartida;
	
	
	
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(puerto);
			
			while(true) {
				System.out.println("Esperando en el puerto " + puerto);
				Socket socket = server.accept();
				if(jugador1 == null || jugador2 == null) {
				System.out.println("Nuevo cliente conectado");
				String id = UUID.randomUUID().toString();
				Session session = new Session(id, socket);
				connectionListener.onConnection(id);
				if(jugador1==null) {
					jugador1 = session;
				}else {
					jugador2 = session;
				}
				setAllMessageListener(messageListener);
				if(jugador1 != null && jugador2!=null) {
					jugador1.giveNumber();
					jugador2.giveNumber();
					turno();
				}
				}else {
					String id = UUID.randomUUID().toString();
					Session session = new Session(id, socket);
					enPartida = session;
					partidaEnProceso();
					enPartida = null;
				}
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setAllMessageListener(OnMessageListener listener) {
		if(jugador1 != null) {
			jugador1.getReceptor().setListener(listener);
		}
		if(jugador2 != null) {
			jugador2.getReceptor().setListener(listener);
		}		
	}
	
	public void setConnectionListener(OnConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}
	
	public void turno() {
		if(jugador1.isTurno() == true && jugador2.isTurno() == false) {
			jugador1.setTurno(false);
			jugador2.setTurno(true);
			System.out.println();
		}else if(jugador1.isTurno() == false && jugador2.isTurno() == true) {
			jugador1.setTurno(true);
			jugador2.setTurno(false);
		}else {
			jugador1.setTurno(true);
			jugador2.setTurno(false);
		}
		try {
			Thread.sleep(50);
			Gson gson = new Gson();
			TurnoMessage tm1 = new TurnoMessage(jugador1.isTurno());
			String stm1 = gson.toJson(tm1);
			jugador1.getEmisor().sendMessage(stm1);
			TurnoMessage tm2 = new TurnoMessage(jugador2.isTurno());
			String stm2 = gson.toJson(tm2);
			jugador2.getEmisor().sendMessage(stm2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public interface OnConnectionListener{
		public void onConnection(String id);
	}
	
	public void setMessageListener(OnMessageListener messageListener) {
		this.messageListener = messageListener;
	}
	
	public void partidaEnProceso() {
		try {
			Thread.sleep(50);
			Gson gson = new Gson();
			PartidaEnProceso pp = new PartidaEnProceso();
			String spp = gson.toJson(pp);
			enPartida.getEmisor().sendMessage(spp);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void verifyNumber(int number) {
		Gson gson = new Gson();
		PerdedorMessage pm = new PerdedorMessage();
		GanadorMessage gm = new GanadorMessage();
		String mp = gson.toJson(pm);
		String mg = gson.toJson(gm);
		if(jugador1.isTurno()) {
			if(jugador2.getNumber() == number) {
				jugador1.getEmisor().sendMessage(mg);
				jugador2.getEmisor().sendMessage(mp);
				jugador2 = null;
			}else {
				turno();
			}
		}else {
			if(jugador1.getNumber() == number) {
				jugador1.getEmisor().sendMessage(mp);
				jugador2.getEmisor().sendMessage(mg);
				jugador1 = null;
			}else {
				turno();
			}
		}
		
	}


}
