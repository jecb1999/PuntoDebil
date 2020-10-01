package model;

public class TurnoMessage {
	
	private String type = "turno";
	private boolean turno;
	
	public TurnoMessage() {
		
	}
	
	public TurnoMessage(boolean turno) {
		this.turno = turno;
	}

	public String getType() {
		return type;
	}

	public boolean isTurno() {
		return turno;
	}
	
}
