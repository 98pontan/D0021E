package Sim;

public class LSAck implements Event {
    int routerID;
    int linkWeight;
    LSAck(int routerID, int linkWeight) {
        this.routerID = routerID;
        this.linkWeight = linkWeight;
    }

    public int getRouterID() {
        return routerID;
    }

    public int getLinkWeight() {
        return linkWeight;
    }
    @Override
    public void entering(SimEnt locale) {

    }
}
