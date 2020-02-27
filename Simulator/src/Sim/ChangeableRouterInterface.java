package Sim;

public class ChangeableRouterInterface extends Router {

    // When created, number of interfaces are defined

    ChangeableRouterInterface(int interfaces) {
        super(interfaces);
        this._interfaces = interfaces;
    }

    // This method connects links to the router and also informs the
    // router of the host connects to the other end of the link

    public void changeInterface(int newInterfaceNumber, int oldInterfaceNumber) {
        if (newInterfaceNumber < _interfaces && _routingTable[newInterfaceNumber] == null) {
            RouteTableEntry route = _routingTable[oldInterfaceNumber];
            _routingTable[oldInterfaceNumber] = null;
            _routingTable[newInterfaceNumber] = route;
        } else
            System.out.println("The port doesn't exist or is already occupied");

    }


    public void printInterfaces() {
        for (int i = 0; i < _interfaces; i++) {
            System.out.println("Entry " + i + ": " + _routingTable[i]);
            //System.out.println((Node)_routingTable[i].node().getAddr());
        }
        System.out.println("------------------");
    }

    // When messages are received at the router this method is called

    public void recv(SimEnt source, Event event) {
        System.out.println("Event Type rcv in router: " + event);
        System.out.println("Src: " + source);
        if (event instanceof MoveInterfaceEvent) {
            changeInterface(((MoveInterfaceEvent) event)._oldInterface, ((MoveInterfaceEvent) event)._newInterfaceNumber());
            System.out.println("fuk");
        }
        if (event instanceof Message) {
            System.out.println("Router handles packet with seq: " + ((Message) event).seq() + " from node: " + ((Message) event).source().networkId() + "." + ((Message) event).source().nodeId());
            SimEnt sendNext = getInterface(((Message) event).destination().networkId());
            System.out.println("Router sends to node: " + ((Message) event).destination().networkId() + "." + ((Message) event).destination().nodeId());
            send(sendNext, event, _now);
        }
    }
}