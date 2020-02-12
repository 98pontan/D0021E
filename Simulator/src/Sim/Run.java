package Sim;
import Sim.LossyLink;

// An example of how to build a topology and starting the simulation engine

public class Run {
	public static void main (String [] args)
	{
 		//Creates two links
 		Link link1 = new LossyLink(50, 5, 0.08);
		Link link2 = new LossyLink(50, 10, 0.00);
		
		// Create two end hosts that will be
		// communicating via the router
		Node host1 = new Node(1,1);
		Node host2 = new Node(2,1);
		
		// Create a host that will be comunicating via the router
		//Generator_CBR host3 = new Generator_CBR(3, 2);
		//Connect links to hosts
		host1.setPeer(link1);
		host2.setPeer(link2);

		// Creates as router and connect
		// links to it. Information about 
		// the host connected to the other
		// side of the link is also provided
		// Note. A switch is created in same way using the Switch class
		Router routeNode = new Router(2);
		routeNode.connectInterface(0, link1, host1);
		routeNode.connectInterface(1, link2, host2);
		
		// Generate some traffic
		// host1 will send 20 messages with time interval 5 to network 2, node 1. Sequence starts with number 1
		host1.StartSending(2, 2, 10, 5, 1);
		//host3.StartSending(2, 1, 30, 2, 1);
		// host2 will send 30 messages with time interval 7 to network 1, node 1. Sequence starts with number 10
		//host2.StartSending(1, 1, 30, 7, 10);
		
		// Start the simulation engine and of we go!
		Thread t=new Thread(SimEngine.instance());
	
		t.start();
		try
		{
			t.join();
			System.out.println("Link 1 average delays: " + ((LossyLink) link1).averageDelay());
			System.out.println("Link 1 average delays: " + ((LossyLink) link1).averageDelay());
			System.out.println("Link 1 delays: " + ((LossyLink) link1).delays.size());
			for (int i = 0; i < ((LossyLink) link1).delays.size(); i++) {
				System.out.println(((LossyLink) link1).delays.get(i));
			}
			System.out.println("Link 1 dropped packages: " + ((LossyLink) link1).droppedPackages);
		}
		catch (Exception e)
		{
			System.out.println("The motor seems to have a problem, time for service?");
		}

	}
}
