package model;

public class SendNumberMessage {
	private String type = "send number";
	private int number;
	
	public SendNumberMessage() {
		
	}
	
	public SendNumberMessage(int i) {
		this.number = i;
	}

	public String getType() {
		return type;
	}

	public int getNumber() {
		return number;
	}
	
	
	

}
