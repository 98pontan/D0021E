package Sim;

public class ChangeWeight implements Event  {
    private int newWeight;
    ChangeWeight(int newWeight) {
        this.newWeight = newWeight;
    }

    public int getWeight() {
        return newWeight;
    }

    @Override
    public void entering(SimEnt locale) {

    }


}
