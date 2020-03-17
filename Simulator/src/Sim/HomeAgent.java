package Sim;

import java.util.HashMap;

public class HomeAgent extends SimEnt {
    protected HashMap<NetworkAddr, NetworkAddr> routingTable; // HashMap for mapping home addresses to the new address, Key HomeAddress Value foreign address

    HomeAgent() {
        routingTable = new HashMap<>();
    }
    @Override
    public void recv(SimEnt source, Event event) {

    }

    public void newAddress(NetworkAddr homeAddress, NetworkAddr careOfAddress) {
        routingTable.put(homeAddress, careOfAddress);
    }

    public NetworkAddr getCoaAddress(NetworkAddr homeAddress) {
        return routingTable.get(homeAddress);
    }

}
