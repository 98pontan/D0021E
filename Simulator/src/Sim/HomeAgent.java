package Sim;

import java.util.HashMap;

public class HomeAgent extends RouterInterfaceChanger {
    protected HomeAgentEntry _homeAgentTable;
    private NetworkAddr address;
    HashMap<NetworkAddr, NetworkAddr> routingTable;

    HomeAgent(int interfaces) {
        super(interfaces);
        this.routingTable = new HashMap<>();
        // TODO Auto-generated constructor stub
    }

    public void recv(SimEnt source, Event event) {
        System.out.println("Event Type rcv in router: " + event);
        System.out.println("Src: " + source);
        if (event instanceof NotifyHAEvent) {
            // call function that changes network address
            System.out.println(((NotifyHAEvent) event).getHomeAddress());
            networkChanger(((NotifyHAEvent) event).getHomeAddress(), ((NotifyHAEvent) event).getCareOfAddress());
        }

        if (event instanceof MoveInterfaceEvent) {
            changeInterface(((MoveInterfaceEvent) event)._oldInterface(), ((MoveInterfaceEvent) event)._newInterfaceNumber());

        }
        if (event instanceof Message) {
            System.out.println("Router handles packet with seq: " + ((Message) event).seq() + " from node: " + ((Message) event).source().networkId() + "." + ((Message) event).source().nodeId());
            SimEnt sendNext = getInterface(((Message) event).destination().networkId());
            System.out.println("Router sends to node: " + ((Message) event).destination().networkId() + "." + ((Message) event).destination().nodeId());
            send(sendNext, event, _now);
        }
    }

    public void networkChanger(NetworkAddr homeAddress, NetworkAddr careOfAddress){
        routingTable.put(homeAddress, careOfAddress);
        System.out.println("fan");
        System.out.println(routingTable);

    }



}
