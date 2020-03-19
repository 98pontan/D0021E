package Sim;

// This class implements a simple router

import java.util.HashMap;

public class Router extends SimEnt {

    protected RouteTableEntry[] _routingTable;
    protected int _interfaces;
    protected int _now = 0;
    private int routerID;
    private HomeAgent homeAgent;


    // When created, number of interfaces are defined

    Router(int interfaces, int routerID) {
        this._routingTable = new RouteTableEntry[interfaces];
        this._interfaces = interfaces;
        this.routerID = routerID;
        this.homeAgent = new HomeAgent();
    }

    public int getRouterID() {
        return this.routerID;
    }

    // This method connects links to the router and also informs the
    // router of the host connects to the other end of the link

    public void connectInterface(int interfaceNumber, SimEnt link, SimEnt node) {
        if (interfaceNumber < _interfaces) {
            _routingTable[interfaceNumber] = new RouteTableEntry(link, node);
        } else
            System.out.println("Trying to connect to port not in router");

        ((Link) link).setConnector(this);
    }

    public void disconnectInterface(NetworkAddr networkAddr) {
        for (int i = 0; i < _interfaces; i++)
            if (_routingTable[i] != null) {
                SimEnt dev = _routingTable[i].node();
                if (dev instanceof Node) {
                    Node node = (Node)dev;
                    if (node.getAddr().compare(networkAddr)) {
                        _routingTable[i] = null;
                    }
                } else if (dev instanceof Router) {
                    Router router = (Router)dev;
                    if (router.getRouterID() == networkAddr.networkId()) {
                        _routingTable[i] = null;
                    }
                }
            }
    }


    // This method searches for an entry in the routing table that matches
    // the network number in the destination field of a messages. The link
    // represents that network number is returned

/*
    protected SimEnt getInterface(int networkAddress) {
        SimEnt routerInterface = null;
        for (int i = 0; i < _interfaces; i++)
            if (_routingTable[i] != null) {
                if (((Node) _routingTable[i].node()).getAddr().networkId() == networkAddress) {
                    routerInterface = _routingTable[i].link();
                }
            }
        return routerInterface;
    }


 */

    public SimEnt getInterface(NetworkAddr addr) {
        SimEnt routerInterface = null;
        for (int i = 0; i < _interfaces; i++)
            if (_routingTable[i] != null) {
                SimEnt dev = _routingTable[i].node();

                if (dev instanceof Node) {
                    Node node = (Node)dev;

                    System.err.println(node.getAddr());
                    if (node.getAddr().compare(addr)) {
                        routerInterface = _routingTable[i].link();
                        return routerInterface;
                    }
                } else if (dev instanceof Router) {
                    Router router = (Router)dev;

                    if (router.getRouterID() == addr.networkId()) {
                        routerInterface = _routingTable[i].link();
                        return routerInterface;
                    }
                }
            }
        return null;
    }


    // When messages are received at the router this method is called

    public void recv(SimEnt source, Event event) {
        if (event instanceof BindingUpdate) {
            /*
            Node node = (Node)source;
            Router router = (Router)this;
            // call function that changes network address
            System.out.println(((BindingUpdate) event).getHomeAddress());


            Link link = new Link();
            node.setPeer(link);

            router.connectInterface(getFreeInterface(), link, node);

             */

            networkChanger(((BindingUpdate) event).getHomeAddress(), ((BindingUpdate) event).getCareOfAddress());

        }

        if (event instanceof MoveInterfaceEvent) {
            changeInterface(((MoveInterfaceEvent) event)._networkAdress(), ((MoveInterfaceEvent) event)._newInterfaceNumber());
        }
        if (event instanceof Message) {
            Message msg = (Message)event;
            NetworkAddr msource = msg.source();
            NetworkAddr mdestination = msg.destination();
            NetworkAddr careOfAddress = homeAgent.getCoaAddress(msource);

            if (careOfAddress != null) {
                // tunnel message to the care-of address
                System.out.println("HA: Tunneling message from " + mdestination.toString() + " to " + careOfAddress.toString());
                mdestination = careOfAddress;
                msg.setDestination(careOfAddress);
            }

            System.out.println("Router " + this.getRouterID() + " handles packet with seq: " + msg.seq() + " from node: " + msource);
            SimEnt sendNext = getInterface(mdestination);
            if (sendNext == null) {
                System.err.println("Router " + this.getRouterID() + ": host " + mdestination + " is unreachable");
            } else {
                System.out.println("Router sends to node: " + mdestination.toString());
                send(sendNext, event, _now);
            }
        }
        if (event instanceof Solicitation) {
            System.out.println("Router recieved solictiation message from: " + ((Message) event).source().networkId() + "." + ((Message) event).source().nodeId());
            //send() send advertisement
        }
    }

    public void networkChanger(NetworkAddr homeAddress, NetworkAddr careOfAddress) {
        homeAgent.newAddress(homeAddress, careOfAddress);
    }


    //FIXA FUNKTION
    public void changeInterface(NetworkAddr oldInterface, int newInterfaceNumber){
        if(_routingTable[newInterfaceNumber] != null){
            System.out.println("!! Interface occupied!");
            return;
        }
        for ( int i  = 0; i < _interfaces; i++){
            if(_routingTable[i] != null){
                try {
                    if (((Node) _routingTable[i].node()).getAddr() == oldInterface) {
                        RouteTableEntry r = _routingTable[i];
                        _routingTable[i] = null;
                        _routingTable[newInterfaceNumber] = r;
                        //_routingTable[newInterfaceNumber] = _routingTable[i];
                    }
                }catch(Exception e){
                    continue;
                }
            }
        }
        return;
    }

    public void printInterfaces() {
        System.out.println("Router ID: " + getRouterID());
        for(int i = 0; i <_routingTable.length; i++) {
            if(_routingTable[i]!=null) {
                if(_routingTable[i].node() instanceof Node){
                    System.out.println("Entry " + i + " Node: " +((Node)_routingTable[i].node()).getAddr().networkId() + "." + ((Node)_routingTable[i].node()).getAddr().nodeId());
                }else if(_routingTable[i].node() instanceof Router){
                    System.out.println("Entry " + i + " Router id: " +((Router)_routingTable[i].node()).getRouterID());
                }
            }
            else {
                System.out.println("Entry " + i + ": " + "null");
            }

        }
    }
    public int getFreeInterface() {
        for (int i = 1; i < _routingTable.length; i++) {
            if (_routingTable[i]==null) {
                return i;
            }
        }
        return -1;
    }

/*
	public void printInterfaces() {
		System.out.println("------------------");
		for (int i = 0; i < _interfaces; i++) {
			if(_routingTable[i] != null) {
				System.out.println("Entry " + i + ": " + "Node " +(((Node) _routingTable[i].node()).getAddr().networkId()));
			}
			else {
				System.out.println("Entry " + i + ": " + "null");
			}
			//System.out.println((Node)_routingTable[i].node().getAddr());
		}
		System.out.println("------------------");
	}


 */
}
