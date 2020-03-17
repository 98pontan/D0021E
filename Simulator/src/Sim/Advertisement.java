package Sim;

public class Advertisement implements Event {
	protected NetworkAddr _adAddr;
	
	protected Advertisement(NetworkAddr adAddr){
		this._adAddr = adAddr; 
	}
	
	protected NetworkAddr get_adAddr() {
		return _adAddr;
	}
	@Override
	public void entering(SimEnt locale) {

	}
}
