package Sim;

import java.util.HashMap;

public class HomeAgent extends RouterInterfaceChanger {
    protected HomeAgentEntry _homeAgentTable; 
    private NetworkAddr address;
    protected HashMap<NetworkAddr, NetworkAddr> routingTable; // HashMap for mapping home addresses to the new address, Key HomeAddress Value foreign address
    private int routerID;

    HomeAgent(int interfaces, int _routerID) {
        super(interfaces);
        this.routingTable = new HashMap<>();
        this.routerID = _routerID;
    }
    
    
    public void recv(SimEnt source, Event event) {
        System.out.println("Event Type rcv in router: " + event);
        System.out.println("Src: " + source);
        
        // When an binding update occur it will receive the addresses and call networkChanger to bind them in the HashMap
        if (event instanceof BindingUpdate) {
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
            	 
            }
            		
            else {
                sendNext = getInterface(((Message) event).destination().networkId());
            }

            send(sendNext, event, _now);
        }
    }

    public void networkChanger(NetworkAddr homeAddress, NetworkAddr careOfAddress){
    	for(NetworkAddr i: routingTable.keySet() ) {
	    	if(careOfAddress == routingTable.get(i)) {
				routingTable.put(homeAddress,  careOfAddress.nodeId() +1));
			}
		
    	}
        routingTable.put(homeAddress, careOfAddress);
        System.out.println("networkChanger Test");
        System.out.println(routingTable);

    }



}
