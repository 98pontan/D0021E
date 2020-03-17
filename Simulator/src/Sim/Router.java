package Sim;

// This class implements a simple router

import java.util.HashMap;

public class Router extends SimEnt {

    protected RouteTableEntry[] _routingTable;
    protected int _interfaces;
    protected int _now = 0;
    protected HashMap<NetworkAddr, NetworkAddr> routingTable; // HashMap for mapping home addresses to the new address, Key HomeAddress Value foreign address
    private int networkID;
    private NetworkAddr networkAddress;


    // When created, number of interfaces are defined

    Router(int interfaces, int networkID) {
        _routingTable = new RouteTableEntry[interfaces];
        _interfaces = interfaces;
        this.routingTable = new HashMap<>();
        networkAddress = new NetworkAddr(networkID, 0);
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


    // This method searches for an entry in the routing table that matches
    // the network number in the destination field of a messages. The link
    // represents that network number is returned


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


    // When messages are received at the router this method is called

    public void recv(SimEnt source, Event event) {
        if (event instanceof BindingUpdate) {
            // call function that changes network address
            System.out.println(((BindingUpdate) event).getHomeAddress());
            networkChanger(((BindingUpdate) event).getHomeAddress(), ((BindingUpdate) event).getCareOfAddress());
        }

        if (event instanceof MoveInterfaceEvent) {
            changeInterface(((MoveInterfaceEvent) event)._oldInterface(), ((MoveInterfaceEvent) event)._newInterfaceNumber());
        }
        if (event instanceof Message) {
            System.out.println("Router handles packet with seq: " + ((Message) event).seq() + " from node: " + ((Message) event).source().networkId() + "." + ((Message) event).source().nodeId());
            //SimEnt sendNext = getInterface(((Message) event).destination().networkId());
            SimEnt sendNext;
            NetworkAddr destination = ((Message) event).destination();
            NetworkAddr careOfAddress = routingTable.get(destination); // Lookup in the HashMap to redirect the incoming message
            System.out.println("Router sends to node: " + ((Message) event).destination().networkId() + "." + ((Message) event).destination().nodeId());
            if (careOfAddress != null) {
                sendNext = getInterface(careOfAddress.networkId());
            } else {
                sendNext = getInterface(destination.networkId());
            }
            send(sendNext, event, _now);


        }
        if (event instanceof Solicitation) {
            System.out.println("Router recieved solictiation message from: " + ((Message) event).source().networkId() + "." + ((Message) event).source().nodeId());
            //send() send advertisement
        }
    }

    public void networkChanger(NetworkAddr homeAddress, NetworkAddr careOfAddress) {
        routingTable.put(homeAddress, careOfAddress);
        System.out.println("networkChan" +
                "ger Test");
        System.out.println(routingTable);

    }

    public void changeInterface(int newInterfaceNumber, int oldInterfaceNumber) {
        if (newInterfaceNumber < _interfaces && _routingTable[newInterfaceNumber] == null) {
            RouteTableEntry route = _routingTable[oldInterfaceNumber];
            _routingTable[oldInterfaceNumber] = null;
            _routingTable[newInterfaceNumber] = route;
            //printInterfaces();

        } else {
            System.out.println("The port doesn't exist or is already occupied");
        }
    }
/*
    public void printInterfaces() {
        for (int i = 0; i < _routingTable.length; i++) {
            if (_routingTable[i] != null) {
                if (_routingTable[i].node() instanceof Node) {
                    System.out.println("Node: " + ((Node) _routingTable[i].node()).getAddr().networkId() + "." + ((Node) _routingTable[i].node()).getAddr().nodeId() + " router interface:" + i);
                } else if (_routingTable[i].node() instanceof Router) {
                    System.out.println("Router id: " + ((Router) _routingTable[i].node()).getRouterID() + " on interface:" + i);
                }
            } else {
                System.out.println("Entry " + i + ": " + "null");
            }

        }
    }

 */

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
    public int getFreeInterface() {
        for (int i = 0; i < _routingTable.length; i++) {
            if (_routingTable[i] != null) {
                return i;
            }
        }
        return 0;
    }
}
