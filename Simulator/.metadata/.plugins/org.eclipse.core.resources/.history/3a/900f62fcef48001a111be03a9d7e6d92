package Sim;

import java.util.Random;


// Setting up a Class with delay, jitter, drop and a random value. 
public class Lossylink extends Link {
	private float delay = 0;
	private float jitter = 0;
	private float drop = 0;
	
	Random rand = new Random();
	//Constructor
	public Lossylink(float delay, float jitter, float drop) {
		super();
		
		this.jitter = jitter;
		this.delay = delay;
		this.drop = drop;

	}
	
	public void recv(SimEnt src, Event ev) {
		if (ev instanceof Message)
		{
			//Creating a jitter with a random positive value between 0 and 50.  
			double jitterValue = rand.nextInt(50)   * jitter;
			
			double time = jitterValue + delay + _now;
			
			int dropOrNot = rand.nextInt(100);
			if (dropOrNot < drop){
				System.out.println("Lossy link dropped the packet");
				return;
			}
			else {		
				System.out.println("Lossy link didnt drop the packet" + '\n' + "Link recv msg, passes it through");
				if (src == _connectorA)
				{
					
					send(_connectorB, ev, time);
				}
				else
				{
					send(_connectorA, ev, time);
				}
			}
		}
	}
}