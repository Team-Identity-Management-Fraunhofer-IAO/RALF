package de.ralf.threatmasterkitchen.controller.datatransfer;

public class RiskClassDTO {
	private int from;
	private int to;
	private String name;
	
	public RiskClassDTO() {
		
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
