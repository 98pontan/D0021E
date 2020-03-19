package Sim;

// This class implements a node (host) it has an address, a peer that it communicates with
// and it count messages send and received.

public class Node extends SimEnt {
	protected NetworkAddr _id;
	protected SimEnt _peer;
	protected int _sentmsg=0;
	protected int _seq = 0;


	public Node (int network, int node)
	{
		super();
		_id = new NetworkAddr(network, node);
	}


	// Sets the peer to communicate with. This node is single homed

	public void setPeer (SimEnt peer)
	{
		_peer = peer;

		if(_peer instanceof Link )
		{
			 ((Link) _peer).setConnector(this);
		}
	}


	public NetworkAddr getAddr()
	{
		return _id;
	}

//**********************************************************************************	
	// Just implemented to generate some traffic for demo.
	// In one of the labs you will create some traffic generators

	protected int _stopSendingAfter = 0; //messages
	protected int _timeBetweenSending = 10; //time between messages
	protected int _toNetwork = 0;
	protected int _toHost = 0;

	public void StartSending(int network, int node, int number, int timeInterval, int startSeq)
	{
		_stopSendingAfter = number;
		_timeBetweenSending = timeInterval;
		_toNetwork = network;
		_toHost = node;
		_seq = startSeq;
		send(this, new TimerEvent(),0);
	}

	private int _changeInterfaceAfter = 0;
	private int _desiredInterface;
	private NetworkAddr _oldInterface;
	private int _olderInterface;

	/* Router variables */
	private int _changeRouterAfter;
	private Router _nextRouter;
	private Router _fromRouter;
	
	void moveInterfaceAfter(int desiredInterface, int numberOfMessages, int olderInterface) {
		_changeInterfaceAfter = numberOfMessages;
		_desiredInterface = desiredInterface;
		_olderInterface = olderInterface;
	}

	/*
	protected void solicit() {
		System.out.println(this.toString() + " Solicitation request sent");
		send(_peer, new Solicitation(this._id, 0), 0);
	}

	 */
	
	protected void changeRouterAfter(int NumberOfMessages, Router fromRouter, Router nextRouter) {
		_nextRouter = nextRouter;
		_changeRouterAfter = NumberOfMessages;
		_fromRouter = fromRouter;
	}
	
	
//**********************************************************************************	

	// This method is called upon that an event destined for this node triggers.

	public void recv(SimEnt src, Event ev)
	{
		if (ev instanceof TimerEvent)
		{
			if (_stopSendingAfter > _sentmsg)
			{
				_sentmsg++;
				send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost),_seq),0);
				send(this, new TimerEvent(),_timeBetweenSending);
				System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq + " at time "+SimEngine.getTime());
				_seq++;
				if (_sentmsg == _changeInterfaceAfter) {
					send(_peer, new MoveInterfaceEvent(_id, _desiredInterface, _olderInterface), 0);
					System.out.println("Node " + _id.networkId() + "."+_id.nodeId() + "tries to change interface to " + _desiredInterface);
				}
				else if (_sentmsg == _changeRouterAfter) {
					//send(_peer, new NodeInterfaceChange(this, 4, (Link) _peer), 0);
					//HARDCODED PLS CHANGE
					NetworkAddr careOfAddress=new NetworkAddr(2, 5);
				//	setupLink(_fromRouter,_nextRouter);
					send(_peer, new BindingUpdate(getAddr(), this, _nextRouter), 0);

					//send(_peer, new Solicitation(), 0);
					System.out.println("Change router after ACCESSED");
				}
			}
		}
		else if (ev instanceof Message)
		{
			System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());
		}
		/*
		else if (ev instanceof Advertisement) {
			send(_peer, new BindingUpdate(_careOfAddress, _id), 0);
		}

		 */

	}

	public void setupLink(Router fromRouter,Router nextRouter) {
		fromRouter.disconnectInterface(getAddr());
		Link link = new Link();
		this.setPeer(link);
		//SimEnt address = fromRouter.getInterface(this.getAddr().);
		//System.out.println(address);
		nextRouter.connectInterface(nextRouter.getFreeInterface(), link, this);
	}


}