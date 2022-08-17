package com.sql.data.objects.helpers.attackpatterns;

public class OrderedKillChainPhase {
	private int kill_chain_order;
	private String kill_chain_name;
	private String phase_name;
	
	public OrderedKillChainPhase() {
		
	}

	public int getKill_chain_order() {
		return kill_chain_order;
	}

	public void setKill_chain_order(int kill_chain_order) {
		this.kill_chain_order = kill_chain_order;
	}

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kill_chain_name == null) ? 0 : kill_chain_name.hashCode());
		result = prime * result + kill_chain_order;
		result = prime * result + ((phase_name == null) ? 0 : phase_name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderedKillChainPhase other = (OrderedKillChainPhase) obj;
		if (kill_chain_name == null) {
			if (other.kill_chain_name != null)
				return false;
		} else if (!kill_chain_name.equals(other.kill_chain_name))
			return false;
		if (kill_chain_order != other.kill_chain_order)
			return false;
		if (phase_name == null) {
			if (other.phase_name != null)
				return false;
		} else if (!phase_name.equals(other.phase_name))
			return false;
		return true;
	}
	
	

}
