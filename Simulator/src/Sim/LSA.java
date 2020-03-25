package Sim;

public class LSA implements Event {
    int routerID;
    LSA(int routerID) {
        this.routerID = routerID;
    }

    public int getRouterID() {
        return routerID;
    }


    @Override
    public void entering(SimEnt locale) {

    }
}
