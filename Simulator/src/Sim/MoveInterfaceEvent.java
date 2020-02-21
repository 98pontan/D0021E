package Sim;

public class MoveInterfaceEvent implements Event {
	
	private NetworkAddr _lastInterface;
	private int _newInterfaceNumber;
	
	MoveInterfaceEvent(NetworkAddr source, int newInterfaceNumber) {
		// TODO Auto-generated method stub
		_lastInterface = source;
		_newInterfaceNumber = newInterfaceNumber;
	}
	
	public NetworkAddr lastInterface() {
		return _lastInterface;
	}
	
	public int _newInterfaceNumber(){
		return _newInterfaceNumber;
	}
	
	
	public void entering(SimEnt locale) {
		
	}
}
