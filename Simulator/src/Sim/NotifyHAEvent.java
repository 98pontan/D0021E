package Sim;

public class NotifyHAEvent implements Event{
    private NetworkAddr careOfAddress;
    private NetworkAddr homeAddress;

    NotifyHAEvent(NetworkAddr careOfAddress, NetworkAddr homeAddress) {
        this.careOfAddress = careOfAddress;
        this.homeAddress = homeAddress;

    }

    public void entering(SimEnt locale) {

    }

    public NetworkAddr getCareOfAddress() {
        return careOfAddress;
    }

    public NetworkAddr getHomeAddress() {
        return homeAddress;
    }
}
