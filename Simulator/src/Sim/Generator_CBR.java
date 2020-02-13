package Sim;

public class Generator_CBR extends Node {
	private double _time = 0.0; // 
	protected int _packets_per_second; // Number of packets per second
	private int _stopSendingAfter = 0; // Number of packets that the genereator will generate
	
	
	
	public Generator_CBR(int network, int node) {
		super(network, node);
		_id = new NetworkAddr(network, node); //id of the network

	}
	
	
	public void StartSending(int network, int node, int number, int packets_per_second)
	{
		_stopSendingAfter = number;
		_packets_per_second = packets_per_second;
		_toNetwork = network;
		_toHost = node;
		_seq = 1;
		send(this, new TimerEvent(),0);	
	}

	public void recv(SimEnt src, Event ev)
	{
		if (ev instanceof TimerEvent)
		{			
			System.out.println(_sentmsg);
			if (_stopSendingAfter > _sentmsg)
			{
				System.out.println(_stopSendingAfter);
				double tempT = 1.0/_packets_per_second;
				//int package_number = 1;
				for(int i = 0; i < _stopSendingAfter; i++) {
					
				
				System.out.println("Packge number " + _seq + "Time " + (_time + SimEngine.getTime()));
				_sentmsg++;
				System.out.println(_id);
				send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost),_seq),0);
				send(this, new TimerEvent(), _time);
				System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq + " at time "+SimEngine.getTime());
				_seq++;
				_time += tempT;
				//package_number ++;
				}
				
			
			}
			send(this, new TimerEvent(),1);
		}
		if (ev instanceof Message)
		{
			System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());
			
		}
	}

}
