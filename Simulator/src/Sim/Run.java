package Sim;
import Sim.LossyLink;


// An example of how to build a topology and starting the simulation engine

public class Run {
	public static void main (String [] args)
	{
		// Creates two links
		Link link1 = new Link();
		Link link2 = new Link();
		Link link3 = new Link();

		// Create two end hosts that will be
		// communicating via the router
		Node host1 = new Node(1,1);
		Node host2 = new Node(1, 2);

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
		Router routeNode = new Router(10, 1);
		Router routeNode1 = new Router(10, 2);
		// Connects two interfaces
		routeNode.connectInterface(0, link3, routeNode1);
		routeNode1.connectInterface(0, link3, routeNode);

		routeNode.connectInterface(1, link1, host1);
		routeNode.connectInterface(2, link2, host2);
		routeNode.printInterfaces();
		routeNode1.printInterfaces();

		// Generate some traffic
		//host1.moveInterfaceAfter(3, 2, 0);
		//host1.StartSending(2, 1, 6, 6, 1);
		host1.StartSending(1, 2, 3, 6, 1);
		host2.StartSending(1, 1, 3, 6, 1);
		host2.changeRouterAfter(3, routeNode, routeNode1);
		
		//routeNode.changeInterface(3, 1);
		//routeNode.printInterfaces();


		//host2.StartSending(1, 1, 3, 6, 1);


		// Start the simulation engine and of we go!
		Thread t=new Thread(SimEngine.instance());

		t.start();
		try
		{
			t.join();

			routeNode.printInterfaces();
			System.out.println("======================================");
			routeNode1.printInterfaces();




		}
		catch (Exception e)
		{
			System.out.println("The motor seems to have a problem, time for service?");
		}

	}

}
