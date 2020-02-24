package Sim;

public class MoveInterface implements Event {
	
	private NetworkAddr _lastInterface;
	private int _newInterfaceNumber;
	protected int _oldInterface;
	
	MoveInterface(NetworkAddr source, int newInterfaceNumber, int oldInterface) {
		// TODO Auto-generated method stub
		this._lastInterface = source;
		this._newInterfaceNumber = newInterfaceNumber;
		this._oldInterface = oldInterface;
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
