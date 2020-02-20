package Sim;

public class Generator_CBR extends Node {
    private double _time = 0.0; //
    protected int _packets_per_second; // Number of packets per second
    private int _stopSendingAfter = 0; // Number of packets that the genereator will generate


    public Generator_CBR(int network, int node) {
        super(network, node);
        _id = new NetworkAddr(network, node); //id of the network
    }


    public void StartSending(int network, int node, int number, int packets_per_second) {
        _stopSendingAfter = number;
        _packets_per_second = packets_per_second;
        _toNetwork = network;
        _toHost = node;
        _seq = 1;
        send(this, new TimerEvent(), 0);
    }

    public void recv(SimEnt src, Event ev) {
        if (ev instanceof TimerEvent) {
            if (_stopSendingAfter > _sentmsg) {
                double tempT = 1.0 / _packets_per_second;
                //int package_number = 1;
                for (int i = 0; i < _stopSendingAfter; i++) {
					TimeWriter.logTime(SimEngine.getTime()+_time, "CBR");
					_sentmsg++;
                    send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost), _seq), _time);
                    send(this, new TimerEvent(), _time);
                    System.out.println("Node " + _id.networkId() + "." + _id.nodeId() + " sent message with seq: " + _seq + " at time " + SimEngine.getTime());
                    System.out.println("SimTime:" + SimEngine.getTime());
                    _seq++;
                    _time += tempT;
                    //package_number ++;
                }


            }
            send(this, new TimerEvent(), 1);
        }
        if (ev instanceof Message) {
            System.out.println("Node " + _id.networkId() + "." + _id.nodeId() + " receives message with seq: " + ((Message) ev).seq() + " at time " + SimEngine.getTime());

        }
    }

}
