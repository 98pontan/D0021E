package Sim;
/*
    Class implementing weight on links in Mbit/s
 */
public class WeightedLink extends Link {
    private int weight;
    WeightedLink(int weight) {
        super();
        this.weight = weight;
    }
    public int getWeight() {
        return weight;
    }
}