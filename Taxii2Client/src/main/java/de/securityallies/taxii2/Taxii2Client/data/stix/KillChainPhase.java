package de.securityallies.taxii2.Taxii2Client.data.stix;

public class KillChainPhase {
	private String kill_chain_name;
	private String phase_name;
	
	public String getKill_chain_name() {
		return kill_chain_name;
	}
	
	public void setKill_chain_name(String kill_chain_name) {
		this.kill_chain_name = kill_chain_name;
	}
	
	public String getPhase_name() {
		return phase_name;
	}
	
	public void setPhase_name(String phase_name) {
		this.phase_name = phase_name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof KillChainPhase) {
			if (((KillChainPhase) o).getKill_chain_name().equals(this.getKill_chain_name()) && ((KillChainPhase) o).getPhase_name().equals(this.getPhase_name())){
				return true;
			}
			return false;
		}
		return false;
	}
	
}
