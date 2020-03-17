package Sim;

public class MoveInterfaceEvent implements Event {
	
	private NetworkAddr _networkAddress;
	private int _newInterfaceNumber;
	private int _oldInterface;

	MoveInterfaceEvent(NetworkAddr source, int newInterfaceNumber, int oldInterface) {
		// TODO Auto-generated method stub
		this._networkAddress = source;
		this._newInterfaceNumber = newInterfaceNumber;
		this._oldInterface = oldInterface;
	}
	
	public NetworkAddr _networkAdress() {
		return _networkAddress;
	}
	
	public int _newInterfaceNumber(){
		return _newInterfaceNumber;
	}
	public int _oldInterface() { return _oldInterface; }

	public void entering(SimEnt locale) {
		
	}
}
