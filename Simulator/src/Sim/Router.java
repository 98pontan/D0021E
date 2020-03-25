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
                SimEnt entity = _routingTable[i].node();
                if (entity instanceof Node) {
                    Node node = (Node)entity;
                    if (node.getAddr().compare(networkAddr)) {
                        _routingTable[i] = null;
                    }
                } else if (entity instanceof Router) {
                    Router router = (Router)entity;
                    if (router.getRouterID() == networkAddr.networkId()) {
                        _routingTable[i] = null;
                    }
                }
            }
    }

    public void advertise()  {
        for (int i = 0; i < _interfaces; i++)
            if (_routingTable[i] != null) {
                SimEnt entity = _routingTable[i].node();
                if (entity instanceof Node) {
                    Node node = (Node) entity;
                    System.out.println("Router: " + getRouterID() + " sends advertisement to " + node._id.networkId() + "." + node._id.nodeId());
                    send(node, new Advertisement(this), 0);
                }
            }
    }


    // This method searches for an entry in the routing table that matches
    // the network number in the destination field of a messages. The link
    // represents that network number is returned


    public SimEnt getInterface(NetworkAddr addr) {
        SimEnt routerInterface = null;
        for (int i = 0; i < _interfaces; i++)
            if (_routingTable[i] != null) {
                SimEnt entity = _routingTable[i].node();
                if (entity instanceof Node) {
                    Node node = (Node)entity;
                    if (node.getAddr().compare(addr)) {
                        routerInterface = _routingTable[i].link();
                        return routerInterface;
                    }
                } else if (entity instanceof Router) {
                    Router router = (Router)entity;
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

            NetworkAddr homeAddress = ((BindingUpdate) event).getHomeAddress();
            Node node = ((BindingUpdate) event).getNode();
            Router nextRouter = ((BindingUpdate) event).getNextRouter();
            disconnectInterface(homeAddress);                            // ACTIVATE?
            getUniqueAddress(nextRouter, node);
            Link link = new Link();
            link.setConnector(node);
            nextRouter.connectInterface(nextRouter.getFreeInterface(),link,node);
            homeAgent.newAddress(homeAddress.nodeId(), node._id);
            System.out.println("Binding complete, Node: " + homeAddress.networkId() + "." + homeAddress.nodeId() + " switched router and got new care of address, Node: " + node._id.networkId() + "." + node._id.nodeId());

        }

        if (event instanceof MoveInterfaceEvent) {
            changeInterface(((MoveInterfaceEvent) event)._oldInterface(), ((MoveInterfaceEvent) event)._newInterfaceNumber());        }
        if (event instanceof Message) {
            Message msg = (Message)event;
            NetworkAddr msource = msg.source();
            NetworkAddr mdestination = msg.destination();
            NetworkAddr careOfAddress = homeAgent.getCoaAddress(mdestination.nodeId());

            if (careOfAddress != null && mdestination.networkId() != msource.networkId()) {
                // tunnel message to the care-of address
                System.out.println("Router: " + this.routerID + " home agent is tunneling message from " + mdestination.networkId() + "." + mdestination.nodeId() + " to " + careOfAddress.networkId() + "." + careOfAddress.nodeId() + " with seq number " + msg.seq());
                mdestination = careOfAddress;
                msg.setDestination(careOfAddress);
            }



            System.out.println("Router " + this.getRouterID() + " handles packet with seq: " + msg.seq() + " from node: " + msource.networkId() + "." + msource.nodeId());
            SimEnt sendNext = getInterface(mdestination);
            if (sendNext == null) {
                System.err.println("Router " + this.getRouterID() + ": host " + mdestination + " is unreachable");
            } else {
                System.out.println("Router: " + this.getRouterID() + " sends packet to node: " + mdestination.networkId() + "." + mdestination.nodeId() + " with seq number " + msg.seq());
                send(sendNext, event, _now);
            }
        }
        if (event instanceof Solicitation) {
            System.out.println("Router : " + this.getRouterID() + " recieved solictiation message from: " + ((Solicitation) event).getAddress().networkId() + "." + ((Solicitation) event).getAddress().nodeId());
            advertise();
        }
        if (event instanceof LSA) {
            //sendLSA();
            LSA message = (LSA) event;
            System.out.println("Router " + getRouterID() + " received LSA from " + "Router: " + message.getRouterID());
        }
    }

    public void getUniqueAddress(Router nextRouter, Node node) {
        int adder=1;
        node._id = new NetworkAddr(nextRouter.getRouterID(), 1);
        while(isAddressUnique(node._id, nextRouter)!=true) {
            node._id = new NetworkAddr(nextRouter.getRouterID(), adder);
            adder++;
        }
    }

    public boolean isAddressUnique(NetworkAddr addr, Router nextRouter) {
        for (int i = 0; i < nextRouter._interfaces; i++)
            if (nextRouter._routingTable[i] != null) {
                SimEnt dev = nextRouter._routingTable[i].node();

                if (dev instanceof Node) {
                    Node node = (Node)dev;
                    if (node.getAddr().compare(addr)) {
                        return false;
                    }
                }
            }
        return true;
    }


    public void changeInterface(int newInterfaceNumber, int oldInterfaceNumber) {
        if (newInterfaceNumber < _interfaces && _routingTable[newInterfaceNumber] == null) {
            RouteTableEntry route = _routingTable[oldInterfaceNumber];
            _routingTable[oldInterfaceNumber] = null;
            _routingTable[newInterfaceNumber] = route;
        } else {
            System.out.println("The port doesn't exist or is already occupied");
        }
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

    public void sendLSA() {
        for (int i = 0; i < _interfaces; i++)
            if (_routingTable[i] != null) {
                SimEnt entity = _routingTable[i].node();
                SimEnt link = _routingTable[i].link();
                if (entity instanceof Router) {
                    Router router = (Router) entity;
                    WeightedLink linkerino = (WeightedLink) link;
                    linkerino.getWeight();
                    //System.out.println("Router: " + getRouterID() + " sends LSA to " + router.getRouterID());
                    send(router, new LSA(getRouterID()), 0);
                }
            }
    }

}
