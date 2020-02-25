package Sim;

public class HomeAgent extends ChangeableRouterInterface {

	HomeAgent(int interfaces) {
		super(interfaces);
		// TODO Auto-generated constructor stub
		protected HomeAgentEntry currentLocation;
		protected HomeAgentEntry  homeLocation;
		
		
	}
	
	
	
	public void changeInterface(int newInterfaceNumber, int oldInterfaceNumber) {
		if (currentLocation == homeLocation) {
			_routingTable[currentLocation} = route;
		}
        if (newInterfaceNumber < _interfaces && _routingTable[newInterfaceNumber] == null) {
            RouteTableEntry route = _routingTable[oldInterfaceNumber];
            _routingTable[oldInterfaceNumber] = null;
            _routingTable[newInterfaceNumber] = route;
        } else
            System.out.println("The port doesn't exist or is already occupied");

    }

}
