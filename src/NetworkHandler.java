import sun.nio.ch.SocketOpts;

import sun.security.krb5.internal.UDPClient;
import java.io.*;
import java.io.IOException;
import java.net.*;

public class NetworkHandler {

    public static final int BROADCAST_PORT = 666;
    public static final int TCP_PORT = 123;
    InetAddress group = InetAddress.getByName("203.0.157.0");
	private GameState state;
    MulticastSocket mcs;

	public NetworkHandler(GameState state) throws IOException {
		this.state = state;


        ServerSocket ss = new ServerSocket();
        mcs = new MulticastSocket(BROADCAST_PORT);
        mcs.joinGroup(group);
    }

    public void broadcast()throws IOException{
        byte[] buf = new byte[256];
        DatagramPacket packet;
        packet = new DatagramPacket(buf, buf.length, group, BROADCAST_PORT);
        mcs.send(packet);
    }

    public void listenForBroadcast()throws IOException{
        byte[] buf = new byte[256];

        DatagramPacket packet;

            packet = new DatagramPacket(buf, buf.length);
            mcs.receive(packet);

            String received = new String(packet.getData());
            System.out.println(received + "has joined.");


        mcs.leaveGroup(group);
        mcs.close();
    }
    public void listensFortTCP()throws IOException {
        byte[] buf = new byte[256];

         MulticastSocket tcp;
        tcp = new MulticastSocket(TCP_PORT );
        ServerSocket tcpsocketListne = new ServerSocket(TCP_PORT);
        Socket tcpConecetion = tcpsocketListne.accept();



    }
}
