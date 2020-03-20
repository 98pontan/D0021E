package Sim;

public class Advertisement implements Event {

	private Router router;
	Advertisement(Router router) {
		this.router = router;
	}

	public Router getRouter() {
		return router;
	}

	@Override
	public void entering(SimEnt locale) {

	}
}
