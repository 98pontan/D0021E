package Sim;

public class TableEntrySwitcher extends TableEntry {
	
	
	TableEntrySwitcher(SimEnt link, SimEnt node)
	{
		super(link, node);
		
	}
	
	protected SimEnt link()
	{
		return super.link();
	}

	protected SimEnt node()
	{
		return super.node();
	}

}
