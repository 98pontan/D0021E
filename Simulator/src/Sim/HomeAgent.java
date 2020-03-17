package Sim;

import java.util.HashMap;

public class HomeAgent extends RouterInterfaceChanger {
    protected HomeAgentEntry _homeAgentTable;
    protected HashMap<NetworkAddr, NetworkAddr> routingTable; // HashMap for mapping home addresses to the new address, Key HomeAddress Value foreign address
    private int routerID;
    private NetworkAddr networkAddress;

    HomeAgent(int interfaces, int _routerID) {
        super(interfaces);
        networkAddress = new NetworkAddr(routerID, 0);
        this.routingTable = new HashMap<>();
        this.routerID = _routerID;
    }
    
    public boolean flag(int nodeId) {
    	for(NetworkAddr i: routingTable.keySet()) {
    		if (i.nodeId() == nodeId) {
    			return true;
    		}
    		
    	}
		return false;    	
    }

    public NetworkAddr getNetworkAddress() {
        return this.networkAddress;
    }

    
    public void recv(SimEnt source, Event event) {
        System.out.println("Event Type rcv in router: " + event);
        System.out.println("Src: " + source);
        
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
            NetworkAddr careOfAddress = routingTable.get(destination);
            System.out.println("Router sends to node: " + ((Message) event).destination().networkId() + "." + ((Message) event).destination().nodeId());
            if (careOfAddress != null) {
                sendNext = getInterface(careOfAddress.networkId());
            }
            else {
                sendNext = getInterface(destination.networkId());
            }
            send(sendNext, event, _now);


        }
        if (event instanceof Solicitation) {
            System.out.println("Router recieved solictiation message from: " + ((Message) event).source().networkId() + "." + ((Message) event).source().nodeId());
            //send() send advertisement
        }
    }

    public void networkChanger(NetworkAddr homeAddress, NetworkAddr careOfAddress){
        routingTable.put(homeAddress, careOfAddress);
        System.out.println("networkChan" +
                "ger Test");
        System.out.println(routingTable);

    }

    public int getFreeInterface() {
        for (int i = 0; i < _routingTable.length; i++) {
            if (_routingTable[i]!=null) {
                return i;
            }
        }
        return -1;
    }



}
