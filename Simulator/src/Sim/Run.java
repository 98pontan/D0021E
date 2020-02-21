package Sim;
import Sim.LossyLink;


// An example of how to build a topology and starting the simulation engine

public class Run {
	public static void main (String [] args)
	{
		//Creates two links
		//Link link1 = new Link();
		//Link link2 = new Link();

		TimeWriter time = new TimeWriter();

		Link link1 = new LossyLink(20, 5, 0.00);
		Link link2 = new LossyLink(20, 5, 0.00);

		// Create two end hosts that will be
		// communicating via the router
		Node host1 = new Node(1,1);
		//Node host2 = new Node(2,1);
		Sink host2 = new Sink(2, 1);
		
		// CBR
		//Generator_CBR host1 = new Generator_CBR(1,1);

		//Gaussian
		//Generator_Gaussian host1 = new Generator_Gaussian(1, 1);

		//Poisson
		//Generator_Poisson host1 = new Generator_Poisson(1, 1);

		// Create a host that will be comunicating via the router

		//Connect links to hosts
		host1.setPeer(link1);
		host2.setPeer(link2);
		// Creates as router and connect
		// links to it. Information about
		// the host connected to the other
		// side of the link is also provided
		// Note. A switch is created in same way using the Switch class
		//Router routeNode = new Router(2);
		ChangeableRouterInterface routeNode = new ChangeableRouterInterface(10);

		
		routeNode.connectInterface(0, link1, host1);
		routeNode.connectInterface(1, link2, host2);
		routeNode.printInterfaces();
		//Poisson
		//host1.StartSending(2, 5, 1, 10000);

		//Gaussian
		//host1.StartSending(2, 2, 3, 8, 10000);


		//CBR
		//host1.StartSending(1, 1, 10, 3);
		
		// Generate some traffic
		// host1 will send 20 messages with time interval 5 to network 2, node 1. Sequence starts with number 1
		host1.StartSending(2, 2, 10, 6, 1);
		routeNode.changeInterface(9, 0);
		routeNode.printInterfaces();


		// host2 will send 30 messages with time interval 7 to network 1, node 1. Sequence starts with number 10
		//host2.StartSending(1, 1, 30, 7, 10);

		// Start the simulation engine and of we go!
		Thread t=new Thread(SimEngine.instance());

		t.start();
		try
		{
			t.join();
		}
		catch (Exception e)
		{
			System.out.println("The motor seems to have a problem, time for service?");
		}

	}

}
