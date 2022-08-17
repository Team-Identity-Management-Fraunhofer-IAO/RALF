package de.ralf.threatmasterkitchen.data.softwarestack.datatransfer;

public class WatchlistDTO {
	private String id;
	private String notification;
	private boolean treated;
	private String mail;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public boolean isTreated() {
		return treated;
	}
	public void setTreated(boolean treated) {
		this.treated = treated;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}	

}
