package Sim;
import Sim.LossyLink;


// An example of how to build a topology and starting the simulation engine

public class Run {
	public static void main (String [] args)
	{

		NetworkAddr n1 = new NetworkAddr(3, 1);
		
		// Creates two links
		Link link1 = new Link();
		Link link2 = new Link();

		// Create two end hosts that will be
		// communicating via the router
		Node host1 = new Node(1,1);
		Node host2 = new Node(2, 1);

		//Connect links to hosts
		host1.setPeer(link1);
		host2.setPeer(link2);
		
		// Creates as router and connect
		// links to it. Information about
		// the host connected to the other
		// side of the link is also provided
		// Note. A switch is created in same way using the Switch class
		//Router routeNode = new Router(2);
		
		// Creates home agent with 10 interfaces
		HomeAgent routeNode = new HomeAgent(10);
		HomeAgent routeNode1 = new HomeAgent(10);
		// Connects two interfaces
		routeNode.connectInterface(0, link1, host1);
		routeNode.connectInterface(1, link2, host2);
		routeNode.printInterfaces();

		// Generate some traffic
		//host1.moveInterfaceAfter(3, 2, 0);
		host1.changeRouterAfter(4, routeNode, routeNode1);
		
		//routeNode.changeInterface(9, 0);
		//routeNode.printInterfaces();

		host1.StartSending(2, 2, 10, 6, 1);


		// Start the simulation engine and of we go!
		Thread t=new Thread(SimEngine.instance());

		t.start();
		try
		{
			t.join();
			routeNode.printInterfaces();
		}
		catch (Exception e)
		{
			System.out.println("The motor seems to have a problem, time for service?");
		}

	}

}
