package Sim;
import Sim.LossyLink;


// An example of how to build a topology and starting the simulation engine

public class Run {
	public static void main (String [] args)
	{
		// Creates two links
		Link link1 = new WeightedLink(30);
		Link link2 = new WeightedLink(10);
		Link link3 = new WeightedLink(30);
		Link link4 = new WeightedLink(100);
		Link link5 = new WeightedLink(100);
		//TESTING LSDB
		LSDB lsdb = new LSDB(3, 2);

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
		Router routeNode = new Router(10, 1, lsdb);
		Router routeNode1 = new Router(10, 2, lsdb);
		Router routeNode2 = new Router(10, 3, lsdb);
		// Connects two interfaces
		routeNode.connectInterface(0, link3, routeNode1);
		routeNode1.connectInterface(0, link3, routeNode);

		routeNode.connectInterface(1, link4, routeNode2);
		routeNode2.connectInterface(1, link4, routeNode);

		routeNode1.connectInterface(2, link5, routeNode2);
		routeNode2.connectInterface(2, link5, routeNode1);

		routeNode.connectInterface(3, link1, host1);
		routeNode1.connectInterface(3, link2, host2);
		routeNode.printInterfaces();
		routeNode1.printInterfaces();
		routeNode2.printInterfaces();

		routeNode.sendLSA();
		routeNode1.sendLSA();
		routeNode2.sendLSA();

		// Generate some traffic
		//host1.moveInterfaceAfter(3, 2, 0);
		//host1.StartSending(2, 1, 6, 6, 1);
		host1.StartSending(2, 1, 1, 6, 1);
		//host2.StartSending(1, 1, 1, 6, 1);
	//	host2.changeRouterAfter(3, routeNode1, routeNode);
		
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
			System.out.println("======================================");
			lsdb.printMatrix();

		}
		catch (Exception e)
		{
			System.out.println("The motor seems to have a problem, time for service?");
		}

	}

}
