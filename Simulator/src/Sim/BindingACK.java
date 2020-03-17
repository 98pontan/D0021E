package Sim;


public class BindingACK implements Event {

	private NetworkAddr careOfAddress;
	private NetworkAddr homeAddress;

	BindingACK(NetworkAddr careOfAddress, NetworkAddr homeAddress) {
		this.careOfAddress = careOfAddress;
		this.homeAddress = homeAddress;
	}

	public void entering(SimEnt locale) {

	}

	public NetworkAddr getCareOfAddress() {
		return careOfAddress;
	}

	public NetworkAddr getHomeAddress() {
		return homeAddress;
	}
}
