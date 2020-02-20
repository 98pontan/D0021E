package Sim;

import java.util.Random;

public class Generator_Gaussian extends Node {
    private double _time = 0.0;
    private int _stopSendingAfter = 0; // Number of packets that the genereator will generate
    private int standardDeviation;
    private int mean;
    Random r = new Random();

    public Generator_Gaussian(int network, int node) {
        super(network, node);
        _id = new NetworkAddr(network, node); //id of the network
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
                    double gaussianValue = getGaussianValue();
                    TimeWriter.logTime(SimEngine.getTime()+gaussianValue, "gaus");
                    _sentmsg++;
                    send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost),_seq), gaussianValue);
                    send(this, new TimerEvent(), gaussianValue);
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
    public double getGaussianValue() {
        return r.nextGaussian()*this.standardDeviation+this.mean;
    }
}

