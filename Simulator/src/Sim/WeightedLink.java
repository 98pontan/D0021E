package Sim;
/*
    Class implementing weight on links in Mbit/s
 */
public class WeightedLink extends Link {
    private int weight;
    private int linkID;
    WeightedLink(int weight, int linkID) {
        super();
        this.weight = weight;
        this.linkID = linkID;
    }
    public int getWeight() {
        return weight;
    }

    public int getLinkID() {
        return linkID;
    }
    public void recv(SimEnt src, Event ev)
    {
        //System.out.println("Link recv msg, passes it through");
        if (src == _connectorA)
        {
            send(_connectorB, ev, _now);
        }
        else
        {
            send(_connectorA, ev, _now);
        }
        if (ev instanceof changeWeight) {
            int newWeight = ((changeWeight) ev).getWeight();
            weight = newWeight;
            System.out.println("New bandwith on Link " + this.getLinkID() + " is " + weight + " at time " + SimEngine.getTime());
            if(_connectorA instanceof Router) {
                ((Router) _connectorA).sendLSA();
            }
            if(_connectorB instanceof Router) {
                ((Router) _connectorB).sendLSA();
            }
        }
    }
}