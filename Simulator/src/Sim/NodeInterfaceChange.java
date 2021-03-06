package Sim;

public class NodeInterfaceChange implements Event {
    private Node node;
    int newInterface;
    private Link link;

    public NodeInterfaceChange(Node node, int newInterface, Link link){
        this.node = node;
        this.link = link;
        this.newInterface = newInterface;
    }

    public int getNewInterface() {
        return this.newInterface;
    }

    public int getNetworkId() {
        return this.node.getAddr().networkId();
    }

    public Node getNode() {
        return this.node;
    }

    public Link getLink() {
        return this.link;
    }

    public void entering(SimEnt locale) {
        // TODO Auto-generated method stub

    }
}
