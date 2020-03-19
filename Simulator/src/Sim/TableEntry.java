package Sim;

// Just a class that works like a table entry hosting
// a link connecting and the node at the other end

public class TableEntry {
	protected NetworkAddr _netAddress;
	private SimEnt _link;
	
	TableEntry(NetworkAddr netAddress, SimEnt link){
		_netAddress=netAddress;
		_link = link;
	}
	
	protected int netAddress() {
		return _netAddress.networkId();
	}
	
	protected SimEnt link() {
		return _link;
	}
	
	protected int node() {
		return _netAddress.nodeId();
	}
	/*
	private SimEnt _link;
	private SimEnt _node;

	TableEntry(SimEnt link, SimEnt node)
	{
		_link=link;
		_node=node;
	}

	protected SimEnt link()
	{
		return _link;
	}

	protected SimEnt node()
	{
		return _node;
	}
*/
}
