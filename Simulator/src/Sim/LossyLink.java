package Sim;
// Importing random for jitter and delay
import java.util.ArrayList;
import java.util.Random;


// Setting up a Class with delay, jitter, drop and a random value.
public class Lossylink extends Link {
	private int delay = 0;    // average delay in ms
	private int jitter = 0;   // jitter in ms
	private double drop = 0;     // probability to drop a package between 0.0-1-0
	private double nextDelay = 0; //
	Random rand = new Random();
	ArrayList<Double> delays = new ArrayList<Double>();
	int droppedPackages;

	public Lossylink(int delay, int jitter, double drop) {
		super();
		this.jitter = jitter;
		this.delay = delay;
		this.drop = drop;
	}

	@Override
	public void recv(SimEnt src, Event ev)
	{
		this.nextDelay = getNextDelay();
		if (ev instanceof Message)
		{
			if (rand.nextDouble() <= drop) {
				System.out.println("Unfortunately the packet was dropped. :(");
				droppedPackages++;
				return;
			}
			else {
				System.out.println("Link recv msg, passes it through");
				if (src == _connectorA)
				{
					send(_connectorB, ev, this.nextDelay);
				}
				else
				{
					send(_connectorA, ev, this.nextDelay);
				}
			}
		}
	}

	private double getNextDelay() {
		double jitterz = Math.abs(jitter * ( rand.nextDouble()*2-1));
		delays.add(jitterz);
		return jitterz + delay;
	}
	double averageDelay() {
		double x = 0;
		for (int i = 0; i<delays.size(); i++) {
			x = x+ delays.get(i);
		}
		x = x/delays.size();
		return x;
	}
}