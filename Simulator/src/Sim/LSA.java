package Sim;

public class LSA implements Event {
    int routerID;
    int linkWeight;
    LSA(int routerID, int linkWeight) {
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
