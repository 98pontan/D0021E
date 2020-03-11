package Sim;

import java.util.HashMap;

public class HomeAgent extends RouterInterfaceChanger {
    protected HomeAgentEntry _homeAgentTable; // a table we will most likely not use
    private NetworkAddr address; // for holding the new Address for lookups in the HashMap
    HashMap<NetworkAddr, NetworkAddr> routingTable; // HashMap for address mapping

    // Initiates HashMap
    HomeAgent(int interfaces) {
        super(interfaces);
        this.routingTable = new HashMap<>();
        // TODO Auto-generated constructor stub
    }

    // function for receiving events
    public void recv(SimEnt source, Event event) {
        System.out.println("Event Type rcv in router: " + event);
        System.out.println("Src: " + source);
        if (event instanceof NotifyHAEvent) {
        	
            // call function that maps the new address to the homeaddress  also calling the wanted data
            System.out.println(((NotifyHAEvent) event).getHomeAddress());
            networkChanger(((NotifyHAEvent) event).getHomeAddress(), ((NotifyHAEvent) event).getCareOfAddress());
        }

        if (event instanceof MoveInterfaceEvent) {
            changeInterface(((MoveInterfaceEvent) event)._oldInterface(), ((MoveInterfaceEvent) event)._newInterfaceNumber());

        }
        
        
        if (event instanceof Message) {
            System.out.println("Router handles packet with seq: " + ((Message) event).seq() + " from node: " + ((Message) event).source().networkId() + "." + ((Message) event).source().nodeId());
            SimEnt sendNext = getInterface(((Message) event).destination().networkId());
            int temp = routingTable.get(address).networkId();
            simEnt sendNext = getInterface();
            System.out.println("Router sends to node: " + ((Message) event).destination().networkId() + "." + ((Message) event).destination().nodeId());
//          HashMap.containsKey();
            send(sendNext, event, _now);
        }
    }
    // Maps homeaddress to the new address
    public void networkChanger(NetworkAddr homeAddress, NetworkAddr careOfAddress){
        routingTable.put(homeAddress, careOfAddress);
        address = careOfAddress;
        System.out.println("re-directed traffic");
        System.out.println(routingTable);

    }



}
