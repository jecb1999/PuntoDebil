package control;

import comm.Receptor.OnMessageListener;

import java.util.Calendar;
import java.util.UUID;

import com.google.gson.Gson;

import comm.TCPConnection;
import comm.TCPConnection.OnConnectionListener;
import javafx.application.Platform;
import model.Generic;
import model.SendNumberMessage;
import view.ChatWindow;

public class ChatController implements OnMessageListener, OnConnectionListener{
	
	private ChatWindow view;
	private TCPConnection connection;
	
	public ChatController(ChatWindow view) {
		this.view = view;
		init();
	}
	
	public void init() {
		connection = TCPConnection.getInstance();
		connection.setPuerto(5000);
		connection.start();
		connection.setConnectionListener(this);
		connection.setMessageListener(this);
	}
	
	
	@Override
	public void onConnection(String id) {
			Platform.runLater(
				
				()->{
					view.getMessagesArea().appendText("<<< Nuevo cliente conectado " + id + "! >>>\n");
				}
				
				);
		
	}

	@Override
	public void OnMessage(String msg) {
		
		
		Gson gson = new Gson();
		Generic type = gson.fromJson(msg, Generic.class);
		
		switch(type.getType()) {
			case "send number":
				SendNumberMessage snm = gson.fromJson(msg, SendNumberMessage.class);
				connection.verifyNumber(snm.getNumber());
				break;
		}
		
		
		
		
	}

	

}
