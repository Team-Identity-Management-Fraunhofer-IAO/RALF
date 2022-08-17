package de.ralf.threatmasterkitchen.controller.datatransfer;

public class PlatformUpdateDTO {
	int attack_pattern_id;
	String platforms;
	
	public PlatformUpdateDTO() {
		
	}
	
	public int getAttack_pattern_id() {
		return attack_pattern_id;
	}
	public void setAttack_pattern_id(int attack_pattern_id) {
		this.attack_pattern_id = attack_pattern_id;
	}
	public String getPlatforms() {
		return platforms;
	}
	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}
	
	
}
