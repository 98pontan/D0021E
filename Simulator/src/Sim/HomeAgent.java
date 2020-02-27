package Sim;

public class HomeAgent extends ChangeableRouterInterface {

	HomeAgent(int interfaces) {
		super(interfaces);
		// TODO Auto-generated constructor stub
		protected HomeAgentEntry currentLocation;
		protected HomeAgentEntry  homeLocation;
		
		
	}
	
	
	
	public void Handover(int newInterfaceNumber, int oldInterfaceNumber) {
		if (currentLocation == homeLocation) {
			_routingTable[currentLocation] = route;
			 System.out.println("You are still att Home");
	
		}
		else {
            RouteTableEntry route = _routingTable[oldInterfaceNumber];
            _routingTable[oldInterfaceNumber] = null;
            _routingTable[newInterfaceNumber] = route;
		}

	}
	
}
