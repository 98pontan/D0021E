package Sim;

import java.util.Random;

public class Generator_Poisson extends Node {
    private double _time = 0.0;
    private int _stopSendingAfter = 0; // Number of packets that the genereator will generate
    private int standardDeviation;
    private double mean;

	
	
    public Generator_Poisson(int network, int node) {
		super(network, node);
		_id = new NetworkAddr(network, node);
	}

	//D. Knuths algorithm
    private static int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
    
    public void StartSending(int network, int node, int standardDeviation, int mean, int number)
    {
        _stopSendingAfter = number;
        this.standardDeviation = standardDeviation;
        this.mean = mean;
        _toNetwork = network;
        _toHost = node;
        _seq = 1;
        send(this, new TimerEvent(),0);
    }
    
    public void recv(SimEnt src, Event ev)
    {
        if (ev instanceof TimerEvent)
        {
            if (_stopSendingAfter > _sentmsg)
            {
                for(int i = 0; i < _stopSendingAfter; i++) {
                    double poissonValue = getPoissonValue();
                    //System.out.println("Packge number " + _seq + "Time " + (_time + SimEngine.getTime()));
                    TimeWriter.logTime(poissonValue, "gaus");
                    _sentmsg++;
                    send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost),_seq), poissonValue);
                    send(this, new TimerEvent(), poissonValue);
                    System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq + " at time "+SimEngine.getTime());
                    System.out.println("SimTime:" + SimEngine.getTime());
                    _seq++;
                }
            }
            send(this, new TimerEvent(),1);
        }
        if (ev instanceof Message)
        {
            System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());

        }
    }
    public double getPoissonValue() {
        return getPoissonRandom(1)*standardDeviation+mean;
    }
}
