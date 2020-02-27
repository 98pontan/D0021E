package Sim;

public class HomeAgentEntry extends TableEntry {
    HomeAgentEntry(SimEnt link, SimEnt node) {
        super(link, node);
    }

    public SimEnt link()
    {
        return super.link();
    }

    public SimEnt node()
    {
        return super.node();
    }
}
