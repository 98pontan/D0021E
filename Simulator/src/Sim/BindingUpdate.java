package Sim;

public class BindingUpdate implements Event{
    private NetworkAddr careOfAddress;
    private NetworkAddr homeAddress;
    private Node node;
    private Router nextRouter;

    BindingUpdate(NetworkAddr homeAddress, Node node, Router nextRouter) {
        this.careOfAddress = careOfAddress;
        this.homeAddress = homeAddress;
        this.node = node;
        this.nextRouter = nextRouter;
    }

    public void entering(SimEnt locale) {

    }

    public Node getNode() {
        return node;
    }

    public Router getNextRouter() {
        return nextRouter;
    }

    public NetworkAddr getCareOfAddress() {
        return careOfAddress;
    }

    public NetworkAddr getHomeAddress() {
        return homeAddress;
    }
}
