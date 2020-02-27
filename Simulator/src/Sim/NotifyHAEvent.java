package Sim;

public class NotifyHAEvent implements Event{
    private NetworkAddr _foreignAddress;
    private Node _node;
    NotifyHAEvent(NetworkAddr foreignAddress, Node node) {
        this._foreignAddress = foreignAddress;
        this._node = node;

    }

    public void entering(SimEnt locale) {

    }
}
