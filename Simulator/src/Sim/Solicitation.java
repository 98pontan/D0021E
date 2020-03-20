package Sim;

public class Solicitation implements Event {
	private NetworkAddr from;

	Solicitation(NetworkAddr networkAddr) {
		this.from = networkAddr;
	}

	public NetworkAddr getAddress() {
		return from;
	}

	@Override
	public void entering(SimEnt locale) {

	}
}
