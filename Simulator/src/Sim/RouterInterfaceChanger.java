package Sim;

public class RouterInterfaceChanger extends Router {

    // When created, number of interfaces are defined



    //OUTDATED
    RouterInterfaceChanger(int interfaces, LSDB lsdb) {
        super(interfaces, 1, lsdb); //NOT SUPPOSED TO BE LIKE THIS
        this._interfaces = interfaces;
    }

    // This method connects links to the router and also informs the
    // router of the host connects to the other end of the link

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
        for(int i = 0; i <_routingTable.length; i++) {
            if(_routingTable[i]!=null) {
                if(_routingTable[i].node() instanceof Node){
                    System.out.println("*** Node: " +((Node)_routingTable[i].node()).getAddr().networkId() + "." + ((Node)_routingTable[i].node()).getAddr().nodeId() + " router interface:" + i);
                }else if(_routingTable[i].node() instanceof Router){
                    System.out.println("*** Router id: " +((Router)_routingTable[i].node()).getRouterID()  + " on interface:" + i);
                }

            }

        }
    }

 */

    // When messages are received at the router this method is called
/*
    public void recv(SimEnt source, Event event) {
        if (event instanceof MoveInterfaceEvent) {
            changeInterface(((MoveInterfaceEvent) event)._newInterfaceNumber(), ((MoveInterfaceEvent) event)._oldInterface());
        }
        if (event instanceof Message) {
            System.out.println("Router handles packet with seq: " + ((Message) event).seq() + " from node: " + ((Message) event).source().networkId() + "." + ((Message) event).source().nodeId());
            SimEnt sendNext = getInterface(((Message) event).destination());
            System.out.println("Router sends to node: " + ((Message) event).destination().networkId() + "." + ((Message) event).destination().nodeId());
            send(sendNext, event, _now);
        }
    }

 */
}
