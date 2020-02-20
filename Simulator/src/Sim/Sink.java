package Sim;

public class Sink extends Node {

	public Sink(int network, int node) {
		super(network, node);
		// TODO Auto-generated constructor stub
	}
	

	public void recv(SimEnt src, Event ev) {
		if (ev instanceof Message)
		{
			System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());
		}
	
	}
}
